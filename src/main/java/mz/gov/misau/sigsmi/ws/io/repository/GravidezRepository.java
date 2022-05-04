package mz.gov.misau.sigsmi.ws.io.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import mz.gov.misau.sigsmi.ws.io.model.entity.GravidezEntity;
import mz.gov.misau.sigsmi.ws.io.model.entity.PacienteEntity;

@Repository
public interface GravidezRepository extends PagingAndSortingRepository<GravidezEntity, Long> {

	public List<GravidezEntity> findByPaciente(PacienteEntity paciente);

	public Optional<GravidezEntity> findByGravidezId(String gravidezId);

	@Query("UPDATE GravidezEntity g SET g.enable = ?1 WHERE g.gravidezId = ?2")
	@Modifying
	public GravidezEntity isDone(boolean done, String gravidezId);
}
