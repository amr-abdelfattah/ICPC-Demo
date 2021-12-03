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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class Team {

//	@JsonIgnore
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	private int rank;

	@Enumerated(EnumType.STRING)
	private State state = State.Pending;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "team_members", joinColumns = @JoinColumn(name = "team_name"), inverseJoinColumns = @JoinColumn(name = "member_id"))
//	@Size(min = 1, max = 3, message = "You must choose from 1 to 3 members")
	private List<Person> members = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "coach_id")
	private Person coach;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "contest_id")
	@NonNull
	@JsonIgnore
	private Contest attendedContest;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "clone_source_id")
	@JsonIgnore
	private Team cloneSource; 

	@OneToOne(mappedBy = "cloneSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Team cloneDestination;

	public Team(Team fromTeam, String newName) {
		this.name = newName;
		this.members = fromTeam.members;
		this.coach = fromTeam.coach;
		this.cloneSource = fromTeam;
		this.attendedContest = fromTeam.attendedContest;
		 this.rank = fromTeam.rank;
	}

	public void addMember(Person member) {
		members.add(member);
	}

	public enum State {
		Accepted, Pending, Cancelled
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("\n");
		string.append("--- Team Description ---");
		string.append("\n");
		string.append("Name: " + name);
		string.append("\n");
		string.append("Rank: " + rank);
		string.append("\n");
		string.append("State: " + state);
		string.append("\n");
		string.append("Attended Contest: " + attendedContest.getName());
		string.append("\n");
		if (cloneSource != null) {
			string.append("Cloned From: " + cloneSource.getName());
			string.append("\n");
		}
		string.append("--- Team END Description ---");
		string.append("\n");
		string.append("Members: " + members.size());
		string.append("\n");
		members.forEach(member -> string.append(member.toString()));
		string.append("Coach: " + coach.toString());
		string.append("\n");
		return string.toString();

	}
}
