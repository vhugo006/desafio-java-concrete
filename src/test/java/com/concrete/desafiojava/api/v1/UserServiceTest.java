package com.concrete.desafiojava.api.v1;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.concrete.desafiojava.api.v1.user.PhoneRequest;
import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.api.v1.user.UserResponse;
import com.concrete.desafiojava.domain.orm.ApplicationUser;
import com.concrete.desafiojava.domain.orm.Phone;
import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;
import com.concrete.desafiojava.domain.repository.PhoneRepository;
import com.concrete.desafiojava.exception.AuthenticationException;
import com.concrete.desafiojava.exception.EmailFoundException;
import com.concrete.desafiojava.exception.SessionException;
import com.concrete.desafiojava.exception.UserNotFoundException;
import com.concrete.desafiojava.service.phone.PhoneServiceImpl;
import com.concrete.desafiojava.service.token.TokenServiceImpl;
import com.concrete.desafiojava.service.user.RequestServiceImpl;
import com.concrete.desafiojava.service.user.UserServiceImpl;
import com.concrete.desafiojava.service.uuid.UuidServiceImpl;
import com.concrete.desafiojava.service.validation.ValidationServiceImpl;

public class UserServiceTest {

	private static final String EMAIL = "vhugo006@gmail.com";
	private static final String NAME = "Victor Moraes";
	private static final String PASSWORD = "senha123";
	private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWN0b3JAc2lsdmEub3JnIiwiZXhwIjoxNTMwMTg4Mzg4fQ.CT4uPA9YM_ZbWByurzuWTkvD9IkvCWxuKKhOr4iZTXh1PvXQRBPVfMSHWVpvYaCcX30Sd7JaemUS_T2yErjp3Q";
	private static final String CRYPT_TOKEN = "$2a$10$Ol9JVEBG/O5ntDUs5c/8S.hgB1U7LFZvp2HnIG5zULsg7jQ6/Mtxu";
	private static final String UUID = "2193a0c80b49465d9f991b5cc7ec8689";
	private static final Date CURRENT_DATE = new Date();
	private static final String HEADER = "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWN0b3JAc2lsdmEub3JnIiwiZXhwIjoxNTMwMTg4Mzg4fQ.CT4uPA9YM_ZbWByurzuWTkvD9IkvCWxuKKhOr4iZTXh1PvXQRBPVfMSHWVpvYaCcX30Sd7JaemUS_T2yErjp3Q";

	
	@Mock
	ApplicationUserRepository userRepository;
	
	@Mock
	private ValidationServiceImpl validationService;
	
	@Mock
	private PhoneRepository phoneRepository;

	@Mock
	private PhoneServiceImpl phoneService;

	@Mock
	private RequestServiceImpl requestService;

	@Mock
	private TokenServiceImpl tokenService;
	
	@Mock
	private UuidServiceImpl uuidService;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Captor
	ArgumentCaptor<ApplicationUser> userArgumentCaptor;

	@InjectMocks
	UserServiceImpl userService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    

	@Test
	public void creatingAUserTellsTheUserRepositoryToSaveAUser() throws EmailFoundException{
		
		UserRequest userRequest = UserRequest.builder()
			.email(EMAIL)
			.name(NAME)
			.password(PASSWORD)
			.phones(getPhoneRequestUserPhonesList())
			.build();
		
		ApplicationUser user = getUser();
		
		when(requestService.toDomain(userRequest))
        	.thenReturn(user);
		
		when(userRepository.save(user))
			.thenReturn(user);
		
		when(phoneService.savePhones(getPhoneList()))
			.thenReturn(getPhoneList());
		
		Optional<UserResponse> userResponse = userService.save(userRequest);
		
		assertThat(userResponse.get().getEmail(), is(userRequest.getEmail()));
        assertThat(userResponse.get().getId(), is(user.getUuid()));
        assertThat(userResponse.get().getEmail(), is(user.getEmail()));
	}


    @Test
    public void findingAnExistingUserByID() throws AuthenticationException, SessionException, UserNotFoundException {
        
    	ApplicationUser existingUser = getUser();
        
        when(userRepository.findByUuid(UUID))
                .thenReturn(Optional.of(existingUser));
        
        when(tokenService.getToken(HEADER))
        		.thenReturn(TOKEN);
        
        when(tokenService.isTokenMatches(TOKEN, CRYPT_TOKEN))
        	.thenReturn(true);
        
        when(phoneRepository.findByUser(existingUser))
        	.thenReturn(getPhoneList());

        Optional<UserResponse> userResponse = userService.findUser(UUID, HEADER);
        

        assertThat(userResponse.get().getId(), is(existingUser.getUuid()));
        assertThat(userResponse.get().getEmail(), is(existingUser.getEmail()));
    }
    
	@Test(expected = UserNotFoundException.class)
	public void returnsUserNotFoundExceptionWhenUnableToFindAnExistingUserByID() throws AuthenticationException, SessionException, UserNotFoundException {
      
    	when(userRepository.findByUuid(UUID))
                .thenReturn(Optional.empty());

        Optional<UserResponse> userResponse = userService.findUser(UUID, HEADER);

        assertThat(userResponse.isPresent(), is(false));
    }
    
	private List<PhoneRequest> getPhoneRequestUserPhonesList(){
		
		PhoneRequest phone = PhoneRequest.builder()
		.ddd("91")
		.number("982567654")
		.build();
		
		return new ArrayList<PhoneRequest>(asList(phone));
	}
	

    
	private List<Phone> getPhoneList(){
		List<Phone> userPhones = new ArrayList<>();
		getPhoneRequestUserPhonesList().forEach(phoneRequest->userPhones.add(Phone.builder().id(1L)
																							.ddd(phoneRequest.getDdd())
																							.number(phoneRequest.getNumber())
																							.build()));
		return userPhones;
	}
	
	private ApplicationUser getUser() {
		
		ApplicationUser savedUser = when(mock(ApplicationUser.class).getId())
                .thenReturn(1L)
                .getMock();

		when(savedUser.getUuid())
        .thenReturn(UUID);

		when(savedUser.getName())
        .thenReturn(NAME);
		
		when(savedUser.getPassword())
        .thenReturn(PASSWORD);

		when(savedUser.getCreated())
        .thenReturn(CURRENT_DATE);
        
		when(savedUser.getEmail())
        .thenReturn(EMAIL);
		
		when(savedUser.getLastLogin())
        .thenReturn(CURRENT_DATE);
		
		when(savedUser.getModified())
        .thenReturn(CURRENT_DATE);
		
		when(savedUser.getNoCryptToken())
        .thenReturn(TOKEN);
		
		when(savedUser.getToken())
        .thenReturn(TOKEN);
		
		return savedUser;
	}
	

}
