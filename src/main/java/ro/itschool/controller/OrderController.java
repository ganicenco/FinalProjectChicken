package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Client;
import ro.itschool.entity.Order;
import ro.itschool.exception.NoClientFoundException;
import ro.itschool.repository.ClientRepository;
import ro.itschool.repository.OrderRepository;
import ro.itschool.service.ClientService;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import static ro.itschool.util.Constants.ORDER_LIST_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    private final ClientService clientService;


    @GetMapping(value = "/get-all-orders/{clientId}")
    public List<Order> getAllOrders(@PathVariable Long clientId) throws NoClientFoundException {

        Optional<Client> optionalClient = clientRepository.findById(clientId);
        //stabilim care e username-ul user-ului autentificat
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //aducem userul din db pe baza username-ului
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        if (optionalClient.isPresent())
            return optionalClient.get().getOrders();
        else
            throw new NoClientFoundException("Client not found.");
    }
    @GetMapping("/orders")
    public String getOrders(Model model) throws Exception {
        model.addAttribute("allOrders", orderRepository.findAll());
        return ORDER_LIST_PAGE;
    }
}
