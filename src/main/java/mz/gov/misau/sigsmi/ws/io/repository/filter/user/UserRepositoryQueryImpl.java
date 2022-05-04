package mz.gov.misau.sigsmi.ws.io.repository.filter.user;

public class UserRepositoryQueryImpl implements UserRepositoryQuery{
	
	/*
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void selectMultipleValueAndMapToDTO() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<UserCriteriaDTO> criteriaQuery = builder.createQuery(UserCriteriaDTO.class);
		
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		
		Path<Object> userId = root.get("userId");
		Path<Object> email = root.get("email");
		Path<Object> firstName = root.get("firstName");
		Path<Object> lastName = root.get("lastName");
		
		criteriaQuery.select(builder.construct(UserCriteriaDTO.class, userId, email, firstName, lastName));
		
		TypedQuery<UserCriteriaDTO> query = manager.createQuery(criteriaQuery);
		
		List<UserCriteriaDTO> users = query.getResultList();
		
		users.forEach(System.out::println);
	}
	
	@Override
	public void selectMultipleValues() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		
		
		Path<Object> emailPath = root.get("email");
		Path<Object> lastNamePath = root.get("lastName");
		
		criteriaQuery.multiselect(emailPath, lastNamePath);
		
		TypedQuery<Object[]> query = manager.createQuery(criteriaQuery);
		
		for(Object[] obj : query.getResultList()) {
			System.out.println("Email :"+ (String) obj[0]);
			System.out.println("Last Name :"+ (String) obj[1]);
		}
		
	}
	
	@Override
	public void selectionObject() {//Multiple values using builder
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		
		Path<Object> userEmailPath = root.get("email");
		Path<Object> userFirstNamePath = root.get("firstName");
		
		criteriaQuery.select(builder.array(userEmailPath, userFirstNamePath));
		
		TypedQuery<Object[]> query = manager.createQuery(criteriaQuery);
		
		for(Object[] obj : query.getResultList()) {
			System.out.println("Email :"+ (String) obj[0]);
			System.out.println("FirstName :"+ (String) obj[1]);
		}
	}
	
	@Override
	public void selectionField() {//select single value
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<String> criteriaQuery = builder.createQuery(String.class);
		
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		
		criteriaQuery.select(root.get("email"));
		
		TypedQuery<String> query = manager.createQuery(criteriaQuery);
		
		List<String> emailList = query.getResultList();
		
		emailList.forEach(System.out::println);
	}

	@Override
	public List<UserEntity> countLastWeekCreateUsers() {//select users
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<UserEntity> criteriaQuery = builder.createQuery(UserEntity.class);
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		
		criteriaQuery.select(root);
		
		Predicate[] predicates = createPredicates(root, builder);
		criteriaQuery.where(predicates);
		
		TypedQuery<UserEntity> query = manager.createQuery(criteriaQuery);
		
		List<UserEntity> users = query.getResultList();
		
		//users.forEach(System.out::println);
		//System.out.println("Resultados: "+query.getResultList().size());
		
		return users;
	}
	
	public Predicate[] createPredicates(Root<UserEntity> root, CriteriaBuilder builder) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		LocalDate stopToday = LocalDate.now();
		
		LocalDate begin = stopToday.minusDays(6);
		predicates.add(builder.greaterThanOrEqualTo(
				root.get("createdOn"), begin));
		
		System.out.println("Begin: "+ begin+" Stop :"+stopToday);
		predicates.add(builder.lessThanOrEqualTo(root.get("createdOn"), stopToday));
		
		//predicates.add(builder.countDistinct(root.get("createdOn")));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	
	@Override
	public void getLastWeekReport() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
		
		Path<Object> createdOn = root.get("createdOn");
		
		criteriaQuery.multiselect(createdOn, builder.count(root.get("id")));
		
		Predicate[] predicates = restricaoSemanal(builder, root);
		criteriaQuery.where(predicates);
		
		criteriaQuery.groupBy(root.get("createdOn"));
		criteriaQuery.orderBy(builder.asc(root.get("createdOn")));
		
		TypedQuery<Object[]> query = manager.createQuery(criteriaQuery);
		
		List<Object[]> results = query.getResultList();
		
		System.out.println("Day \t\t\tcount");
		for(Object[] result : results) {
			LocalDate date = (LocalDate) result[0];
			System.out.printf("%s\t\t  %d\n",  date.getDayOfWeek(), result[1]);
		}
	}
	
	private Predicate[] restricaoSemanal(CriteriaBuilder builder, Root<UserEntity> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		LocalDate lastDay = LocalDate.now();
		LocalDate firstDay = lastDay.minusDays(6);
		predicates.add(builder.greaterThanOrEqualTo(root.get("createdOn"), firstDay));
		predicates.add(builder.lessThanOrEqualTo(root.get("createdOn"), lastDay));
	
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	*/
}
