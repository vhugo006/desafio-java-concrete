package com.concrete.desafiojava.service.phone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.domain.orm.Phone;
import com.concrete.desafiojava.domain.repository.PhoneRepository;

@Service
public class PhoneServiceImpl implements IPhoneService {

	@Autowired
	private PhoneRepository phoneRepository;

	public List<Phone> findUserPhones(ApplicationUser user) {

		return phoneRepository.findByUser(user);
	}

	public List<Phone> savePhones(List<Phone> phoneListToSave) {

		return phoneRepository.saveAll(phoneListToSave);
	}
}
