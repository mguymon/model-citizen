package com.tobedevoured.modelcitizen.template;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import com.tobedevoured.modelcitizen.CreateModelException;
import com.tobedevoured.modelcitizen.ModelFactory;
import com.tobedevoured.modelcitizen.RegisterBlueprintException;
import com.tobedevoured.modelcitizen.blueprint.ImmutableModelBlueprint;
import com.tobedevoured.modelcitizen.model.ImmutableModel;
import org.junit.Before;
import org.junit.Test;

public class ConstructorInjectionTemplateTest {

    private final ModelFactory modelFactory = new ModelFactory();

    @Before
    public void setup() throws RegisterBlueprintException {
        modelFactory.registerBlueprint(ImmutableModelBlueprint.class);
    }

    @Test
    public void shouldCreateModelFromClassConstructor() throws CreateModelException {
        ImmutableModel model = modelFactory.createModel(ImmutableModel.class);

        // values set via blueprint
        assertThat(model.getPrivateField(), is("a value"));
        assertThat(model.intValue, is(5));

        // default values for reference types
        assertThat(model.objectValue, nullValue());

        // default values for primitive types
        assertThat(model.shortValue, is((short) 0));
        assertThat(model.longValue, is(0L));
        assertThat(model.byteValue, is((byte) 0));
        assertThat(model.floatValue, is(0.0f));
        assertThat(model.doubleValue, is(0.0d));
        assertThat(model.booleanValue, is(false));
    }

}
