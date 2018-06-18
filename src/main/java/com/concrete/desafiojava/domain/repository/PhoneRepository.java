package com.concrete.desafiojava.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concrete.desafiojava.domain.orm.Phone;
import com.concrete.desafiojava.domain.orm.ApplicationUser;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

	List<Phone> findByUser(ApplicationUser user);
}
