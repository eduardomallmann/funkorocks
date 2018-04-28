package com.mcfadyen.shoppingcart.backend.service;

import com.mcfadyen.shoppingcart.backend.exception.BusinessException;
import com.mcfadyen.shoppingcart.backend.exception.ErrorMessage;
import com.mcfadyen.shoppingcart.backend.model.CommerceItem;
import com.mcfadyen.shoppingcart.backend.model.ShoppingCart;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

/**
 * Class responsible for the business logic of the {@link ShoppingCart} object.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@Service
public class ShoppingCartService {

    private static final String ATTRIBUTE_SHOPPINGCART_KEY = "shoppingcart";
    private static final Integer BIGDECIMAL_SCALE = 2;

    private HttpSession session;
    private ProductService productService;

    /**
     * Main constructor, responsible for inject classes.
     *
     * @param session        the user session from the request
     * @param productService service of the product to be injected in the class
     */
    public ShoppingCartService(HttpSession session, ProductService productService) {
        this.session = session;
        this.productService = productService;
    }

    /**
     * Method that finds the shoppingcart by the user session.
     * <p> In case of new session, a new shoppingcart is instantiated.</p>
     *
     * @return a shopping cart object
     */
    public ShoppingCart findShoppingCart() {
        // verifies if it's a new session
        if (session.isNew()) {
            //instantiate a new empty shoppingcart
            ShoppingCart shoppingCart = new ShoppingCart();
            //set the shoppingcart to the session
            session.setAttribute(ATTRIBUTE_SHOPPINGCART_KEY, shoppingCart);
            // return the new empty shoppingcart
            return shoppingCart;
        } else {
            // return the existent shoppingcart from the session
            return (ShoppingCart) session.getAttribute(ATTRIBUTE_SHOPPINGCART_KEY);
        }
    }

    /**
     * Method that add a commerceItem to the shoppingcart.
     *
     * @param product_id product unique identification
     * @param quantity   unit amount of the product
     * @return the CommerceItem created.
     * @throws BusinessException in case of price not found
     */
    public CommerceItem addCommerceItem(final String product_id, final Integer quantity) throws BusinessException {
        // instantiate the shoppingcart with the existent one
        ShoppingCart shoppingCart = this.findShoppingCart();
        // create the new CommerceItem
        CommerceItem commerceItem = this.getCommerceItem(shoppingCart, product_id, quantity);
        // removes the item from shoppingcart if it's already present
        shoppingCart.getItems().stream().filter(item -> item.getProduct_id().equals(product_id)).findFirst()
                .ifPresent(item -> shoppingCart.getItems().remove(item));
        // adds the new commerceitem to the shoppingcart
        shoppingCart.getItems().add(commerceItem);
        // updates the shoppingcart amount
        this.updateShoppingCartAmount(shoppingCart);
        // updates the shoppingcart in the session
        session.setAttribute(ATTRIBUTE_SHOPPINGCART_KEY, shoppingCart);
        // returns the commerceitem created
        return commerceItem;
    }

    /**
     * Method that removes the CommerceItem from the ShoppingCart.
     *
     * @param itemId       CommerceItem identification for the ShoppingCart
     * @param shoppingCart the shoppingcart that should have the item removed
     * @return boolean informing the success of the operation
     * @throws BusinessException in case of the itemId wasn't found
     */
    public boolean removeCommerceItem(final ShoppingCart shoppingCart, final String itemId) throws BusinessException {
        // find the item to be removed
        CommerceItem item = shoppingCart.getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst()
                .orElseThrow(() -> new BusinessException(new ErrorMessage(HttpStatus.CONFLICT,
                        "Item not found", Collections.singletonList("The item with the id: "
                        .concat(itemId).concat(" wasn't found")))));
        // remove the item and keeps the result of the operation
        boolean result = shoppingCart.getItems().remove(item);
        // updates the shoppingcart amount
        this.updateShoppingCartAmount(shoppingCart);
        // updates the shoppingcart in the session
        session.setAttribute(ATTRIBUTE_SHOPPINGCART_KEY, shoppingCart);
        // return the result of the operation
        return result;
    }

    /**
     * Method that searches for a CommerceItem with the product informed inside the items of the shoppingcart, and
     * updates it if found or creates a new one if not.
     *
     * @param shoppingCart the current shoppingcart
     * @param product_id   product unique identifier
     * @param quantity     quantity of the product for the commerceitem
     * @return a new or updated CommerceItem
     * @throws BusinessException if the product wasn't found
     */
    private CommerceItem getCommerceItem(final ShoppingCart shoppingCart, final String product_id,
                                         final Integer quantity) throws BusinessException {
        // searches the product in the current shoppingcart and get its commerceitem if exists
        CommerceItem commerceItem = shoppingCart.getItems().stream()
                .filter(item -> item.getProduct_id().equals(product_id))
                .findFirst().orElse(null);
        // verifies if a commerceitem for the product was found
        if (commerceItem == null) {
            // creates a new commerceItem in case of new product
            commerceItem = new CommerceItem(String.valueOf(UUID.randomUUID()), product_id, quantity,
                    this.productService.getProductPrice(product_id).multiply(BigDecimal.valueOf(quantity))
                            .setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN));
        } else {
            // updates de quantity, sums the actual quantity with the new one
            commerceItem.setQuantity(commerceItem.getQuantity() + quantity);
            // updates the amount of hte commerce item
            commerceItem.setAmount(this.productService.getProductPrice(product_id)
                    .multiply(BigDecimal.valueOf(commerceItem.getQuantity()))
                    .setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN));
        }
        // return the commerceitem
        return commerceItem;
    }

    /**
     * Method that updates the ShoppingCart amount.
     *
     * @param shoppingCart the object to be updated
     */
    private void updateShoppingCartAmount(ShoppingCart shoppingCart) {
        // empty the shoppingcart amount
        shoppingCart.setAmount(new BigDecimal(0));
        // calculates the shoppingcart amount
        shoppingCart.getItems().forEach(item -> shoppingCart.setAmount(shoppingCart.getAmount().add(item.getAmount())
                .setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
    }
}
