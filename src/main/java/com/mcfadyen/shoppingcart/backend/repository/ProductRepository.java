package com.mcfadyen.shoppingcart.backend.repository;

import com.mcfadyen.shoppingcart.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product entity.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    /**
     * Find product by name containing the given string.
     *
     * @param name partial name to search
     * @return list of matching products
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find all products sorted by price ascending.
     *
     * @return list of products sorted by price
     */
    List<Product> findAllByOrderByPriceAsc();
    
    /**
     * Find products within price range.
     *
     * @param minPrice minimum price
     * @param maxPrice maximum price
     * @return list of products in price range
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}