DELIMITER //

CREATE FUNCTION right_num(
    left_num bigint,
    left_den bigint
) RETURNS bigint
    READS SQL DATA
    DETERMINISTIC
BEGIN
RETURN (left_num * right_den(left_num, left_den) + 1) / left_den;
END //
DELIMITER ;