package edu.baylor.swe.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.baylor.swe.models.Contest;

//@Component("beforeSaveContestValidator")
@Component
public class ContestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return Contest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Contest contest = (Contest) target;
        if (!checkCapacity(contest)) {
            errors.rejectValue("capacity", "contest.capacity");
        }
        
//        if (!checkWritable(contest)) {
//        	errors.rejectValue("isWritable", "contest.isWritable");
//        }
	} 
	
	private boolean checkCapacity(Contest contest) {
		return contest.getNotCancelledTeams().size() <= contest.getCapacity();
	}
	
//	private boolean checkWritable(Contest contest) {
//		return contest.isWritable();
//	}
}
