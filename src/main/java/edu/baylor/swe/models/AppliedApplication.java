package edu.baylor.swe.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.baylor.swe.models.AmazonApplication.ApplicationStatus;
import edu.baylor.swe.models.Applicant.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class AppliedApplication {
//	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	
	private String interviewNote;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "application_id")
	@NonNull
	@JsonIgnore
	private AmazonApplication application;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "applicant_id")
	@NonNull
//	@JsonIgnore
	private Applicant applicant;
	
	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AplicationTrace> traces = new ArrayList<>();
	
	public void addTrace(AplicationTrace trace) {
		traces.add(trace);
		trace.setApplication(this);
	}
}