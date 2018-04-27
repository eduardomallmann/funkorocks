package com.mcfadyen.shoppingcart.backend.service;

import com.mcfadyen.shoppingcart.backend.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class responsible for the business logic of the {@link Product} object.
 *
 * @author Eduardo Mallmann
 * @since 0.0.1
 */
@Service
public class ProductService {

    private static List<Product> products = new ArrayList<>();

    private static final Integer BIGDECIMAL_SCALE = 2;
    private static final Double JOEY_RAMONE_PRICE = 5.98;
    private static final Double JIMI_HENDRIX_PRICE = 8.10;
    private static final Double LEMMY_KILMISTER_PRICE = 9.94;
    private static final Double KURT_COBAIN_PRICE = 10.00;
    private static final Double AXL_ROSE_PRICE = 5.99;
    private static final Double DUFF_MCKAGAN_PRICE = 9.98;
    private static final Double SLASH_PRICE = 12.52;
    private static final Double KIRK_HAMMETT_PRICE = 8.25;
    private static final Double LARS_ULRICH_PRICE = 7.66;
    private static final Double ROBERT_TRUJILLO_PRICE = 7.99;
    private static final Double JAMES_HETFIELD_PRICE = 6.63;
    private static final Double VINCE_NEIL_PRICE = 10.99;
    private static final Double NIKKI_SIX_PRICE = 10.99;
    private static final Double TOMMY_LEE = 10.99;
    private static final Double MICK_MARS_PRICE = 10.99;


    /**
     * Static block responsible to create and add the product objects in its list.
     */
    static {
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Joey Ramone - Ramones",
                "https://images-na.ssl-images-amazon.com/images/I/41i3tE9rSlL.jpg",
                new BigDecimal(JOEY_RAMONE_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Jimi Hendrix",
                "https://images-na.ssl-images-amazon.com/images/I/41Baiq1QkpL.jpg",
                new BigDecimal(JIMI_HENDRIX_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Lemmy Kilmister - Motörhead",
                "https://images-na.ssl-images-amazon.com/images/I/51WpJ-s-D6L.jpg",
                new BigDecimal(LEMMY_KILMISTER_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Kurt Cobain - Nirvana",
                "https://images-na.ssl-images-amazon.com/images/I/41QG9-Ewk-L.jpg",
                new BigDecimal(KURT_COBAIN_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Axl Rose - Guns N’Roses",
                "https://images-na.ssl-images-amazon.com/images/I/61z3DkOYbHL._SL1221_.jpg",
                new BigDecimal(AXL_ROSE_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Duff Mckagan - Guns N’Roses",
                "https://images-na.ssl-images-amazon.com/images/I/51AUT%2BpucUL.jpg",
                new BigDecimal(DUFF_MCKAGAN_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Slash - Guns N’Roses",
                "https://images-na.ssl-images-amazon.com/images/I/41HZYtnAMfL.jpg",
                new BigDecimal(SLASH_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Kirk Hammett - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/51FRYtesEOL.jpg",
                new BigDecimal(KIRK_HAMMETT_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Lars Ulrich - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/41KVf8XtVcL.jpg",
                new BigDecimal(LARS_ULRICH_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Robert Trujillo - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/51iKUKv6igL.jpg",
                new BigDecimal(ROBERT_TRUJILLO_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "James Hetfield - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/41MxOyKK9gL.jpg",
                new BigDecimal(JAMES_HETFIELD_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Vince Neil - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/51t%2BrDpYpGL.jpg",
                new BigDecimal(VINCE_NEIL_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Nikki Six - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/41ZfjnVvUyL.jpg",
                new BigDecimal(NIKKI_SIX_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Tommy Lee - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/51vvWZSX7UL.jpg",
                new BigDecimal(TOMMY_LEE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Mick Mars - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/51cEt69h7AL.jpg",
                new BigDecimal(MICK_MARS_PRICE).setScale(BIGDECIMAL_SCALE, BigDecimal.ROUND_HALF_EVEN)));
    }

    /**
     * Method that finds all the objects of the product model.
     *
     * @return list with all the product objects
     */
    public List<Product> findAllProducts() {
        return products;
    }

    /**
     * Method that find the price of the product by its id.
     *
     * @param product_id product unique identification
     * @return bigdecimal with the price of the product
     * @throws Exception in case of error during the search
     */
    public BigDecimal getProductPrice(final String product_id) throws Exception {
        // find and return the product price of the informed id
        return products.stream().filter(product -> product.getId().equals(product_id)).map(Product::getPrice).findAny()
                .orElseThrow(Exception::new);
    }
}
