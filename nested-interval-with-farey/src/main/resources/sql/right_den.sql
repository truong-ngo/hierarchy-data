DELIMITER //

CREATE FUNCTION rightDen(a BIGINT, m BIGINT) RETURNS BIGINT
    DETERMINISTIC
    READS SQL DATA
BEGIN
    DECLARE u BIGINT;
    DECLARE v BIGINT;
    DECLARE x BIGINT;
    DECLARE y BIGINT;
    DECLARE q BIGINT;
    DECLARE r BIGINT;

    SET u = m;
    SET v = a;
    SET x = 0;
    SET y = 1;

    WHILE v != 0 DO
        SET q = u DIV v;
        SET r = u MOD v;

        SET @tempX := x;
        SET x = y;
        SET y = @tempX - q * y;

        SET u = v;
        SET v = r;
    END WHILE;

    IF u = 1 OR u = -1 THEN
        RETURN IF(x < 0, -x, m - x);
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Inverse does not exist';
END IF;
END //

DELIMITER ;