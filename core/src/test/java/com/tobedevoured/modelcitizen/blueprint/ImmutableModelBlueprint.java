package com.tobedevoured.modelcitizen.blueprint;

import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.annotation.Default;
import com.tobedevoured.modelcitizen.model.ImmutableModel;
import com.tobedevoured.modelcitizen.template.ConstructorInjectionTemplate;

@Blueprint(value = ImmutableModel.class, template = ConstructorInjectionTemplate.class)
public class ImmutableModelBlueprint {

    @Default
    String privateField = "a value";

    @Default(force = true)
    int intValue = 5;

}
