package ru.bisoft.laboratory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.bisoft.laboratory.rest.UserController;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:spring/application-test-context.xml")
//@WebAppConfiguration
public class UserControllerTest {
	@Autowired
	private UserController controller;

//	// @Test
//	public void shouldReturnDefaultMessage() throws Exception {
//		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Hello World")));
//	}
//
	@Test
	public void contexLoads() throws Exception {
//		MockMvcBuilders.standaloneSetup(controller).build().perform(get("/api/v1/users")).andExpect(status().isOk());
//		Assertions.assertNotNull(controller);
	}
}
