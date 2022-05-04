CREATE TABLE `historia_gravidez` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `gravidez_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs659vsx2cisa515gm04twljeu` (`gravidez_id`),
  CONSTRAINT `FKs659vsx2cisa515gm04twljeu` FOREIGN KEY (`gravidez_id`) REFERENCES `gravidezes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
