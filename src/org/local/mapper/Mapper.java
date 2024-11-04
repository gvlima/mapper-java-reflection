package org.local.mapper;

import org.local.configuration.MapperFieldConfig;
import org.local.exception.MapperException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Objects;

public class Mapper {
    public static <I, O> O transform(I input, Class<?> outputClass) {

        try {
            Class<?> inputClass = input.getClass();

            Constructor<?> constructor = outputClass.getDeclaredConstructor();
            O output = (O) constructor.newInstance();

            Field[] inputFields = inputClass.getDeclaredFields();
            Field[] outputFields = outputClass.getDeclaredFields();

            for (Field fieldInput: inputFields){
                fieldInput.setAccessible(true);
                for(Field fieldOutput: outputFields){
                    fieldOutput.setAccessible(true);
                    if(fieldOutput.getName().equals(fieldInput.getName())){
                        fieldOutput.set(output, fieldInput.get(input));
                    } else {
                        MapperFieldConfig annotation = fieldOutput.getAnnotation(MapperFieldConfig.class);
                        if(Objects.nonNull(annotation)){
                            String destination = annotation.source();
                            boolean typeConversion = annotation.typeConversion();

                            if(destination.equals(fieldInput.getName())){

                                if(typeConversion){
                                    Class<?> fieldInputType = fieldInput.getType();
                                    Class<?> fieldOutputType = fieldOutput.getType();
                                    BigDecimal value = (BigDecimal) fieldInput.get(input);
                                    fieldOutput.set(output, value.doubleValue());
                                } else {
                                    fieldOutput.set(output, fieldInput.get(input));
                                }
                            }
                        }
                    }
                }
            }
            return output;
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException ex) {
            System.out.println(ex.getMessage());
            throw new MapperException(ex.getMessage());
        }


    }
}
