package com.mcfadyen.shoppingcart.backend.resource;

import com.mcfadyen.shoppingcart.backend.model.Product;
import com.mcfadyen.shoppingcart.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class responsible for the endpoints of the {@link Product} object.
 * <p>
 * <p>Uses the {@link ProductService} to access the business logic of the object</p>
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@RestController
@RequestMapping("/products")
public class ProductResource {

    private ProductService productService;

    /**
     * Main constructor responsible to inject the services.
     *
     * @param productService service of the product to be injected in the class
     * @since 0.0.1
     */
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Method responsible for send a list with all products to the request owner in JSON by the calling of its endpoint.
     *
     * @return a response with the list of all the products
     * @since 0.0.1
     */
    @GetMapping
    public ResponseEntity<List<Product>> productsGet() {
        // get the all the products and instatiate them inside the list
        List<Product> products = productService.findAllProducts();
        // return the list and successful status
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
