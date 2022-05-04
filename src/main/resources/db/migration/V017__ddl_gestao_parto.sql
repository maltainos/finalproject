CREATE TABLE `gestao_partos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `complicacoes_hemorragicas` tinyint(1) NOT NULL DEFAULT '0',
  `data_parto` datetime(6) NOT NULL,
  `gestao_parto_id` varchar(30) NOT NULL,
  `nado_vivo` tinyint(1) NOT NULL DEFAULT '1',
  `parto_cesariana` tinyint(1) NOT NULL DEFAULT '0',
  `parto_com_ventose` tinyint(1) NOT NULL DEFAULT '0',
  `remocao_manual_da_placeta` tinyint(1) NOT NULL DEFAULT '0',
  `gravidez_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_23jgpf61gijlohk006ex2jpic` (`gestao_parto_id`),
  KEY `FKdrmv2m010t5q4aw16i9cie08q` (`gravidez_id`),
  CONSTRAINT `FKdrmv2m010t5q4aw16i9cie08q` FOREIGN KEY (`gravidez_id`) REFERENCES `gravidezes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
