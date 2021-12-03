package edu.baylor.swe.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.baylor.swe.models.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	@Query("select new edu.baylor.swe.repositories.StudentAgeGroup(p.age as age2, " + "count(p) as count2) "
			+ "from Person p " + "where p.university is NOT NULL " + "group by p.age")
	List<StudentAgeGroup> groupByAge();
	Optional<Person> findByEmail(String email);
	
	@Query("SELECT p FROM Person p " + 
		       "JOIN p.memberedTeams m ON m.attendedContest.id = :contestId AND p.name = :name AND p.email = :email")
	Optional<Person> findByNameAndEmail(@Param("name") String name, @Param("email") String email, @Param("contestId") Long contestId);
	
//	@Query("SELECT p.age FROM Person p WHERE p.id = :id")
//	Long findAgeById(@Param("id") Long id);
}
