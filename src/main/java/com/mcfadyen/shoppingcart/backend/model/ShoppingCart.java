package com.mcfadyen.shoppingcart.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class responsible for ShoppingCart objects.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -6779957335984353807L;

    private List<CommerceItem> items;
    private BigDecimal amount;

    /**
     * Main empty constructor.
     */
    public ShoppingCart() {
        items = new ArrayList<>();
        amount = new BigDecimal(0);
    }

    /**
     * Optional constructor.
     *
     * @param items  list of commerceitems in the shoppingcart
     * @param amount sum of all commerceitems amounts in the shoppingcart
     */
    public ShoppingCart(List<CommerceItem> items, BigDecimal amount) {
        this.items = items;
        this.amount = amount;
    }

    public List<CommerceItem> getItems() {
        return items;
    }

    public void setItems(List<CommerceItem> items) {
        this.items = items;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
