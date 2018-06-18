package com.concrete.desafiojava.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concrete.desafiojava.domain.orm.ApplicationUser;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

	Optional<ApplicationUser> findByUuid(String uuid);
	Optional<ApplicationUser> findByEmail(String email);
}
