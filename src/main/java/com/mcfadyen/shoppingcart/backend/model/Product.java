package com.mcfadyen.shoppingcart.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Model class responsible for product objects.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {

    private static final long serialVersionUID = 8384078803744807457L;

    private String id;
    private String name;
    private String image;
    private BigDecimal price;

    /**
     * Main empty constructor.
     */
    public Product() {
    }

    /**
     * Optional constructor.
     *
     * @param id    product unique identification
     * @param name  product name
     * @param image product image address
     * @param price product price
     */
    public Product(final String id, final String name, final String image, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id)
                && Objects.equals(name, product.name)
                && Objects.equals(image, product.image)
                && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, image, price);
    }

    @Override
    public String toString() {
        return "Product{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", image='" + image + '\''
                + ", price=" + price
                + '}';
    }
}
