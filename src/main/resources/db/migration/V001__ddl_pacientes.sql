CREATE TABLE `pacientes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `paciente_id` varchar(30) NOT NULL,
  `primeiro_nome` varchar(30) NOT NULL,
  `sobre_nome` varchar(30) NOT NULL,
  `anos_idade` tinyint DEFAULT NULL,
  `grupo_sanguineo` varchar(255) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `nome_mae` varchar(35) NOT NULL,
  `nome_pai` varchar(35) NOT NULL,
  `ocupacao_mae` varchar(35) DEFAULT NULL,
  `ocupacao_pai` varchar(35) DEFAULT NULL,
  `estado_civil` varchar(40) NOT NULL,
  `nome_pessoa_referencia` varchar(30) NOT NULL,
  `telefone_emergencia` varchar(13) NOT NULL,
  `cargo` varchar(255) DEFAULT NULL,
  `local_trabalho` varchar(255) DEFAULT NULL,
  `profissao` varchar(255) DEFAULT NULL,
  `criado_em` date NOT NULL,
  `atualizado_em` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
	
	
	
	
	
	
	
