package example;

import mapper.Mapper;
import mapper.NumericConverter;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws Exception {
        Mapper objectMapper = new Mapper();
        Product product = new Product("123", "Headphone", "Wireless Bluetooth Headphone", BigDecimal.valueOf(400.00));
        ProductDTO dto = objectMapper.transform(product, ProductDTO.class);
        System.out.println(dto);
        //BigDecimal value = BigDecimal.valueOf(100.0);
        //Double result = (Double) NumericConverter.convertNumber(value, Double.class);
        //System.out.println(result);
    }
}