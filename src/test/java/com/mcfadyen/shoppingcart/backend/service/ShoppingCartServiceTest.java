package com.mcfadyen.shoppingcart.backend.service;

import com.mcfadyen.shoppingcart.backend.model.CommerceItem;
import com.mcfadyen.shoppingcart.backend.model.Product;
import com.mcfadyen.shoppingcart.backend.model.ShoppingCart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ShoppingCartServiceTest {

    private static final String ATTRIBUTE_SHOPPINGCART_KEY = "shoppingcart";

    @TestConfiguration
    static class ProductServiceTestContextConfiguration {
        @Bean
        public ProductService productService() {
            return new ProductService();
        }

        @Bean
        public ShoppingCartService shoppingCartService() {
            return new ShoppingCartService(new MockHttpSession(), productService());
        }
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    private ShoppingCart shoppingCart;
    private String product_id;
    private String commerceItemId;

    //given
    @Before
    public void setUp() {

        List<Product> products = productService.findAllProducts();

        this.shoppingCart = new ShoppingCart();

        products.forEach(product -> shoppingCart.getItems().add(new CommerceItem(String.valueOf(UUID.randomUUID()),
                product.getId(), 5, product.getPrice().multiply(BigDecimal.valueOf(5)))));

        this.product_id = shoppingCart.getItems().stream().map(CommerceItem::getProduct_id).findAny().get();

        this.commerceItemId = shoppingCart.getItems().stream().map(CommerceItem::getId).findAny().get();
    }

    @Test
    public void whenFindAShoppingCart_thenShouldReturnNewCart() {
        // when
        ShoppingCart newShoppingCart = this.shoppingCartService.findShoppingCart();
        // then
        assertThat(newShoppingCart.getItems().isEmpty()).isTrue();
        assertThat(newShoppingCart.getAmount()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    public void whenAddCommerceItem_thenShouldReturnIt() throws Exception {
        // when
        CommerceItem item = this.shoppingCartService.addCommerceItem(this.product_id, 5);
        BigDecimal amount = this.productService.findAllProducts().stream().filter(product -> product.getId()
                .equals(product_id)).map(Product::getPrice).findFirst().get().multiply(BigDecimal.valueOf(5));
        // then
        assertThat(item).isNotNull();
        assertThat(item.getId()).matches("[a-zA-Z0-9-]+");
        assertThat(item.getProduct_id()).matches("[a-zA-Z0-9-]+");
        assertThat(item.getQuantity()).isEqualTo(5);
        assertThat(item.getAmount()).isEqualTo(amount);
    }

    @Test
    public void whenRemoveCommerceItem_ThenReturnTrue() {
        // when
        Boolean result = this.shoppingCartService.removeCommerceItem(this.shoppingCart, this.commerceItemId);
        // then
        assertThat(result).isTrue();

    }
}
