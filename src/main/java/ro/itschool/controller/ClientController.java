package ro.itschool.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.exception.NoClientFoundException;
import ro.itschool.service.ClientService;

import static ro.itschool.util.Constants.ALL_CLIENTS_PAGE;
import static ro.itschool.util.Constants.REDIRECT_TO_CLIENTS;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;


    @GetMapping("/clients")
    public String getAllClients(Model model, String keyword) throws NoClientFoundException {
       model.addAttribute("allClients", clientService.searchClient(keyword));

      return ALL_CLIENTS_PAGE;
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteClientById(Model model, @PathVariable("id") Long id) {
        clientService.deleteById(id);
        return REDIRECT_TO_CLIENTS;
    }


}
