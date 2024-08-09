-- student_learning.subject definition

CREATE TABLE `subject` (
  `credits` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- student_learning.`user` definition

CREATE TABLE `user` (
  `dob` date DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `gender` enum('MALE','FEMALE','OTHERS') DEFAULT NULL,
  `role` enum('STUDENT','LECTURER','ACADEMIC_AFFAIR','ADMIN') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- student_learning.academic_details definition

CREATE TABLE `academic_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `admission_year` varchar(255) DEFAULT NULL,
  `faculty` varchar(255) DEFAULT NULL,
  `klass` varchar(255) DEFAULT NULL,
  `student_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1if67idjafn172qfl29wuo8y3` (`student_id`),
  UNIQUE KEY `UK_friaogorhl0f7mhve5glb6u5n` (`student_code`),
  CONSTRAINT `FKnl9yjwnqa1tqjc74x275xhnfw` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- student_learning.assessment definition

CREATE TABLE `assessment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL,
  `weight` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2mbr7j6xr3m5pki41mp9479vi` (`subject_id`),
  CONSTRAINT `FK2mbr7j6xr3m5pki41mp9479vi` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- student_learning.course definition

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lecturer_id` int(11) DEFAULT NULL,
  `semester_no` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `academic_year` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtkf1pbf7lcb4a21m1rt361atd` (`lecturer_id`),
  KEY `FKm1expnaas0onmafqpktmjixnx` (`subject_id`),
  CONSTRAINT `FKm1expnaas0onmafqpktmjixnx` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `FKtkf1pbf7lcb4a21m1rt361atd` FOREIGN KEY (`lecturer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- student_learning.enrollment definition

CREATE TABLE `enrollment` (
  `avg_grade` float DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbhhcqkw1px6yljqg92m0sh2gt` (`course_id`),
  KEY `FK37r2i259m5gtd7pr6j1vx9x8d` (`student_id`),
  CONSTRAINT `FK37r2i259m5gtd7pr6j1vx9x8d` FOREIGN KEY (`student_id`) REFERENCES `academic_details` (`id`),
  CONSTRAINT `FKbhhcqkw1px6yljqg92m0sh2gt` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- student_learning.assessment_value definition

CREATE TABLE `assessment_value` (
  `assessment_id` int(11) DEFAULT NULL,
  `enrollment_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfrq8do282rqbbn6twb10c5lpk` (`assessment_id`),
  KEY `FK3p55sdxgup94ic9xgan27i2jr` (`enrollment_id`),
  CONSTRAINT `FK3p55sdxgup94ic9xgan27i2jr` FOREIGN KEY (`enrollment_id`) REFERENCES `enrollment` (`id`),
  CONSTRAINT `FKfrq8do282rqbbn6twb10c5lpk` FOREIGN KEY (`assessment_id`) REFERENCES `assessment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;