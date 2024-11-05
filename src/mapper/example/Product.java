package mapper.example;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal value;

    public Product(String id, String name, String description, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
