package com.mcfadyen.shoppingcart.backend.resource;

import com.mcfadyen.shoppingcart.backend.exception.BusinessException;
import com.mcfadyen.shoppingcart.backend.exception.ErrorMessage;
import com.mcfadyen.shoppingcart.backend.model.CommerceItem;
import com.mcfadyen.shoppingcart.backend.model.ShoppingCart;
import com.mcfadyen.shoppingcart.backend.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Collections;

/**
 * Class responsible for the endpoints of the {@link ShoppingCart} object.
 * <p>Uses the {@link ShoppingCartService} to access the business logic of the object</p>
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartResource {

    private ShoppingCartService shoppingCartService;

    /**
     * Main constructor responsible to inject the services.
     *
     * @param shoppingCartService service of the shoppingcart to be injected in the class
     * @since 0.0.1
     */
    public ShoppingCartResource(final ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * Method responsible to recover the current shoppingcart if exists or create a new one.
     *
     * @return ShoppingCart found
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCart> shoppingcartGet() {
        return new ResponseEntity<>(this.shoppingCartService.findShoppingCart(), HttpStatus.OK);
    }

    /**
     * Method responsible to add an item to the shoppingcart. It will update an existing item or create a new one when
     * not.
     *
     * @param product_id product unique identification
     * @param quantity   quantity units of product
     * @return the CommerceItem created/updated
     * @throws BusinessException in case of the product not found
     */
    @PostMapping(value = "/items", params = {"product_id", "quantity"})
    public ResponseEntity<CommerceItem> shoppingcartItemsPost(@RequestParam("product_id") final String product_id,
                                                              @RequestParam("quantity") final Integer quantity)
            throws BusinessException {
        return new ResponseEntity<>(this.shoppingCartService.addCommerceItem(product_id, quantity), HttpStatus.OK);
    }

    /**
     * Method responsible for remove an item from the shoppingcart.
     *
     * @param id           commerceitem identification inside the shoppingcart
     * @param shoppingCart shoppingcart from whom the commerceitem will be removed
     * @return Success response
     * @throws BusinessException in case of failure
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity shoppingcartItemsIdDelete(@PathVariable("id") final String id,
                                                    @SessionAttribute("shoppingcart") final ShoppingCart shoppingCart)
            throws BusinessException {
        // calls the service to remove the item
        Boolean result = this.shoppingCartService.removeCommerceItem(shoppingCart, id);
        // verifeis if it's succeed
        if (result) {
            // return success
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // throw exception
            throw new BusinessException(new ErrorMessage(HttpStatus.CONFLICT,
                    "Item cannot be deleted", Collections.singletonList("The item with the id: "
                    .concat(id).concat(" cannot be deleted, 'cause it wasn't found"))));
        }
    }

}
