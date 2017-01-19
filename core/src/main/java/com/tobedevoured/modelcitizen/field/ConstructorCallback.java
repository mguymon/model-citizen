package com.tobedevoured.modelcitizen.field;

import com.tobedevoured.modelcitizen.callback.internal.Constructable;

/**
 *
 * @author Michael Guymon
 * @deprecated for ConstructorCallback
 *
 */
@Deprecated
public abstract class ConstructorCallback implements Constructable {

	public abstract Object createInstance();
}
