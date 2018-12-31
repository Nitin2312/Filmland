package com.entertainment.filmland.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.entertainment.filmland.model.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class GenerateHasHandlerTest {
	
	@InjectMocks
	private GenerateHashHandler generateHashHandler;
	
	UserDetails userDetail = new UserDetails();
	private String expectedPassword= "C747238F43C8D25F867906C2441A3CACD948D9D2";
	
	@org.junit.Before
	public void setUp(){
	userDetail.setEmail("isosidof");
	userDetail.setPassword("abcs123");
	}
	
	@Test
	public void testMd5Generation() throws Exception{
		generateHashHandler.handle(userDetail);
		assertEquals(expectedPassword,userDetail.getPassword());
	}
}
