package com.tobedevoured.modelcitizen.field;

import static org.junit.Assert.*;

import com.tobedevoured.modelcitizen.blueprint.OptionBlueprint;
import org.junit.Before;
import org.junit.Test;

import com.tobedevoured.modelcitizen.CreateModelException;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.WheelBlueprint;
import com.tobedevoured.modelcitizen.model.Wheel;

public class ConstructorCallbackTest {
	
	private ModelFactory modelFactory;
    private WheelBlueprint wheelBlueprint = new WheelBlueprint();
	private OptionBlueprint optionBlueprint = new OptionBlueprint();
	
	@Before
	public void setUp() throws RegisterBlueprintException {
		modelFactory = new ModelFactory();
		modelFactory.registerBlueprint( wheelBlueprint );
        modelFactory.registerBlueprint( optionBlueprint );
	}
	

	@Test
	public void testCreateModelWithConstructorCallBack() throws CreateModelException {
		
		Wheel wheel1 = modelFactory.createModel( Wheel.class );
		
		assertEquals( "tire name", wheel1.getName() );
		
		Wheel wheel2 = modelFactory.createModel( Wheel.class );
		
		assertEquals( "tire name", wheel1.getName() );
		
		assertFalse( "Should create new instances", wheel1.equals( wheel2 ) );
	}
}