CREATE TABLE `consultas_pre_natais` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_pvt` varchar(255) DEFAULT NULL,
  `consulta_pre_natal_id` varchar(30) NOT NULL,
  `data_consulta` date NOT NULL,
  `data_provavel_de_parto` date DEFAULT NULL,
  `observacoes` varchar(255) DEFAULT NULL,
  `peso_mae` float DEFAULT NULL,
  `recomendacoes` varchar(255) DEFAULT NULL,
  `gravidez_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1ygtaq8mgycw9972bu3c67a3p` (`consulta_pre_natal_id`),
  KEY `FKjpp5rgwkvrj4pusmeo5myx3fv` (`gravidez_id`),
  CONSTRAINT `FKjpp5rgwkvrj4pusmeo5myx3fv` FOREIGN KEY (`gravidez_id`) REFERENCES `gravidezes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
