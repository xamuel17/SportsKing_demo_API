package com.sport.models;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	@JsonIgnore
	private String password;
	
	
	@NotBlank
	@Size(max = 25)
	 @Column( unique=true)
	private String phone;
	
	@Column
	private String photo;

    @Column
    private boolean isVerified;
    
   
	@Column
    private String verificationCode ;
	
	   
	@CreationTimestamp
	private Date created_at ;
	   
	@UpdateTimestamp
	private Date updated_at ;


	
	
	
	@Column(columnDefinition = "TEXT")
	@NotBlank
	private String interest;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	public User() {
	}
	
	
	public User(String username, String email, String phone,String password, String verificationCode, String interest) {
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.verificationCode = verificationCode;
		this.isVerified = false;
		this.interest = interest;
	}

	
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public Long getId() {
		return id;
	}


	


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public boolean isVerified() {
		return isVerified;
	}


	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}


	public String getVerificationCode() {
		return verificationCode;
	}


	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public String getInterest() {
		return interest;
	}


	public void setInterest(String interest) {
		this.interest = interest;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
