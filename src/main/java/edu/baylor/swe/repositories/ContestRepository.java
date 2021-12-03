package edu.baylor.swe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import edu.baylor.swe.models.Contest;

public interface ContestRepository extends CrudRepository<Contest, Long> {
	Optional<Contest> findByName(String name);
}
