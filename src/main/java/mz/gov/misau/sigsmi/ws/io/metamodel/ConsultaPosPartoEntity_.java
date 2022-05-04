package mz.gov.misau.sigsmi.ws.io.metamodel;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPosPartoEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.GravidezEntity;

@Generated(value = {"org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor"})
@StaticMetamodel(ConsultaPosPartoEntity.class)
public abstract class ConsultaPosPartoEntity_ {

	public static volatile SingularAttribute<ConsultaPosPartoEntity, String> consultaPosPartoId;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, Long> id;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, String> codigoPvt;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, String> recomendacao;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, Float> pesoMae;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, LocalDate> dataConsulta;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, Boolean> tomouVitaminaA;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, Boolean> tomouSalFerroso;
	public static volatile SingularAttribute<ConsultaPosPartoEntity, GravidezEntity> gravidez;

	public static final String CONSULTA_POS_PARTO_ID = "consultaPosPartoId";
	public static final String DATA_CONSULTA = "dataConsulta";
}
