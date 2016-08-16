package com.Group6.TweenAccessors;

import aurelienribon.tweenengine.TweenAccessor;
//Code written by: Meagan Bailey
public class ValueAccessor implements TweenAccessor<Value> {

	@Override
	public int getValues(Value target, int tweenType, float[] returnValues) {
		returnValues[0] = target.getValue();
		return 1;
	}

	@Override
	public void setValues(Value target, int tweenType, float[] newValues) {
		target.setValue(newValues[0]);
	}

}
