ALTER TABLE consultas_pre_natais CHANGE COLUMN observacoes observacoes text;
ALTER TABLE consultas_pre_natais CHANGE COLUMN recomendacoes recomendacoes text;
ALTER TABLE consultas_pre_natais ADD COLUMN cpnId VARCHAR(35) NOT NULL UNIQUE;
