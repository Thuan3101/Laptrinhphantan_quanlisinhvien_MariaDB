package org.dev.iuh.server.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.dev.iuh.common.enums.Gender;
import org.dev.iuh.common.enums.UserRole;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "User.findAllStudents", query = "SELECT u FROM User u WHERE u.role = :role"),
		@NamedQuery(name = "User.login", query = "SELECT u FROM User u WHERE u.email = :email and u.password = :password")
})
@Table(name = "user")
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 823163718038191778L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private LocalDate dob;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column(name = "phone_number", unique = true)
	private String phoneNumber;
	@Column(unique = true)
	private String email;
	private String address;
	private String password;
	@Enumerated(EnumType.STRING)
	private UserRole role;

	public User() {

	}

	// ------------------- Getter and Setter --------------------
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
