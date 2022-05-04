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

import mz.gov.misau.sigsmi.ws.io.model.entity.ConsultaPreNatalEntity;
import mz.gov.misau.sigsmi.ws.io.repository.filter.ConsultaFilter;

public class ConsultaPreNatalRepositoryImpl implements ConsultaPreNatalRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	private CriteriaBuilder builder;
	private CriteriaQuery<ConsultaPreNatalEntity> criteria;
	private Root<ConsultaPreNatalEntity> root;
	private TypedQuery<ConsultaPreNatalEntity> query;

	private void initConsultaPreNatalRepositoryImpl(){

		builder = manager.getCriteriaBuilder();
		criteria = builder.createQuery(ConsultaPreNatalEntity.class);
		root = criteria.from(ConsultaPreNatalEntity.class);
	}

	private Predicate[] criarRestricoes(ConsultaFilter filter, CriteriaBuilder builder,
			Root<ConsultaPreNatalEntity> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (filter.getIniciarEm() != null) {

			predicates.add(builder.greaterThanOrEqualTo(root.get("dataProvavelDeParto"),
					filter.getIniciarEm()));
		}

		
		if (filter.getTerminarEm() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataProvavelDeParto"),
					filter.getTerminarEm()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public List<ConsultaPreNatalEntity> filtrar(ConsultaFilter filter) {

		initConsultaPreNatalRepositoryImpl();
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		query = manager.createQuery(criteria);
		
		List<ConsultaPreNatalEntity> returnValue = query.getResultList();
		return returnValue;
	}

	@Override
	public List<Integer> countDeathInTheFirstWeek(ConsultaFilter filter) {

		initConsultaPreNatalRepositoryImpl();
		Predicate[] predicates = contarPartosPrevistoParaMesCorrente(filter, builder, root);
		criteria.where(predicates);
		
		query = manager.createQuery(criteria);
		
		System.out.println(query.getFirstResult());
		
		return null;
	}

	private Predicate[] contarPartosPrevistoParaMesCorrente(ConsultaFilter filter, CriteriaBuilder builder,
			Root<ConsultaPreNatalEntity> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		//predicates.add(builder.count(root.get(ConsultaPreNatalEntity_.id)));
		
		predicates.add(builder.greaterThanOrEqualTo(root.get(
				"dataProvavelDeParto"), filter.getIniciarEm()));
		
		predicates.add(builder.lessThanOrEqualTo(root.get("dataProvavelDeParto"), filter.getTerminarEm()));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
