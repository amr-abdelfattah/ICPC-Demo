package edu.baylor.swe.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Applicant {
//	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String password;
	
	private String experience;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
////	@JsonFormat(pattern="dd/MM/yyyy")
//	private Date birthdate;

//	@Formula("(YEAR(CURDATE()) - YEAR(BIRTHDATE))")
	private int age;
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<AppliedApplication> appliedApplications = new ArrayList<>();
	
	
 
	public static enum Gender {
		male, female
	}
//	@JsonProperty("Email Address")
	
	
//	public String toString() {
//		StringBuilder string = new StringBuilder();
//		string.append("\n");
//		string.append("--- Person Description ---");
//		string.append("\n");
//		string.append("Name: " + name);
//		string.append("\n");
//		string.append("Age: " + age);
//		string.append("\n");
//		string.append("Email: " + email);
//		string.append("\n");
//		string.append("University: " + university);
//		string.append("\n");
//		string.append("--- Person END Description ---");
//		string.append("\n");
//		return string.toString();
//	}
	
	
	
}