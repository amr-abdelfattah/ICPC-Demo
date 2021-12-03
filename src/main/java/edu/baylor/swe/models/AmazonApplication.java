package edu.baylor.swe.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.baylor.swe.models.Team.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class AmazonApplication implements Serializable {
	private static final long serialVersionUID = 1L;

//	@JsonIgnore
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date createdDate;
	
	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AppliedApplication> appliedApplications = new ArrayList<>();
	
	public void addApplicant(AppliedApplication applicant) {
		appliedApplications.add(applicant);
		applicant.setApplication(this);
	}
	 
//	public List<Team> getNotCancelledTeams() {
//		return attendedTeams.stream()
//				.filter(team -> team.getState() != State.Cancelled)
//				.collect(Collectors.toList());
//	}
//	
	public static enum ApplicationStatus {
		pending, declined, approved, invited;
	}
}