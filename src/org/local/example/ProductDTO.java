package org.local.example;

import org.local.configuration.MapperFieldConfig;

public class ProductDTO {
    private String name;
    private String description;

    @MapperFieldConfig(source = "value", typeConversion = true)
    private Double price;
}
