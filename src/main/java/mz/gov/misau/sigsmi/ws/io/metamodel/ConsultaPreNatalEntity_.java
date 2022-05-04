package mz.gov.misau.sigsmi.ws.io.metamodel;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.GravidezEntity;

@Generated(value = {"org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor"})
@StaticMetamodel(ConsultaPreNatalEntity.class)
public abstract class ConsultaPreNatalEntity_ {

	public static volatile SingularAttribute<ConsultaPreNatalEntity, String> consultaPreNatalId;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, Long> id;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, String> codigoPvt;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, String> observacao;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, String> recomendacao;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, Float> pesoMae;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, LocalDate> dataConsulta;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, LocalDate> dataProvavelDeParto;
	public static volatile SingularAttribute<ConsultaPreNatalEntity, GravidezEntity> gravidez;
	
	public static final String CONSULTA_POS_PARTO_ID = "consultaPosPartoId";
	public static final String DATA_PROVAVEL_PARTO = "dataProvavelDeParto";
	public static final String DATA_CONSULTA = "dataConsulta";
	public static final String ID = "id";
}
