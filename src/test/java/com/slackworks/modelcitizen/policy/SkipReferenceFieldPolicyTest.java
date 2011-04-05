package com.slackworks.modelcitizen.policy;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
   *
 * http://www.apache.org/licenses/LICENSE-2.0
   *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
