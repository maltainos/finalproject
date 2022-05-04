package mz.gov.misau.sigsmi.ws.service.impl;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mz.gov.misau.sigsmi.ws.exception.resource.RecemNascidoFalecidoNaPrimeiraSemanaException;
import mz.gov.misau.sigsmi.ws.exception.resource.RecemNascidoNotFoundException;
import mz.gov.misau.sigsmi.ws.io.model.entity.RecemNascidoEntity;
import mz.gov.misau.sigsmi.ws.io.repository.RecemNascidoRepository;
import mz.gov.misau.sigsmi.ws.service.RecemNascidoService;
import mz.gov.misau.sigsmi.ws.shared.MyUtils;
import mz.gov.misau.sigsmi.ws.shared.dto.RecemNascidoDTO;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoMonthReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoWeekReport;
import mz.gov.misau.sigsmi.ws.ui.model.response.report.RecemNascidoYearReport;

@Service
@Transactional
public class RecemNascidoServiceImpl implements RecemNascidoService {

	@Autowired
	private MyUtils utility;

	private ModelMapper mapper;

	public RecemNascidoServiceImpl() {
		mapper = new ModelMapper();
	}

	@Autowired
	private RecemNascidoRepository recemNascidoRepository;

	@Override
	public List<RecemNascidoDTO> getRecemNascidos(int page, int limit) {

		Pageable pageable = PageRequest.of(page, limit);

		Page<RecemNascidoEntity> pageOfUsers = recemNascidoRepository.findAll(pageable);
		List<RecemNascidoEntity> recemNascidos = pageOfUsers.getContent();

		Type listType = new TypeToken<List<RecemNascidoDTO>>() {
		}.getType();

		List<RecemNascidoDTO> returnValue = mapper.map(recemNascidos, listType);
		return returnValue;
	}

	@Override
	public RecemNascidoDTO create(RecemNascidoEntity recemNascido) {

		recemNascido.setRecemNascidoId(utility.generateUrlResource(30));
		recemNascido.setCreateOn(LocalDate.now());

		recemNascido = recemNascidoRepository.save(recemNascido);

		RecemNascidoDTO returnValue = mapper.map(recemNascido, RecemNascidoDTO.class);

		return returnValue;
	}

	@Override
	public RecemNascidoDTO isDeathInFirstWeeky(String recemNascidoId, boolean isDeath) {

		RecemNascidoEntity recemNascido = findOne(recemNascidoId);

		if (recemNascido.isDeathFirstWeeky())
			throw new RecemNascidoFalecidoNaPrimeiraSemanaException(
					RecemNascidoFalecidoNaPrimeiraSemanaException.class.toString().replace("class", ""));

		recemNascido = recemNascidoRepository.isDeathInFirstWeeky(recemNascidoId, isDeath);
		RecemNascidoDTO returnValue = mapper.map(recemNascido, RecemNascidoDTO.class);
		return returnValue;
	}

	private RecemNascidoEntity findOne(String recemNascidoId) {

		Optional<RecemNascidoEntity> returnValue = recemNascidoRepository.findByRecemNascidoId(recemNascidoId);
		if (!returnValue.isPresent())
			throw new RecemNascidoNotFoundException(
					RecemNascidoNotFoundException.class.toString().replace("class", ""));

		return returnValue.get();
	}

	@Override
	public void getReportprocedure() {

	}

	@Override
	public RecemNascidoWeekReport getLastWeekReport() {
		RecemNascidoWeekReport returnValue = recemNascidoRepository.getLastWeekReport();
		return returnValue;//completeWeek(returnValue);
	}
	
	/*
	private RecemNascidoWeekReport completeWeek(RecemNascidoWeekReport week) {

		String[] weeks1 = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
		

		List<Long> weekData = new ArrayList<>();
		List<String> weekNames = new ArrayList<>();
		
		
		for (String weekName : weeks1) {
			boolean status = true;
			for (int i = 0; i < week.getDayOfWeeek().size(); i++) {
				if (weekName.equals(week.getDayOfWeeek().get(i))) {
					weekNames.add(weekName);
					weekData.add(week.getCountData().get(i));
					status = false;
				}
			}
			if (status) {
				weekData.add(0L);
				weekNames.add(weekName);
			}
		}

		RecemNascidoWeekReport retunValue = new RecemNascidoWeekReport();
		retunValue.setWeekOfYear(week.getWeekOfYear());
		retunValue.setDayOfWeeek(weekNames);
		retunValue.setCountData(weekData);

		return retunValue;
	}*/


	@Override
	public RecemNascidoMonthReport getLastMonthReport() {
		RecemNascidoMonthReport returnValue = recemNascidoRepository.getLastMonthReport();
		return completeDays(returnValue);
	}

	private RecemNascidoMonthReport completeDays(RecemNascidoMonthReport month) {

		Month month2 = Month.valueOf(month.getMonthName());
		List<Integer> days = new ArrayList<>();
		List<Long> monthDatas = new ArrayList<>();

		for (int i = 0; i < month2.maxLength(); i++) {
			days.add(i+1);
			boolean status = true;
			for (int j = 0; j < month.getDay().size(); j++) {
				if( i+1 == month.getDay().get(j)) {
					monthDatas.add(month.getDailyData().get(j));
					status = false;
				}
			}
			if (status) {
				monthDatas.add(0L);
			}
		}

		RecemNascidoMonthReport retunValue = new RecemNascidoMonthReport();
		retunValue.setMonthName(month2.toString());
		retunValue.setDay(days);
		retunValue.setDailyData(monthDatas);

		return retunValue;
	}

	@Override
	public List<RecemNascidoYearReport> compareCurrenteYearToPreviousYear() {

		LocalDate currentYearStart = LocalDate.of(LocalDate.now().getYear(), 1, 1);
		LocalDate currentYearEnd = currentYearStart.withMonth(12).withDayOfMonth(31);
		LocalDate previousYearStart = currentYearStart.minusYears(1);

		RecemNascidoYearReport reportCurrentYear = recemNascidoRepository.getLastYearReport(currentYearStart,
				currentYearEnd);
		RecemNascidoYearReport reportPreviousYear = recemNascidoRepository.getLastYearReport(previousYearStart,
				currentYearStart.minusDays(1));

		List<RecemNascidoYearReport> returnValue = new ArrayList<>();
		returnValue.add(completeMonths(reportCurrentYear));
		returnValue.add(completeMonths(reportPreviousYear));

		return returnValue;
	}

	private RecemNascidoYearReport completeMonths(RecemNascidoYearReport year) {

		String[] months = { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER",
				"OCTOBER", "NOVEMBER", "DECEMBER" };

		List<BigInteger> monthDatas = new ArrayList<>();

		for (String month : months) {
			boolean status = true;
			for (int i = 0; i < year.getMonthName().size(); i++) {
				if (month.equals(year.getMonthName().get(i))) {
					monthDatas.add(year.getMonthData().get(i));
					status = false;
				}
			}
			if (status) {
				monthDatas.add(BigInteger.ZERO);
			}
		}

		RecemNascidoYearReport retunValue = new RecemNascidoYearReport();
		retunValue.setYear(year.getYear());
		retunValue.setMonthName(Arrays.asList(months));
		retunValue.setMonthData(monthDatas);

		return retunValue;
	}

}
