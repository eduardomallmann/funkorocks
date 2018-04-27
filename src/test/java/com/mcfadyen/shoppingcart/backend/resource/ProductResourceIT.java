package com.mcfadyen.shoppingcart.backend.resource;

import com.mcfadyen.shoppingcart.backend.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductResourceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<List<Product>> result;

    @Before
    public void setUp() {
        //given
        result = restTemplate.exchange("/products", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {});
    }

    @Test
    public void whenRequestForProductsGet_thenShouldReturnInformationAboutProducts() {
        //when
        Product lemmy = result.getBody().stream().filter(p -> p.getName().contains("Lemmy")).findAny().orElse(null);
        //then
        assertThat(result.getBody().isEmpty()).isFalse();
        assertThat(lemmy.getId()).matches("([a-zA-Z0-9-])+");
        assertThat(lemmy.getName()).contains("Motörhead");
        assertThat(lemmy.getImage()).contains("http");
        assertThat(lemmy.getPrice()).isGreaterThan(new BigDecimal(0));
    }

    @Test
    public void whenRequestForProductsGet_thenShouldReturnArray() {
        //then
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isInstanceOf(List.class);
        assertThat(result.getBody().size()).isEqualTo(15);
    }

    @Test
    public void whenRequestForProductsGet_thenShouldResponses200() {
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenRequestForProductsGet_thenShouldHasContentTypeJson() {
        //then
        assertThat(result.getHeaders().containsKey("Content-Type")).isTrue();
        assertThat(result.getHeaders().getContentType()).hasToString((MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

}
