package mapper.example;

import mapper.configuration.MapperFieldConfig;

public class ProductDTO {
    private String name;
    private String description;

    @MapperFieldConfig(source = "value", typeConversion = true)
    private Double price;
}
