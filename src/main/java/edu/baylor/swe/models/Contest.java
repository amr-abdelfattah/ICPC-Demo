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
import javax.validation.constraints.Size;

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
public class Contest implements Serializable {
	private static final long serialVersionUID = 1L;

//	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private Date date = new Date();

	private int capacity;

	@NotBlank(message = "Empty Name")
	private String name;

	private boolean isRegistrationAllowed;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date registrationFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date registrationTo;
	
	private boolean isWritable = true;
	
	private boolean isDeleted = false;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pre_contest_id")
	@JsonIgnore
	private Contest preliminaryContest;

	@OneToMany(mappedBy = "preliminaryContest", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Contest> contests = new ArrayList<>();

	@OneToMany(mappedBy = "attendedContest", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@Size(min = 1, message = "You must include at least 1 team")
//	@JsonIgnore
	private List<Team> attendedTeams = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "contest_manager", joinColumns = @JoinColumn(name = "contest_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
//	@Size(min = 1, message = "You must include at least 1 manager")
	private List<Person> managers = new ArrayList<>();

	public void addTeam(Team team) {
		attendedTeams.add(team);
		team.setAttendedContest(this);
	}

	public void addManager(Person manager) {
		this.managers.add(manager);
	}
	
	public List<Team> getNotCancelledTeams() {
		return attendedTeams.stream()
				.filter(team -> team.getState() != State.Cancelled)
				.collect(Collectors.toList());
	}
}
