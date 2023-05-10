CREATE TABLE `department` (
  `id` varchar(255) NOT NULL,
  `budget` bigint(20) NOT NULL,
  `created_date` bigint(20) NOT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `modified_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `employee` (
  `id` varchar(255) NOT NULL,
  `created_date` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `modified_date` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `department_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbejtwvg9bxus2mffsm3swj3u9` (`department_id`),
  CONSTRAINT `FKbejtwvg9bxus2mffsm3swj3u9` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;