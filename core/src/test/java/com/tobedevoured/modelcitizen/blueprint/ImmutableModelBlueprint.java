package com.tobedevoured.modelcitizen.blueprint;

import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.annotation.Default;
import com.tobedevoured.modelcitizen.model.ImmutableModel;

@Blueprint(ImmutableModel.class)
public class ImmutableModelBlueprint {

    @Default
    String stringValue = "a value";

    @Default(force = true)
    int intValue = 5;

}
