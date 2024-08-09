insert into user (dob, address, email, first_name, last_name, phone_number, gender, role, password)
values ('2001-06-05', '498 Huỳnh Tấn Phát, Quận 7', 'an@gmail.com', 'Nguyễn Văn', 'An', '0911222333', 'MALE', 'STUDENT', '1234'),
       ('2002-03-26', '498 Huỳnh Tấn Phát, Quận 7', 'quynh@gmail.com', 'Lê Trúc', 'Quỳnh', '0911222333', 'FEMALE', 'STUDENT', '1234'),
       ('2003-03-12', '600 Điện Biên Phủ, TPHCM', 'hvc@gmail.com', 'Hồ Văn', 'Cường', '0911222333', 'MALE', 'STUDENT', '1234'),
       ('1998-05-26', '498 Huỳnh Tấn Phát, Quận 7', 'gianvien1@gmail.com', 'Giảng viên', '1', '0111933232', 'FEMALE', 'LECTURER', '1234');

insert into academic_details (student_id, student_code,  admission_year, faculty, klass)
values (1, '1915221', 'K2019', 'Khoa Máy Tính', 'CS01K2019'),
       (2, '2022111', 'K2020', 'Khoa Luật', 'LAW05K2020'),
       (3, '1922111', 'K2019', 'Khoa Điện', 'EE01K2019');

insert into subject (credits, code, name)
values (3, '2101625 ', 'Systems Analysis and Design'),
       (3, '2101404 ', 'Graph Theory'),
       (3, '2101623 ', 'Object Oriented Programming'),
       (3, '2101584 ', 'Data Analysis Programming 1'),
       (3, '2101657 ', 'Application Development'),
       (3, '2101445 ', 'Pattern Recognition'),
       (3, '2101558 ', 'Distributed Programming with Java Technology'),
       (3, '2101804 ', 'Computer Graphics'),
       (3, '2101628 ', 'Requirement Analysis and management'),
       (3, '2112015 ', 'History of Vietnamese Communist Party');

insert into assessment (subject_id, weight, name)
values  ('1', 20, 'Regular test I'),
        ('1', 30, 'Mid-term'),
        ('1', 10, 'Regular test II'),
        ('1', 20, 'Practical test'),
        ('1', 30, 'Presentation'),

        ('2', 20, 'Regular test'),
        ('2', 30, 'Mid-term'),
        ('2', 50, 'Summative'),

        ('3', 5, 'Writing test'),
        ('3', 5, 'Practical test I'),
        ('3', 5, 'Practical test II'),
        ('3', 5, 'Practical test III'),
        ('3', 30, 'Mid-term test'),
        ('3', 40, 'Practical test IV'),

        ('4', 20, 'Regular test I'),
        ('4', 30, 'Mid-term'),
        ('4', 10, 'Regular test II'),
        ('4', 20, 'Practical test'),
        ('4', 20, 'Presentation'),

        ('5', 25, 'Regular Theory'),
        ('5', 25, 'Mid-term'),
        ('5', 25, 'Regular Practical'),
        ('5', 25, 'Practical test'),

        ('6', 20, 'Regular Test I'),
        ('6', 30, 'Mid-term'),
        ('6', 10, 'Regular Test II'),
        ('6', 20, 'Regular Test III'),
        ('6', 20, 'Presentation'),

        ('7', 20, 'Writing test'),
        ('7', 30, 'Practical test I'),
        ('7', 10, 'Practical test II'),
        ('7', 20, 'Presentation'),
        ('7', 20, 'Writing test'),

        ('8', 20, 'Regular test I'),
        ('8', 30, 'Mid-term test'),
        ('8', 10, 'Regular test II'),
        ('8', 20, 'Practical test'),
        ('8', 20, 'Presentation'),

        ('9', 20, 'Regular test'),
        ('9', 30, 'Mid-term'),
        ('9', 50, 'Final test'),

        ('10', 10, 'Regular exercises'),
        ('10', 5, 'Presentation'),
        ('10', 5, 'Other activities'),
        ('10', 30, 'Mid term'),
        ('10', 50, 'Final exam');

insert into course (lecturer_id, semester_no, subject_id, academic_year, code, name, room)
values  (4, 1, 1, '2024', 'S2101625HK2520241L01', 'Systems Analysis and Design L01', 'A3-456'),
        (4, 1, 1, '2024', 'S2101625HK2520241L02', 'Systems Analysis and Design L02', 'B2-103'),
        (4, 1, 7, '2024', 'S2101558HK2520241L02', 'Distributed Programming with Java Technology L02', 'B2-203'),
        (4, 1, 7, '2024', 'S2101558HK2520241L02', 'Distributed Programming with Java Technology L04', 'A1-103');

insert into enrollment (course_id, student_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 3),
       (3, 3);


DROP procedure pros_init_assessment_value;

DELIMITER //
CREATE PROCEDURE pros_init_assessment_value()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE assessment_id INT;
    DECLARE enrollment_id INT;

    DECLARE cur CURSOR FOR
        select a.id as assessment_id, e.id as enrollment_id
        from enrollment e
                 join assessment a
                 join course c
        where e.course_id = c.id
          and c.subject_id = a.subject_id;

        -- Declare exit handler
    DECLARE EXIT HANDLER FOR NOT FOUND
        BEGIN
            SET done = TRUE;
        END;

    OPEN cur;
    read_loop:
    LOOP
        FETCH cur INTO assessment_id, enrollment_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        INSERT INTO assessment_value (assessment_id, enrollment_id, value)
            VALUES (assessment_id, enrollment_id, -1); -- Initialize grade to NULL
    END LOOP;

    CLOSE cur;
END;
DELIMITER ;

CALL pros_init_assessment_value();