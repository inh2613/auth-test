package com.inhee.jwt.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inhee.jwt.config.SecurityConfiguration;
import com.inhee.jwt.member.domain.dto.MemberDTO;
import com.inhee.jwt.member.repository.MemberRepository;
import com.inhee.jwt.member.service.MemberService;
import com.inhee.jwt.util.JsonUtil;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(MemberController.class)
//@Import(SecurityConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
public class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private MemberService memberService;

	@BeforeEach
	public void setup() {
//		this.mockMvc=MockMvcBuilders.webAppContextSetup(context)
//				.apply(springSecurity())
//				.build();
		MockitoAnnotations.openMocks(this);

	}

	@Test
	@WithAnonymousUser
	public void testSignUp() throws Exception {

		// given
		MemberDTO memberDTO = MemberDTO.builder()
				.username("testuser")
				.password("testpassword")
				.build();


		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(memberDTO)))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		//then
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals("Sign up successful", result.getResponse().getContentAsString());

	}

	@Test
	@WithMockUser(username = "inhee1", password = "inh123",roles = "USER")

	public void testLoginWithValidCredentials() throws Exception {
		// given
		MemberDTO memberDTO = MemberDTO.builder()
				.username("inhee1")
				.password("inh123")
				.build();

		when(memberService.login(any(MemberDTO.class))).thenReturn(memberDTO);

		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonUtil.toJson(memberDTO))
				)

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("inhee1"))
				.andReturn();

		//then
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		MemberDTO loggedInMember = JsonUtil.fromJson(result.getResponse().getContentAsString(), MemberDTO.class);
		assertNotNull(loggedInMember);
	}

	@Test
	@WithAnonymousUser
	public void testLoginWithInvalidCredentials() throws Exception {
		// given
		MemberDTO memberDTO = MemberDTO.builder()
				.username("testuser")
				.password("testpassword11")
				.build();

		when(memberService.login(memberDTO)).thenReturn(null);

		// when
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonUtil.toJson(memberDTO)))

				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andReturn();

		//then
		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
	}


}