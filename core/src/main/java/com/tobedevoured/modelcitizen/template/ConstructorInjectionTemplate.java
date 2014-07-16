package com.tobedevoured.modelcitizen.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConstructorInjectionTemplate implements BlueprintTemplate {
    @Override
    public <T> T construct(Class<T> modelClass) throws BlueprintTemplateException {
        final Constructor<?>[] constructors = modelClass.getConstructors();
        final Constructor<?> constructor = constructors[0];
        final T instance;

        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final List<Object> parameters = new ArrayList<Object>(parameterTypes.length);
        for (Class<?> t : parameterTypes) {
            if (t.isPrimitive()) {
                if (t == Boolean.TYPE) {
                    parameters.add(false);
                } else if (t == Integer.TYPE) {
                    parameters.add(0);
                } else if (t == Long.TYPE) {
                    parameters.add(0L);
                } else if (t == Short.TYPE) {
                    parameters.add((short) 0);
                } else if (t == Byte.TYPE) {
                    parameters.add((byte) 0);
                } else if (t == Float.TYPE) {
                    parameters.add(0.0f);
                } else if (t == Double.TYPE) {
                    parameters.add(0.0d);
                }
            } else {
                parameters.add(null);
            }
        }

        try {
            instance = (T) constructor.newInstance(parameters.toArray());
        } catch (Exception e) {
            throw new BlueprintTemplateException(e);
        }
        return instance;
    }

    @Override
    public <T> T set(T model, String property, Object value) throws BlueprintTemplateException {
        try {
            getAccessibleField(model, property).set(model, value);
            return model;
        } catch (Exception e) {
            throw new BlueprintTemplateException(e);
        }
    }

    @Override
    public Object get(Object model, String property) throws BlueprintTemplateException {
        try {
            return getAccessibleField(model, property).get(model);
        } catch (Exception e) {
            throw new BlueprintTemplateException(e);
        }
    }

    private Field getAccessibleField(Object model, String property) throws NoSuchFieldException {
        final Field field = model.getClass().getDeclaredField(property);
        field.setAccessible(true);
        return field;
    }
}
