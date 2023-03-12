package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Client;
import ro.itschool.repository.ClientRepository;
import ro.itschool.service.ClientService;

import java.util.Optional;

import static ro.itschool.util.Constants.*;

@Controller
@RequiredArgsConstructor
public class ActivationController {
    private final ClientService clientService;

    private final ClientRepository clientRepository;

    @GetMapping(value = "/activation/{randomToken}")
    public String registerForm(@PathVariable String randomToken, Model model) {
        final Optional<Client> clientByRandomToken = Optional.ofNullable(clientService.findClientByRandomToken(randomToken));
        if (clientByRandomToken.isPresent()) {
            model.addAttribute("client", clientByRandomToken.get());
            return ACTIVATION_PAGE;
        } else
            return INVALID_TOKEN_PAGE;

    }
    @PostMapping(value = "/activation/{randomToken}")
    public String registerClient(@ModelAttribute("client") @RequestBody Client c) {
        final Client client = clientService.findClientByRandomToken(c.getRandomToken());
        client.setEnabled(true);
        clientRepository.save(client);
        return ACTIVATION_SUCCESS_PAGE;
    }

}