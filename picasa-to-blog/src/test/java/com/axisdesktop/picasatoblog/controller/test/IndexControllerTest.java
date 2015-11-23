package com.axisdesktop.picasatoblog.controller.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.config.AppConfig;
import com.axisdesktop.picasatoblog.controller.IndexController;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { AppConfig.class } )
@WebAppConfiguration
@Transactional
@ActiveProfiles( "development" )
public class IndexControllerTest {

	@Autowired
	private IndexController indexController;

	// @Autowired
	// WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		// this.mockMvc = MockMvcBuilders.webAppContextSetup( this.wac ).dispatchOptions( true ).build();
		this.mockMvc = MockMvcBuilders.standaloneSetup( indexController ).build();
	}

	@Test
	public void getIndex() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( "/" );

		mockMvc.perform( request ).andExpect( status().isOk() ).andExpect( view().name( "/index" ) )
				.andExpect( cookie().exists( "visitor" ) );
	}

	@Test
	public void getIndexAndExistsWidthCookie() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( "/" ).cookie( new Cookie( "w", "12345" ) );

		mockMvc.perform( request ).andExpect( status().isOk() ).andExpect( view().name( "/index" ) )
				.andExpect( model().attribute( "picasaForm", hasProperty( "width", hasToString( "12345" ) ) ) );
	}

	@Test
	public void getIndexAndNotExistsWidthCookie() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( "/" );

		mockMvc.perform( request ).andExpect( status().isOk() ).andExpect( view().name( "/index" ) )
				.andExpect( model().attribute( "picasaForm", hasProperty( "width", hasToString( "1024" ) ) ) );
	}

	@Test
	public void getIndexAndExistsHeightCookie() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( "/" ).cookie( new Cookie( "h", "567890" ) );

		mockMvc.perform( request ).andExpect( status().isOk() ).andExpect( view().name( "/index" ) )
				.andExpect( model().attribute( "picasaForm", hasProperty( "height", hasToString( "567890" ) ) ) );
	}

	@Test
	public void getIndexAndNotExistsHeightCookie() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( "/" );

		mockMvc.perform( request ).andExpect( status().isOk() ).andExpect( view().name( "/index" ) )
				.andExpect( model().attribute( "picasaForm", hasProperty( "height", hasToString( "512" ) ) ) );
	}
}
