package mapper;

import configuration.MapperFieldConfig;
import exception.MapperException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mapper {
    public <I, O> O transform(I input, Class<O> outputClass) {
        NumericConverter numericConverter = new NumericConverter();

        try {
            Class<?> inputClass = input.getClass();

            List<Field> inputFields = List.of(inputClass.getDeclaredFields());
            List<Field> outputFields = new ArrayList<>(List.of(outputClass.getDeclaredFields()));

            Constructor<O> constructor = outputClass.getDeclaredConstructor();
            O output = constructor.newInstance();

            for (Field fieldInput: inputFields){
                fieldInput.setAccessible(true);

                for(Field fieldOutput: outputFields){
                    fieldOutput.setAccessible(true);

                    if(fieldOutput.getName().equals(fieldInput.getName())){
                        if(fieldOutput.getType().equals(fieldInput.getType())){
                            fieldOutput.set(output, fieldInput.get(input));
                            continue;
                        }
                        fieldOutput.set(output, null);
                        outputFields.remove(fieldInput);
                    } else {
                        MapperFieldConfig annotation = fieldOutput.getAnnotation(MapperFieldConfig.class);
                        if(Objects.nonNull(annotation)){
                            String destination = annotation.source();
                            boolean typeConversion = annotation.typeConversion();

                            if(destination.equals(fieldInput.getName())){
                                if(typeConversion){
                                    Class<?> fieldOutputType = fieldOutput.getType();
                                    fieldOutput.set(output, numericConverter.convert(fieldInput.get(input), fieldOutputType));
                                } else {
                                    if(fieldOutput.getType().equals(fieldInput.getType())){
                                        fieldOutput.set(output, fieldInput.get(input));
                                        continue;
                                    }

                                    fieldOutput.set(output, null);
                                }
                            }
                        }
                    }
                }
            }

            return output;
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            throw new MapperException(ex.getMessage());
        }

    }
}
