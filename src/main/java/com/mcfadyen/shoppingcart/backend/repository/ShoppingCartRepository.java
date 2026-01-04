package com.mcfadyen.shoppingcart.backend.repository;

import com.mcfadyen.shoppingcart.backend.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for ShoppingCart entity.
 *
 * @author Eduardo Mallmann
 * @since 0.0.2
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    
    /**
     * Find shopping cart by user identifier.
     *
     * @param userId user identifier
     * @return optional shopping cart
     */
    Optional<ShoppingCart> findByUserId(String userId);
    
    /**
     * Find shopping cart with items eagerly loaded.
     *
     * @param cartId cart identifier
     * @return optional shopping cart with items
     */
    Optional<ShoppingCart> findByIdWithItems(String cartId);
}