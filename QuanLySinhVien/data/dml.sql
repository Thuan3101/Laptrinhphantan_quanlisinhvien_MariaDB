insert into student_learning.user (dob, id, address, email, first_name, last_name, password, phone_number, gender, role)
values  ('2001-06-05', 1, '498 Huỳnh Tấn Phát, Quận 7', 'an@gmail.com', 'Nguyễn Văn', 'An', '1234', '0233444', 'MALE', 'STUDENT'),
        ('2002-03-26', 2, '498 Huỳnh Tấn Phát, Quận 7', 'quynh@gmail.com', 'Lê Trúc', 'Quỳnh', '1234', '091196333', 'FEMALE', 'STUDENT'),
        ('2003-03-12', 3, '600 Điện Biên Phủ, TPHCM', 'hvc@gmail.com', 'Hồ Văn', 'Cường', '1234', '0922874399', 'MALE', 'STUDENT'),
        ('1998-05-26', 4, '498 Huỳnh Tấn Phát, Quận 7', 'giangvien1@gmail.com', 'Giảng viên', '1', '1234', '0322874399', 'FEMALE', 'LECTURER'),
        ('2002-03-12', 5, 'Nam định	', 'hoang@gmail.com', 'Lê Nguyên', 'Hoàng', null, '0573490933', 'MALE', 'STUDENT'),
        ('2002-05-16', 6, 'Vũng tàu', 'linh@gmail.com', 'Lê Huyền', 'Linh', null, '0546690933', 'FEMALE', 'STUDENT'),
        ('2001-06-20', 7, 'Quận tân phú', 'my@gmail.com', 'Trần Huỳnh', 'My', null, '0246490785', 'FEMALE', 'STUDENT'),
        ('2001-10-17', 8, 'Quận phú nhuận', 'lmc@gmail.com', 'Lâm Mạnh', 'Cường', null, '098690785', 'MALE', 'STUDENT'),
        ('2003-08-18', 9, 'Trà Vinh', 'lqsn@gmail.com', 'Lê Quang', 'Song Nhật', null, '093390745', 'MALE', 'STUDENT'),
        ('2003-03-11', 12, 'Long An', 'ptna@gmail.com', 'Phạm Thị', 'Nhật Ánh', null, '093390722', 'FEMALE', 'STUDENT');

insert into student_learning.academic_details (id, student_id, admission_year, faculty, klass, student_code)
values  (1, 1, 'K2019', 'Khoa Máy Tính', 'CS01K2019', '1915221'),
        (2, 2, 'K2020', 'Khoa Luật', 'LAW05K2020', '2022111'),
        (3, 3, 'K2019', 'Khoa Điện', 'EE01K2019', '1922111'),
        (4, 5, '2020', 'Môi trường', 'CC222', '20201101'),
        (5, 6, '2020', 'CCC', 'CC220', '20021122'),
        (6, 7, 'K2021', 'Khoa Dệt may', 'CS01K2021', '2100999'),
        (7, 8, 'K2019', 'Khoa Cơ khí', 'CS01K2021', '1911567'),
        (8, 9, 'K2021', 'Khoa Cơ khí', 'CS01K2021', '2100992'),
        (11, 12, 'K2021', 'Khoa Hoá học', 'HC01K2021', '2100990');

insert into student_learning.subject (credits, id, code, name)
values  (3, 1, '2101625 ', 'Systems Analysis and Design'),
        (3, 2, '2101404 ', 'Graph Theory'),
        (3, 3, '2101623 ', 'Object Oriented Programming'),
        (3, 4, '2101584 ', 'Data Analysis Programming 1'),
        (3, 5, '2101657 ', 'Application Development'),
        (3, 6, '2101445 ', 'Pattern Recognition'),
        (3, 7, '2101558 ', 'Distributed Programming with Java Technology'),
        (3, 8, '2101804 ', 'Computer Graphics'),
        (3, 9, '2101628 ', 'Requirement Analysis and management'),
        (3, 10, '2112015 ', 'History of Vietnamese Communist Party');

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

insert into student_learning.course (id, lecturer_id, semester_no, subject_id, academic_year, code, name, room)
values  (1, 4, 1, 1, '2024', 'S2101625HK2520241L01', 'Systems Analysis and Design L01', 'A3-456'),
        (2, 4, 1, 1, '2024', 'S2101625HK2520241L02', 'Systems Analysis and Design L02', 'B2-103'),
        (3, 4, 1, 7, '2024', 'S2101558HK2520241L02', 'Distributed Programming with Java Technology L02', 'B2-203'),
        (4, 4, 1, 7, '2024', 'S2101558HK2520241L02', 'Distributed Programming with Java Technology L04', 'A1-103');

insert into student_learning.enrollment (avg_grade, course_id, id, student_id)
values  (8.4, 1, 1, 1),
        (null, 1, 2, 2),
        (7.66667, 2, 3, 1),
        (null, 2, 4, 3),
        (null, 3, 5, 3),
        (null, 1, 7, 3),
        (8.46667, 4, 8, 7),
        (6.90625, 4, 9, 8),
        (7.71875, 4, 10, 3),
        (7.5625, 4, 11, 5);
