package com.mcfadyen.shoppingcart.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Model class responsible for ShoppingCart objects.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
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
    public ShoppingCart(final List<CommerceItem> items, final BigDecimal amount) {
        this.items = items;
        this.amount = amount;
    }

    public List<CommerceItem> getItems() {
        return items;
    }

    public void setItems(final List<CommerceItem> items) {
        this.items = items;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(items, that.items)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(items, amount);
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "items=" + Arrays.toString(items.toArray())
                + ", amount=" + amount
                + '}';
    }
}
