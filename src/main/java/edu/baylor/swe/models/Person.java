package edu.baylor.swe.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class Person {
//	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
//	@JsonFormat(pattern="dd/MM/yyyy")
	private Date birthdate;

	@Formula("(YEAR(CURDATE()) - YEAR(BIRTHDATE))")
	private int age;

//	@JsonProperty("Email Address")
	@NotBlank
	@Email(message = "Invalid Email")
	private String email;

//	@JsonProperty(value = )
	@NotBlank
	private String name;

	private String university;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "managers")
	private List<Contest> managedContest = new ArrayList<>();

	@JsonIgnore
	@NotNull 
	@ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
	private List<Team> memberedTeams = new ArrayList<>();

	@JsonIgnore
	@NotNull
	@OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
	private List<Team> coachedTeams = new ArrayList<>();
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("\n");
		string.append("--- Person Description ---");
		string.append("\n");
		string.append("Name: " + name);
		string.append("\n");
		string.append("Age: " + age);
		string.append("\n");
		string.append("Email: " + email);
		string.append("\n");
		string.append("University: " + university);
		string.append("\n");
		string.append("--- Person END Description ---");
		string.append("\n");
		return string.toString();
	}
	
	public int getAge() {
		return calculateAge(getBirthdate());
	}
	
	private int calculateAge(Date birthDate) {            
	    Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int currentYear = now.get(Calendar.YEAR);
		
		Calendar birthCalender = Calendar.getInstance();
		birthCalender.setTime(birthDate);
		int birthYear = birthCalender.get(Calendar.YEAR);
		return currentYear - birthYear;
	}
}
