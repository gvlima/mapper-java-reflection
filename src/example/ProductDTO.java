package example;

import configuration.MapperConfig;
import configuration.MapperFieldConfig;

@MapperConfig(log = true)
public class ProductDTO {
    private String name;
    private String description;

    @MapperFieldConfig(source = "value", typeConversion = true)
    private Double price;
}
