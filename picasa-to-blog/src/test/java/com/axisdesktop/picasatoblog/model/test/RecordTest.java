package com.axisdesktop.picasatoblog.model.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.axisdesktop.picasatoblog.config.AppConfig;
import com.axisdesktop.picasatoblog.controller.IndexController;
import com.axisdesktop.picasatoblog.model.Record;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { AppConfig.class } )
@WebAppConfiguration
@Transactional
@ActiveProfiles( "development" )
public class RecordTest {

	MockMvc mockMvc;

	Record record = new Record();

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup( new IndexController() ).build();
	}

	@Test
	public void test() {
		// mockMvc.perform( get( "/media" ) ).andExpect( status().isOk() );
		// assertTrue( true );
		// ( "Not yet implemented" );
	}

}
