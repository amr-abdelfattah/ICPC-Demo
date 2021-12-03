package edu.baylor.swe.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import edu.baylor.swe.models.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
//	@Query("select t from Team t WHERE t.attendedContest.id = :id")
	List<Team> findByAttendedContest_Id(Long id);
}
