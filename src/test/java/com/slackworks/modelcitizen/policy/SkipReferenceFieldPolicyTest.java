package com.slackworks.modelcitizen.policy;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.slackworks.modelcitizen.ModelFactory;
import com.slackworks.modelcitizen.ModelFactoryException;
import com.slackworks.modelcitizen.RegisterBlueprintException;
import com.slackworks.modelcitizen.blueprint.UserBlueprint;
import com.slackworks.modelcitizen.model.User;

public class SkipReferenceFieldPolicyTest {

	private ModelFactory modelFactory;
	private UserBlueprint userBlueprint = new UserBlueprint();
	
	@Before
	public void setUp() throws RegisterBlueprintException {
		modelFactory = new ModelFactory();
		modelFactory.registerBlueprint( userBlueprint );
	}
	
	@Test
	public void alwaySetFieldPolicy() throws ModelFactoryException {
		modelFactory.addPolicy( new SkipReferenceFieldPolicy( "username", User.class ) );
		
		User user1 = modelFactory.createModel( User.class );
		User user2 = modelFactory.createModel( user1 );
		
		assertNotSame( user1.getUsername(), user2.getUsername() );
		assertEquals( user1.getEmails(), user2.getEmails() );
	}
	
}
