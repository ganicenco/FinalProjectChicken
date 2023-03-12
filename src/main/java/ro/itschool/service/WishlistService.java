package ro.itschool.service;

import ro.itschool.entity.Cart;
import ro.itschool.entity.Wishlist;

import java.util.Arrays;
import java.util.Optional;

public interface WishlistService {
    public Optional<Wishlist> findById(Integer id);

     Cart convertWishlistToCart(Wishlist wishlist);

     Wishlist update(Wishlist wishlist);

     Wishlist save(Wishlist wl);
     void deleteProductByIdFromWishlist(Integer productId);

}
