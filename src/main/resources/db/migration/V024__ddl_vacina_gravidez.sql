CREATE TABLE `vacinas_gravidez` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_vacina` date NOT NULL,
  `dose_vacina` varchar(255) DEFAULT NULL,
  `local_vacina` varchar(255) DEFAULT NULL,
  `vacina_id` varchar(30) NOT NULL,
  `gravidez_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9ycu7ygowi541r3a98t7rqbb4` (`vacina_id`),
  KEY `FK1p05sq9nfrdqhlc329ih0e8i` (`gravidez_id`),
  CONSTRAINT `FK1p05sq9nfrdqhlc329ih0e8i` FOREIGN KEY (`gravidez_id`) REFERENCES `gravidezes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
