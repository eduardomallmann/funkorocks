package com.mcfadyen.shoppingcart.backend.resource;

import com.mcfadyen.shoppingcart.backend.model.CommerceItem;
import com.mcfadyen.shoppingcart.backend.model.Product;
import com.mcfadyen.shoppingcart.backend.model.ShoppingCart;
import com.mcfadyen.shoppingcart.backend.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartResourceIT {

    @Autowired
    private ProductService productService;
    @Autowired
    private TestRestTemplate restTemplate;

    private ShoppingCart shoppingCart;
    private String product_id;

    //given
    @Before
    public void setUp() {

        List<Product> products = productService.findAllProducts();

        this.shoppingCart = new ShoppingCart();

        products.forEach(product -> shoppingCart.getItems().add(new CommerceItem(String.valueOf(UUID.randomUUID()),
                product.getId(), 5, product.getPrice().multiply(BigDecimal.valueOf(5)))));

        this.product_id = shoppingCart.getItems().stream().map(CommerceItem::getProduct_id).findAny().get();
    }

    @Test
    public void whenShoppingcartGet_thenShouldReturnShoppingcart4Session() {
        // given
        ResponseEntity<ShoppingCart> shoppingCart = restTemplate.getForEntity("/shoppingcart", ShoppingCart.class);

        // then
        assertThat(shoppingCart.getHeaders().get("Set-Cookie")).isNotNull();
        assertThat(shoppingCart.getBody().getItems().isEmpty()).isTrue();
        assertThat(shoppingCart.getBody().getAmount()).isEqualTo(BigDecimal.valueOf(0));
        assertThat(shoppingCart.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(shoppingCart.getHeaders().getContentType()).hasToString(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    @Test
    public void whenShoppingcartItemsPost_thenShouldReturnCommerceItemCreated() {
        // given
        ResponseEntity<CommerceItem> commerceItem = restTemplate
                .postForEntity("/shoppingcart/items?product_id=".concat(product_id).concat("&quantity=5"), null,
                        CommerceItem.class);
        // then
        assertThat(commerceItem.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(commerceItem.getBody()).isInstanceOf(CommerceItem.class);
        assertThat(commerceItem.getBody().getProduct_id()).isEqualTo(product_id);
        assertThat(commerceItem.getHeaders().getContentType()).hasToString(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    @Test
    public void whenShoppingcartItemsIdDelete_thenShouldGetException() {
        // given
        ResponseEntity<Object> result = restTemplate
                .exchange("/shoppingcart/items/1", HttpMethod.DELETE, null, Object.class);
        // then
        assertThat(result.getBody()).isNull();
    }



}
