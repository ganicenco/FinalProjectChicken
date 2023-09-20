package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.itschool.entity.Client;
import ro.itschool.repository.RoleRepository;
import ro.itschool.service.ClientService;
import ro.itschool.util.Constants;
import ro.itschool.util.PasswordValidator;

import java.util.Set;

import static ro.itschool.util.Constants.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final RoleRepository roleRepository;

    private final ClientService clientService;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        Client client = new Client();
        client.setAccountNonExpired(true);
        client.setAccountNonLocked(true);
        client.setCredentialsNonExpired(true);
        client.setEnabled(false);
        model.addAttribute("client", client);
        return REGISTER_PAGE;
    }

    @PostMapping(value = "/register")
    public String registerClient(@ModelAttribute("client") @RequestBody Client client) {
        if (!PasswordValidator.isPasswordValid(client.getPassword())) {
            return REGISTER_FAILED;
        }
        if (client.getPassword().equals(client.getPasswordConfirm())) {
            client.setRoles(Set.of(roleRepository.findByName(Constants.ROLE_USER)));
            clientService.saveClient(client);
            return REGISTER_SUCCESS_PAGE;
        } else {
            return REGISTER_PAGE;
        }
    }

}

