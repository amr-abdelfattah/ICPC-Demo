package edu.baylor.swe.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.baylor.swe.models.AmazonApplication;
import edu.baylor.swe.models.AppliedApplication;

public interface AppliedApplicationRepository extends CrudRepository<AppliedApplication, Long> {

}
