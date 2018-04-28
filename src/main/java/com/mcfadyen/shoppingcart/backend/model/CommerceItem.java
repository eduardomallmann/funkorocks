package com.mcfadyen.shoppingcart.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Model class responsible for commerce item objects.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommerceItem implements Serializable {

    private static final long serialVersionUID = 5668623572369327551L;

    private String id;
    private String product_id;
    private Integer quantity;
    private BigDecimal amount;

    /**
     * Main empty constructor.
     */
    public CommerceItem() {
    }

    /**
     * Optional constructor.
     *
     * @param id         commerce item unique identification for shoppingcart
     * @param product_id product object identification
     * @param quantity   quantity of the product
     * @param amount     total value of the product (quantity x product price)
     */
    public CommerceItem(String id, String product_id, Integer quantity, BigDecimal amount) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommerceItem that = (CommerceItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(product_id, that.product_id) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, product_id, quantity, amount);
    }

    @Override
    public String toString() {
        return "CommerceItem{" +
                "id='" + id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}
