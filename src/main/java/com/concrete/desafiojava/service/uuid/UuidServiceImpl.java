package com.concrete.desafiojava.service.uuid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;

import lombok.Synchronized;

@Service
public class UuidServiceImpl implements IUuidService {

	@Autowired
	private ApplicationUserRepository userRepository;

	@Synchronized
	public String generateUuid() {

		String uuid = UUID.randomUUID().toString().replace("-", "");

		while (userRepository.findByUuid(uuid).isPresent()) {

			uuid = UUID.randomUUID().toString().replace("-", "");
			continue;
		}

		return uuid;

	}
}
