package com.concrete.desafiojava.domain.orm;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Wither
public class ApplicationUser {

	@Id
	@GeneratedValue
	private Long id;
	private String uuid;
	private String name;
	private String email;
	private String password;
	private Date created;
	private Date modified;
	private Date lastLogin;
	private String token;
	
	@Transient
	private String noCryptToken;

}
