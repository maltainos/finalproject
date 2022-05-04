package mz.gov.misau.sigsmi.ws.io.repository.filter.pacientes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GravidezRepositoryImpl implements GravidezRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	/*public List<PacienteHistorialClinica> list(String pacienteId){
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PacienteEntity> criteria = builder.createQuery(PacienteEntity.class);
		
		Root<PacienteEntity> root = criteria.from(PacienteEntity.class);
		
		Predicate[] predicates = buscarGravidezesSaudavel(root, builder);
		criteria.where(predicates);
		
		TypedQuery<PacienteEntity> query = manager.createQuery(criteria);
		
		return new ArrayList<>();
	}*/

	/*private Predicate[] buscarGravidezesSaudavel(Root<PacienteEntity> root, CriteriaBuilder builder) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		predicates.add(builder.);
		
		return null;
	}*/

}
