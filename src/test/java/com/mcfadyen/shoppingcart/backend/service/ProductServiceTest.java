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
        public ProductRepository productRepository() {
            // Mock repository for testing
            return org.mockito.Mockito.mock(ProductRepository.class);
        }
        
        @Bean
        public ProductService productService() {
            return new ProductService(productRepository);
        }
    }

    @Autowired
    private ProductService productService;

    @Test
    void whenFindAllProducts_thenShouldRetrieveAllProducts() {
        // given
        List<Product> products = productService.findAllProducts();
        // then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isGreaterThan(0);
    }

    @Test
    public void whenGetProductPrice_thenShouldReturnCorrectPrice() throws BusinessException {
        // given
        String productId = products.get(0).getId();
        BigDecimal expectedPrice = products.get(0).getPrice();
        // when
        BigDecimal actualPrice = productService.getProductPrice(productId);
        // then
        assertThat(actualPrice).isEqualTo(expectedPrice);
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
