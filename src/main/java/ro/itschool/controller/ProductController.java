package ro.itschool.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.itschool.csv.helper.CsvFileGenerator;
import ro.itschool.mapper.ProductMapper;
import ro.itschool.controller.model.ProductDTO;
import ro.itschool.csv.helper.CvsHelper;
import ro.itschool.entity.Client;
import ro.itschool.entity.Product;
import ro.itschool.entity.ShoppingCartProductQuantity;
import ro.itschool.exception.NoProductException;
import ro.itschool.repository.ProductRepository;
import ro.itschool.repository.ShoppingCartProductQuantityRepository;
import ro.itschool.service.ProductService;
import ro.itschool.service.impl.CartServiceImpl;
import ro.itschool.service.ClientService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ro.itschool.util.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private static Logger logger = Logger.getLogger(ProductController.class.getName());
    private final ProductService productService;
    private final CartServiceImpl cartService;
    private final ClientService clientService;
    private final ShoppingCartProductQuantityRepository quantityRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CsvFileGenerator csvFileGenerator;

    @RequestMapping(value = {"/all"})
    public String productList(Model model, String keyword) throws Exception {
        model.addAttribute("allProducts", productService.searchProduct(keyword));
        return PRODUCTS_PAGE;
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        logger.log(Level.INFO, "Method deleteProduct was used!");
        cartService.deleteProductByIdFromCart(id);
        productService.deleteById(id);
        return REDIRECT_TO_PRODUCTS;
    }

    @RequestMapping(value = "/add/{id}")
    public String addProductToCart(@PathVariable Integer id, @ModelAttribute("product") @RequestBody Product frontendProduct) throws NoProductException {
        logger.log(Level.INFO, "Method addProductToCart was used!");
        Optional<ProductDTO> desiredProductOptional = productService.findById(id);

        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        Integer quantityToBeOrdered = frontendProduct.getQuantity();
        if (quantityToBeOrdered == null) {
            throw new NoProductException("Quantity can't be null. Please select a number grater than 0!");
        }
        //in shopping cart-ul userului adus adaugam produsul trimis din frontend
        desiredProductOptional.ifPresent(desiredProduct -> {
            //setez pe produs quantity-ul si il adaug in shopping cart
            Product productToBeAddedToShoppingCart = new Product();
            productToBeAddedToShoppingCart.setId(desiredProduct.getId());
            productToBeAddedToShoppingCart.setPrice(desiredProduct.getPrice());
            productToBeAddedToShoppingCart.setName(desiredProduct.getName());
            productToBeAddedToShoppingCart.setQuantity(quantityToBeOrdered);
            //  clientByUsername.getCart().addProductToCart(productToBeAddedToShoppingCart);

            int remainingQuantity = desiredProduct.getQuantity() - quantityToBeOrdered;
            desiredProduct.setQuantity(remainingQuantity);


            Optional<ShoppingCartProductQuantity> cartProductQuantityOptional = quantityRepository.findByShoppingCartIdAndProductId(clientByUsername.getId().intValue(), desiredProduct.getId());
            if (cartProductQuantityOptional.isEmpty()) {
                quantityRepository.save(new ShoppingCartProductQuantity(clientByUsername.getId().intValue(), desiredProduct.getId(), quantityToBeOrdered));
            } else {
                cartProductQuantityOptional.ifPresent(cartProductQuantity -> {
                    cartProductQuantity.setQuantity(cartProductQuantity.getQuantity() + quantityToBeOrdered);
                    quantityRepository.save(cartProductQuantity);
                });
            }
            //productService.save(desiredProduct);
            clientService.updateClient(clientByUsername);
    });
        return REDIRECT_TO_PRODUCTS;
    }

    @RequestMapping(value = "/remove/{productId}")
    public String removeProductFromCart(@PathVariable Integer productId) {
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        quantityRepository.getProductsByShoppingCartId(clientByUsername.getId()).stream()
                .filter(p -> p.getId().equals(productId))
                .peek(p -> {
                    Optional<ProductDTO> byId = productService.findById(p.getId());
                    byId.ifPresent(pr -> {
                        pr.setQuantity(pr.getQuantity() + p.getQuantity());
                        productService.save(byId.get());
                    });
                })
                .findFirst();
        quantityRepository.deleteByShoppingCartIdAndProductId(clientByUsername.getId().intValue(), productId);

        return REDIRECT_TO_CART;
    }

    @RequestMapping(value = "/addToWishlist/{id}")
    public String addProductToWishlist(@PathVariable Integer id) {
        Optional<ProductDTO> optionalProduct = productService.findById(id);
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        //in shopping cart-ul userului adus adaugam produsul trimis din frontend
        optionalProduct.ifPresent(product -> {
            clientByUsername.getWishlist().addProductToWishlist(productMapper.toEntity(product));
            clientService.updateClient(clientByUsername);
        });
        return REDIRECT_TO_PRODUCTS;
    }


    @GetMapping(value = "/add-new")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return ADD_PRODUCT_PAGE;
    }

    @PostMapping(value = "/add-new")
    public String addProduct(@ModelAttribute("product") @RequestBody ProductDTO product) {
        productService.save(product);
        return REDIRECT_TO_PRODUCTS;
    }

    @PostMapping (value = "/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            List<Product> allProducts = CvsHelper.getProductsFromCsv(file).stream()
                    .map(ProductDTO::toEntity)
                    .collect(Collectors.toList());

            productRepository.saveAll(allProducts);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/export-to-csv")
    public void exportIntoCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"productList.csv\"");
        csvFileGenerator.writeProductsToCsv(productService.getProductsOfList(), response.getWriter());
    }

}




