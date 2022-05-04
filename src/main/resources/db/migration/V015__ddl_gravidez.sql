CREATE TABLE `gravidezes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gravidez_id` varchar(30) NOT NULL,
  `gravidez_status` varchar(120) NOT NULL,
  `aborto` varchar(255) DEFAULT NULL,
  `local_parto` varchar(255),
  `data_engravida` datetime DEFAULT NULL,
  `data_aborto` datetime DEFAULT NULL,
  `data_parto` datetime DEFAULT NULL,
  `paciente_id` bigint NOT NULL,
  `data_atualizacao` datetime(6) DEFAULT NULL,
  `data_registro` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `paciente_id` (`paciente_id`),
  CONSTRAINT `gravidezes_ibfk_1` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
