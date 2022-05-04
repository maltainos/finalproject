CREATE TABLE `vacinas_recem_nascido` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_vacina` date NOT NULL,
  `vacina` varchar(255) NOT NULL,
  `recem_nascido_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsnalftsfeodx2hlng849amj6p` (`recem_nascido_id`),
  CONSTRAINT `FKsnalftsfeodx2hlng849amj6p` FOREIGN KEY (`recem_nascido_id`) REFERENCES `recem_nascido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
