package edu.baylor.swe.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.baylor.swe.models.AmazonApplication;
import edu.baylor.swe.models.AmazonApplication.ApplicationStatus;
import edu.baylor.swe.models.Applicant;
import edu.baylor.swe.models.Contest;
import edu.baylor.swe.models.Applicant.Gender;
import edu.baylor.swe.models.AppliedApplication;
import edu.baylor.swe.repositories.AmazonApplicationRepository;
import edu.baylor.swe.repositories.ApplicantRepository;
import edu.baylor.swe.repositories.AppliedApplicationRepository;
import edu.baylor.swe.repositories.ContestRepository;
import edu.baylor.swe.validations.ContestValidator;
import edu.baylor.swe.validations.TeamValidator;

@Service
public class AmazonService {

	@Autowired
	private AmazonApplicationRepository applicationRepo;
	
	@Autowired
	private AppliedApplicationRepository appliedApplicationRepo;
	
	@Autowired
	private ApplicantRepository applicantRepo;
	
	
	public AmazonService(AmazonApplicationRepository applicationRepo, ApplicantRepository applicantRepo, AppliedApplicationRepository appliedApplicationRepo) {
		this.applicantRepo = applicantRepo;
		this.applicationRepo = applicationRepo;
		this.appliedApplicationRepo = appliedApplicationRepo;
	}
	
	public List<AmazonApplication> allApplications() {
		Iterable<AmazonApplication> allApplications = applicationRepo.findAll();
		return ((List<AmazonApplication>) IteratorUtils.toList(allApplications.iterator()))
				.stream()
//			    .filter(c -> !c.isDeleted())
			    .collect(Collectors.toList());
	}
	
	public List<AppliedApplication> allAppliedApplications() {
		Iterable<AppliedApplication> allApplications = appliedApplicationRepo.findAll();
		return ((List<AppliedApplication>) IteratorUtils.toList(allApplications.iterator()))
				.stream()
//			    .filter(c -> !c.isDeleted())
			    .collect(Collectors.toList());
	}

	public Optional<AmazonApplication> registration(Long applicationId, Applicant applicant) {
		Optional<AmazonApplication> application = applicationRepo.findById(applicationId);
		if (application.isPresent()) {
			AppliedApplication app = new AppliedApplication();
			app.setApplicant(applicant);
			app.setApplication(application.get());
			app.setInterviewNote("aas");
			application.get().addApplicant(app);
			if (this.isValid(app)) {
				return Optional.of(applicationRepo.save(application.get()));
			}
			
		}
		return Optional.empty();
	}
	
	private boolean isValid( AppliedApplication appliedApplication) {
		
		if (validateGender(appliedApplication.getApplicant()) 
				|| appliedApplication.getApplicant().getPassword().length() > 9 ) {
			appliedApplication.setStatus(ApplicationStatus.approved);
			
		} 
		
		if (appliedApplication.getApplicant().getGender() == Gender.female 
				&& appliedApplication.getStatus() == ApplicationStatus.approved) {
			appliedApplication.setStatus(ApplicationStatus.invited);
		}
		
		if (appliedApplication.getStatus() == ApplicationStatus.invited 
				&& (appliedApplication.getInterviewNote() == null || appliedApplication.getInterviewNote().isEmpty())) {
			return false;
		}
		
		int numberApplied = appliedApplication.getApplication().getAppliedApplications().size();
		
		if (numberApplied > 15) {
			appliedApplication.setStatus(ApplicationStatus.pending);
		}
		
		return true;
		
	}

	public Optional<AmazonApplication> getApplication(Long applicationId) {
		Optional<AmazonApplication> application = applicationRepo.findById(applicationId);
		if (application.isPresent()) {
			return Optional.of(applicationRepo.save(application.get()));
		}
		return Optional.empty();
	}
	
	private boolean validateGender(Applicant applicant) {
		boolean acceptedMale = applicant.getGender() == Gender.male && applicant.getAge() < 55;
		boolean acceptedFemale = applicant.getGender() == Gender.female && applicant.getAge() < 57;		
		return acceptedMale || acceptedFemale;
	}
	
}
