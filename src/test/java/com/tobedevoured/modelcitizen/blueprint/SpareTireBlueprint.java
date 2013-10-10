package com.tobedevoured.modelcitizen.blueprint;

import com.tobedevoured.modelcitizen.annotation.Blueprint;
import com.tobedevoured.modelcitizen.annotation.Default;
import com.tobedevoured.modelcitizen.annotation.NewInstance;
import com.tobedevoured.modelcitizen.field.ConstructorCallback;
import com.tobedevoured.modelcitizen.model.SpareTire;
import com.tobedevoured.modelcitizen.model.Wheel;

@Blueprint(SpareTire.class)
public class SpareTireBlueprint extends WheelBlueprint {

    @Default
    public Integer mileLimit = 400;

    @Default
    public Integer size = 9;

    @NewInstance
    ConstructorCallback constructor = new ConstructorCallback() {

        @Override
        public Object createInstance() {
            SpareTire spareTire = new SpareTire( "spare tire name" );
            return spareTire;
        }

    };
}
