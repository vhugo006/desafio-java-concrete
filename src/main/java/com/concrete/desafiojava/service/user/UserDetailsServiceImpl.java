package com.concrete.desafiojava.service.user;

import static java.util.Collections.emptyList;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	private ApplicationUserRepository userRepository;

	public UserDetailsServiceImpl(ApplicationUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ApplicationUser> applicationUser = userRepository.findByEmail(username);
		if (!applicationUser.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		return new User(applicationUser.get().getEmail(), applicationUser.get().getPassword(), emptyList());
	}
}
