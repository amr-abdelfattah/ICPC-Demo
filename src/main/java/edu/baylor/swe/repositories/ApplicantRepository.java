package edu.baylor.swe.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.baylor.swe.models.Applicant;
import edu.baylor.swe.models.Contest;

public interface ApplicantRepository extends CrudRepository<Applicant, Long> {

}
