package com.concrete.desafiojava.api.v1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.concrete.desafiojava.api.v1.user.UserRequest;
import com.concrete.desafiojava.api.v1.user.UserResponse;
import com.concrete.desafiojava.api.v1.user.UsersRestService;
import com.concrete.desafiojava.domain.repository.ApplicationUserRepository;
import com.concrete.desafiojava.domain.repository.PhoneRepository;
import com.concrete.desafiojava.service.phone.PhoneServiceImpl;
import com.concrete.desafiojava.service.token.TokenServiceImpl;
import com.concrete.desafiojava.service.user.RequestServiceImpl;
import com.concrete.desafiojava.service.user.UserServiceImpl;
import com.concrete.desafiojava.service.uuid.UuidServiceImpl;
import com.concrete.desafiojava.service.validation.ValidationServiceImpl;

public class UsersRestServiceTest {

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
	
	@Mock
	UserServiceImpl userService;
	
	@InjectMocks
	UsersRestService usersRestService;

	@Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	MockMvc mockMvc;

    @Before
    public void setUpMockMvc() {
        mockMvc = standaloneSetup(usersRestService).build();
    }
    
	@Test
	public void validateTheReturnTypeClassForUserSaveRequest() throws Exception {

		UserResponse userResponse = when(mock(UserResponse.class)
				.getEmail())
				.thenReturn("vhugo006@gmail.com")
				.getMock();

		when(userService.save(mock(UserRequest.class)))
			.thenReturn(Optional.of(userResponse));

		mockMvc.perform(post("/v1/users/saveUser")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
				.andExpect(status().isCreated())
				.andReturn().getClass().isInstance(UserResponse.class);
	}
	
}
