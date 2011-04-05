package com.slackworks.modelcitizen.blueprint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.slackworks.modelcitizen.annotation.Blueprint;
import com.slackworks.modelcitizen.annotation.Default;
import com.slackworks.modelcitizen.field.FieldCallBack;
import com.slackworks.modelcitizen.model.User;

@Blueprint(User.class )
public class UserBlueprint {
	
	@Default
	public FieldCallBack<String> username = new FieldCallBack<String>() {

		@Override
		public String get( Object model) {
			return "username" + UUID.randomUUID();
		}
		
	};
	
	@Default
	public FieldCallBack<List<String>> emails = new FieldCallBack<List<String>>() {

		@Override
		public List<String> get( Object model) {
			List<String> emails = new ArrayList();
			emails.add( "email" + UUID.randomUUID() + "@test.net" );
			emails.add( "email" + UUID.randomUUID() + "@test.net" );
			emails.add( "email" + UUID.randomUUID() + "@test.net" );
			
			return emails;
		}
		
	};
}
