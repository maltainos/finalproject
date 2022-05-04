package mz.gov.misau.sigsmi.ws.io.repository.filter.consultas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;

import mz.gov.misau.sigsmi.ws.io.metamodel.ConsultaPosPartoEntity_;
import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPosPartoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;

public class ConsultaPosPartoRepositoryImpl implements ConsultaPosPartoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	private CriteriaBuilder builder;
	private CriteriaQuery<ConsultaPosPartoEntity> criteria;
	private Root<ConsultaPosPartoEntity> root;
	private TypedQuery<ConsultaPosPartoEntity> query;
	
	public void initConsultaPosPartoRepositoryImpl() {
		
		builder = manager.getCriteriaBuilder();
		criteria = builder.createQuery(ConsultaPosPartoEntity.class);
		root = criteria.from(ConsultaPosPartoEntity.class);
	}

	@Override
	public List<ConsultaPosPartoEntity> filtrar(ConsultaFilter filter, Pageable pageable) {
		
		initConsultaPosPartoRepositoryImpl();
		
		Predicate[] predicates = criarRestricoes(root, builder, filter);
		criteria.where(predicates);

		query = manager.createQuery(criteria);

		return query.getResultList();
	}

	private Predicate[] criarRestricoes(Root<ConsultaPosPartoEntity> root, CriteriaBuilder builder,
			ConsultaFilter filter) {


		List<Predicate> predicates = new ArrayList<>();

		if (filter.getIniciarEm() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(
							root.get(ConsultaPosPartoEntity_.dataConsulta), filter.getIniciarEm()));
		}

		if (filter.getTerminarEm() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(
							root.get(ConsultaPosPartoEntity_.dataConsulta), filter.getTerminarEm()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
