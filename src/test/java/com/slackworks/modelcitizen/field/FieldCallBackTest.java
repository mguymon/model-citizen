package com.slackworks.modelcitizen.field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.slackworks.modelcitizen.CreateModelException;
import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.RegisterBlueprintException;
import com.slackworks.modelcitizen.blueprint.UserBlueprint;
import com.slackworks.modelcitizen.model.User;

public class FieldCallBackTest {
	
	private ModelFactory modelFactory;
	private UserBlueprint userBlueprint = new UserBlueprint();
	
	@Before
	public void setUp() throws RegisterBlueprintException {
		modelFactory = new ModelFactory();
		modelFactory.registerBlueprint( userBlueprint );
	}
	

	@Test
	public void testCreateModelWithFieldCallBack() throws CreateModelException {
		
		User user = modelFactory.createModel( User.class );
		
		assertNotNull( user.getUsername() );
		assertTrue( user.getUsername().contains("username") );
		
		assertNotNull( user.getEmails() );
		assertEquals( 3, user.getEmails().size() );
		for ( String email : user.getEmails() ) {
			assertTrue( email.contains( "email" ) );
			assertTrue( email.contains( "@test.net" ) );
		}
	}
}
