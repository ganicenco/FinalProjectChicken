package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Client;
import ro.itschool.entity.Product;
import ro.itschool.entity.ShoppingCartProductQuantity;
import ro.itschool.repository.*;
import ro.itschool.service.ClientService;
import ro.itschool.service.impl.WishlistServiceImpl;

import java.util.Optional;

import static ro.itschool.util.Constants.REDIRECT_TO_WISHLIST;
import static ro.itschool.util.Constants.WISHLIST_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/wishlist")

public class WishlistController {

    public final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    private final WishlistServiceImpl wishlistService;
    private final ClientService clientService;

    private final CartRepository cartRepository;

    private final ShoppingCartProductQuantityRepository quantityRepository;


    @RequestMapping(value = "/to-cart/{productId}")
    public String addToCart(@PathVariable Integer productId) {
        //cautam produsul dupa id
        Optional<Product> optionalProduct = productRepository.findById(productId);

        //stabilim care e username-ul user-ului autentificat
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        //aducem userul din db pe baza username-ului
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        //Vrem sa adaugam produsul din wishlist in shoppingCart
        //shoppingCartId, productId, quantity

        ShoppingCartProductQuantity shoppingCartProductQuantity = new ShoppingCartProductQuantity();
        shoppingCartProductQuantity.setProductId(optionalProduct.get().getId());
        shoppingCartProductQuantity.setShoppingCartId(clientByUsername.getId().intValue());
        shoppingCartProductQuantity.setQuantity(optionalProduct.get().getQuantity());

        quantityRepository.save(shoppingCartProductQuantity);


//        List<Product> productsByWishlistId = wlquantityRepository.getProductsByWishlistId(clientByUsername.getId());
//        wishlistService.findById(clientByUsername.getId().intValue()).ifPresent(wishlist -> {
//            wishlist.setProducts(productsByWishlistId);
//            clientByUsername.setWishlist(wishlist);
//        });

        //in cart-ul userului adus adaugam produsul trimis din frontend
      /*  optionalProduct.ifPresent(product -> {
            clientByUsername.getCart().addProductToCart(product);
           clientService.updateClient(clientByUsername);
        });


       */
       /* cartRepository.save(wishlistService.convertWishlistToCart(clientByUsername.getWishlist()));
        clientByUsername.getWishlist().getProducts().clear();
        clientService.updateClient(clientByUsername);


        */
        return REDIRECT_TO_WISHLIST;
    }

    @RequestMapping
    public String getWishlistForPrincipal(Model model) {
        //stabilim care e username-ul user-ului autentificat
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //aducem userul din db pe baza username-ului
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        model.addAttribute("products", clientByUsername.getWishlist().getProducts());

        return WISHLIST_PAGE;
    }

    @RequestMapping(value = "/product/remove/{productId}") //quatity = quantity - 1 ??
    public String removeProductFromWishlist(@PathVariable Integer productId) {
        //stabilim care e username-ul user-ului autentificat
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //aducem userul din db pe baza username-ului
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        Optional<Product> optionalProduct = productRepository.findById(productId);

        clientByUsername.getWishlist().getProducts().removeIf(product -> product.getId().equals(productId));

        clientService.updateClient(clientByUsername);

        return REDIRECT_TO_WISHLIST;
    }
}
