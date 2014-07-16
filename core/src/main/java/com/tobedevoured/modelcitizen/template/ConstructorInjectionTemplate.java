package com.tobedevoured.modelcitizen.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConstructorInjectionTemplate implements BlueprintTemplate {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T construct(Class<T> modelClass) throws BlueprintTemplateException {
        final Constructor<?>[] constructors = modelClass.getConstructors();
        final Constructor<?> constructor = constructors[0];
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        final List<Object> arguments = new ArrayList<Object>(parameterTypes.length);

        for (Class<?> t : parameterTypes) {
            if (t.isPrimitive()) {
                if (t == Boolean.TYPE) {
                    arguments.add(false);
                } else if (t == Integer.TYPE) {
                    arguments.add(0);
                } else if (t == Long.TYPE) {
                    arguments.add(0L);
                } else if (t == Short.TYPE) {
                    arguments.add((short) 0);
                } else if (t == Byte.TYPE) {
                    arguments.add((byte) 0);
                } else if (t == Float.TYPE) {
                    arguments.add(0.0f);
                } else if (t == Double.TYPE) {
                    arguments.add(0.0d);
                }
            } else {
                arguments.add(null);
            }
        }

        try {
            return (T) constructor.newInstance(arguments.toArray());
        } catch (Exception e) {
            throw new BlueprintTemplateException(e);
        }
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
