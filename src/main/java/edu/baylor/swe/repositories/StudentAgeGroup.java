package edu.baylor.swe.repositories;

import lombok.Data;

@Data
public class StudentAgeGroup {
	private Long count;
	private int age;

	public StudentAgeGroup(int age, Long count) {
		this.age = age;
		this.count = count;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("\n");
		string.append("--- Student Age Group Description ---");
		string.append("\n");
		string.append("Age: " + age);
		string.append("\n");
		string.append("Count: " + count);
		string.append("\n");
		string.append("--- Student Age Group END Description ---");
		string.append("\n");
		return string.toString();
	}
}
