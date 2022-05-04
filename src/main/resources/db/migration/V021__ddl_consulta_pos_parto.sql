CREATE TABLE `consulta_pos_parto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_pvt` VARCHAR(30) NOT NULL UNIQUE,
  `consulta_pos_parto_id` varchar(30) NOT NULL,
  `observacao` text,
  `recomendacao` text,
  `peso_mae` decimal(5.2) NOT NULL,
  `tomou_vitaminaa` tinyint(1) DEFAULT 0,
  `tomou_sal_ferroso` tinyint(1) DEFAULT 0,
  `data_consulta` date not null,
  `gravidez_id` bigint not null,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cpp_id` (`consulta_pos_parto_id`),
  KEY `FK_gravidez_id` (`gravidez_id`),
  CONSTRAINT `FK_gravidez_id` FOREIGN KEY (`gravidez_id`) REFERENCES `gravidezes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
