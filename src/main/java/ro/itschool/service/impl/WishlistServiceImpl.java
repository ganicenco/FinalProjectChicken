package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Cart;
import ro.itschool.entity.Wishlist;
import ro.itschool.repository.WishlistRepository;
import ro.itschool.service.WishlistService;

import java.util.Optional;
@Service
@ToString
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    public Optional<Wishlist> findById(Integer id) {
        return wishlistRepository.findById(id);
    }

    public Cart convertWishlistToCart(Wishlist wishlist) {
        Cart cart = new Cart();
        cart.getProducts().addAll(wishlist.getProducts());
        cart.setClient(wishlist.getClient()); //cu SpringSecurity folosim Principal - adica usarul care este logat in momentul de fata
        return cart;

    }

    public Wishlist update(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    public Wishlist save(Wishlist wl) {
        return wishlistRepository.save(wl);
    }

    public void deleteProductByIdFromWishlist(Integer productId) {
        wishlistRepository.findAll().stream()
                .filter(wishlist -> wishlist.getProducts().removeIf(product -> product.getId().equals(productId)))
                .toList();
    }

}
