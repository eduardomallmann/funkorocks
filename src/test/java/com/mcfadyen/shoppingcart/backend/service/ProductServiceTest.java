package com.mcfadyen.shoppingcart.backend.service;

import com.mcfadyen.shoppingcart.backend.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @TestConfiguration
    static class ProductServiceTestContextConfiguration {
        @Bean
        public ProductService productService() {
            return new ProductService();
        }
    }

    @Autowired
    private ProductService productService;

    @Test
    public void whenFindAllProducts_thenShouldRetrieveAllProducts() {
        // given
        List<Product> results = productService.findAllProducts();
        // then
        assertThat(results.isEmpty()).isFalse();
        assertThat(results.size()).isEqualTo(15);
    }

    @Test
    public void whenFindAllProducts_thenShouldRetrieveAListWithProducts() {
        //given
        Product axlRose = productService.findAllProducts().stream()
                .filter(p -> p.getName().contains("Axl")).findAny().orElse(null);
        //then
        assertThat(axlRose).isNotNull();
        assertThat(axlRose.getId()).isNotNull();
        assertThat(axlRose.getName()).contains("Guns");
        assertThat(axlRose.getImage()).contains("http");
        assertThat(axlRose.getPrice()).isGreaterThan(new BigDecimal(0));
    }
}
