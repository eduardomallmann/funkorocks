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

    /**
     * Static block responsible to create and add the product objects in its list.
     */
    static {
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Joey Ramone - Ramones",
                "https://images-na.ssl-images-amazon.com/images/I/41i3tE9rSlL.jpg", new BigDecimal(5.98)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Elton John",
                "https://images-na.ssl-images-amazon.com/images/I/41-Cu54MYqL.jpg", new BigDecimal(10.30)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Jimi Hendrix",
                "https://images-na.ssl-images-amazon.com/images/I/41Baiq1QkpL.jpg", new BigDecimal(8.10)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Lemmy Kilmister - Motörhead",
                "https://images-na.ssl-images-amazon.com/images/I/51WpJ-s-D6L.jpg", new BigDecimal(9.94)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Kurt Cobain - Nirvana",
                "https://images-na.ssl-images-amazon.com/images/I/41QG9-Ewk-L.jpg", new BigDecimal(10.00)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Axl Rose - Guns N’Roses",
                "https://images-na.ssl-images-amazon.com/images/I/61z3DkOYbHL._SL1221_.jpg",
                new BigDecimal(5.99)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Duff Mckagan - Guns N’Roses",
                "https://images-na.ssl-images-amazon.com/images/I/51AUT%2BpucUL.jpg", new BigDecimal(9.98)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Slash - Guns N’Roses",
                "https://images-na.ssl-images-amazon.com/images/I/41HZYtnAMfL.jpg", new BigDecimal(12.52)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Kirk Hammett - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/51FRYtesEOL.jpg", new BigDecimal(8.25)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Lars Ulrich - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/41KVf8XtVcL.jpg", new BigDecimal(7.66)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Robert Trujillo - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/51iKUKv6igL.jpg", new BigDecimal(7.99)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "James Hetfield - Metallica",
                "https://images-na.ssl-images-amazon.com/images/I/41MxOyKK9gL.jpg", new BigDecimal(6.63)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Vince Neil - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/51t%2BrDpYpGL.jpg", new BigDecimal(10.99)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Nikki Six - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/41ZfjnVvUyL.jpg", new BigDecimal(10.99)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Tommy Lee - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/51vvWZSX7UL.jpg", new BigDecimal(10.99)));
        products.add(new Product(String.valueOf(UUID.randomUUID()), "Mick Mars - Mötley Crüe",
                "https://images-na.ssl-images-amazon.com/images/I/51cEt69h7AL.jpg", new BigDecimal(10.99)));
    }

    /**
     * Method that finds all the objects of the product model.
     *
     * @return list with all the product objects
     */
    public List<Product> findAllProducts() {
        return products;
    }
}
