CREATE PROCEDURE adjacency_to_nested_set() MODIFIES SQL DATA

BEGIN

START TRANSACTION;

DROP TABLE IF EXISTS tally;
DROP TABLE IF EXISTS nested_set;

CREATE TABLE tally(
    n int primary key
);

INSERT INTO tally(n)
WITH RECURSIVE tally_cte AS (
    SELECT 1 AS n
    UNION ALL
    SELECT n + 1
    FROM tally_cte
    WHERE n < 1000
)
SELECT n from tally_cte;

CREATE TABLE nested_set(
                           id bigint not null default 0,
                           name varchar(255),
                           parent_id bigint default null,
                           lft bigint,
                           rgt bigint,
                           node_number int,
                           node_count int,
                           depth int,
                           sorted_path varbinary(4000),
                           primary key (id),
                           index using hash (lft),
                           index using hash (rgt)
);

INSERT INTO nested_set(id, name, parent_id, depth, lft, rgt, node_number, node_count, sorted_path)
WITH RECURSIVE cte_build_path AS (
    SELECT
        anchor.id,
        anchor.name,
        anchor.parent_id,
        1 as depth,
        CONVERT(UNHEX(LPAD(HEX(anchor.id), 8, '0')), binary(4000)) AS sorted_path
    FROM adjacency_list as anchor
    WHERE anchor.parent_id IS NULL

    UNION ALL

    SELECT
        recur.id,
        recur.name,
        recur.parent_id,
        cte.depth + 1 as depth,
        CONVERT(UNHEX(CONCAT(LPAD(HEX(cte.sorted_path), cte.depth * 8, '0'), LPAD(HEX(recur.id), 8, '0'))), binary) AS sort_path
    FROM adjacency_list as recur
    JOIN cte_build_path as cte ON cte.id = recur.parent_id
)
SELECT
    sorted.id AS id,
    sorted.name as name,
    COALESCE(sorted.parent_id, 0) as parent_id,
    COALESCE(sorted.depth, 0) as depth,
    0 as lft,
    0 as rgt,
    ROW_NUMBER() OVER (ORDER BY sorted.sorted_path) as node_number,
    0 as node_count,
    CAST(UNHEX(LPAD(HEX(sorted.sorted_path), depth * 8, '0')) as binary) as sorted_path
FROM cte_build_path as sorted;

WITH cte_downlines as (
    SELECT
        CONV(HEX(SUBSTRING(sorted_path, (tally.n - 1) * 4 + 1, 4)), 16, 10) as id,
        COUNT(*) as count
    FROM nested_set, tally
    WHERE (tally.n - 1) * 4 + 1 BETWEEN 1 AND LENGTH(sorted_path)
    GROUP BY SUBSTRING(sorted_path, (tally.n - 1) * 4 + 1, 4)
)

UPDATE nested_set
JOIN cte_downlines on nested_set.id = cte_downlines.id
    SET
        nested_set.lft = 2 * nested_set.node_number - nested_set.depth,
        nested_set.node_count = cte_downlines.count,
        nested_set.rgt = (cte_downlines.count - 1) * 2 + (2 * nested_set.node_number - nested_set.depth) + 1;

DROP TABLE tally;

COMMIT;
END;