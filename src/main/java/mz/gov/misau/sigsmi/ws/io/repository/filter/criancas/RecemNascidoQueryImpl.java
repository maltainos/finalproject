package mz.gov.misau.sigsmi.ws.io.repository.filter.criancas;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mz.gov.misau.sigsmi.ws.io.model.entity.RecemNascidoEntity;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoMonthReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoWeekReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoYearReport;

public class RecemNascidoQueryImpl implements RecemNascidoQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public RecemNascidoWeekReport getLastWeekReport() {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<RecemNascidoEntity>root = criteriaQuery.from(RecemNascidoEntity.class);
		
		Predicate[] predicates = lastWeekPredicate(builder, root);
		
		Path<Object> data = root.get("createOn");
		
		criteriaQuery.multiselect(data, builder.count(root.get("id")));
		
		criteriaQuery.where(predicates);
		
		criteriaQuery.groupBy(data);
		
		criteriaQuery.orderBy(builder.asc(data));
		
		TypedQuery<Object[]> query = manager.createQuery(criteriaQuery);
		
		RecemNascidoWeekReport returnValue = new RecemNascidoWeekReport();
		int weekOfYear = 1;
		List<String> dayOfWeek = new ArrayList<>();
		List<Long> dailyDataWeek = new ArrayList<>();
		
		for(Object[] obj : query.getResultList()) {
			LocalDate date = (LocalDate) obj[0];
			weekOfYear = date.getDayOfYear() / 7;
			dayOfWeek.add(date.getDayOfWeek().toString());
			dailyDataWeek.add((Long) obj[1]);
		}
		
		returnValue.setWeekOfYear(weekOfYear);
		returnValue.setDayOfWeeek(dayOfWeek);
		returnValue.setCountData(dailyDataWeek);
		
		return returnValue;
	}

	private Predicate[] lastWeekPredicate(CriteriaBuilder builder, Root<RecemNascidoEntity> root) {

		List<Predicate> predicates = new ArrayList<>();

		LocalDate lastDay = LocalDate.now();
		LocalDate firstDay = lastDay.minusDays(6);
		
		predicates.add(builder.greaterThanOrEqualTo(root.get("createOn"), firstDay));
		predicates.add(builder.lessThanOrEqualTo(root.get("createOn"), lastDay));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public RecemNascidoMonthReport getLastMonthReport() {
		
		RecemNascidoMonthReport returnValue = new RecemNascidoMonthReport();
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);
		
		Root<RecemNascidoEntity> root = criteriaQuery.from(RecemNascidoEntity.class);
		
		Predicate[] predicates = createLastMonthPredicates(builder, root);
		
		Path<Object> createdOnPath = root.get("createOn");
	
		criteriaQuery.multiselect(createdOnPath, builder.count(root.get("id")));
		criteriaQuery.where(predicates);
		criteriaQuery.groupBy(createdOnPath);
		criteriaQuery.orderBy(builder.asc(createdOnPath));
		TypedQuery<Object[]> result = manager.createQuery(criteriaQuery);
		
		List<Object[]> objectDTO = result.getResultList();
		
		List<Integer> days = new ArrayList<>();
		List<Long> datas = new ArrayList<>();
		
		objectDTO.forEach(object -> {
			LocalDate date = (LocalDate) object[0];
			returnValue.setMonthName(date.getMonth().toString());
			days.add(date.getDayOfMonth());
			datas.add((Long)object[1]);
		});
		
		returnValue.setDay(days);
		returnValue.setDailyData(datas);
		
		return returnValue;
	}

	private Predicate[] createLastMonthPredicates(CriteriaBuilder builder, Root<RecemNascidoEntity> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		LocalDate currentMonthStart = LocalDate.now().withDayOfMonth(1);
		
		LocalDate currentMonthEnd = currentMonthStart.plusDays(
				currentMonthStart.getMonth().maxLength()).minusDays(1);
		
		predicates.add(builder.greaterThanOrEqualTo(root.get("createOn"), currentMonthStart));
		
		predicates.add(builder.lessThanOrEqualTo(root.get("createOn"), currentMonthEnd));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RecemNascidoYearReport getLastYearReport(LocalDate previousYear, LocalDate currentYear) {
		
		RecemNascidoYearReport returnValue = new RecemNascidoYearReport();
		
		returnValue.setYear(currentYear.getYear());
		
		List<Object[]> yearReports = manager.createNamedStoredProcedureQuery("getRecemNascidoAnualProcedure")
					.setParameter("currentYearBegin", previousYear)
					.setParameter("currentYearEnd", currentYear)
					.getResultList();
		
		List<String> monthNames = new ArrayList<>();
		List<BigInteger> monthData = new ArrayList<>();
		yearReports.forEach(value -> {
			Date date = (Date) value[0];
			LocalDate month = date.toLocalDate();
			monthNames.add(month.getMonth().toString());
			monthData.add((BigInteger) value[1]);
		});
		
		returnValue.setMonthName(monthNames);
		returnValue.setMonthData(monthData);
		return returnValue;
	}
}
