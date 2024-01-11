DELIMITER //

CREATE FUNCTION compare(
    a_num bigint,
    a_den bigint,
    b_num bigint,
    b_den bigint
) RETURNS bigint
    READS SQL DATA
    DETERMINISTIC
BEGIN
RETURN a_num * b_den - a_den * b_num;
END //
DELIMITER ;