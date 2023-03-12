package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Client;
import ro.itschool.entity.Contact;
import ro.itschool.repository.ContactRepository;

import static ro.itschool.util.Constants.*;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactRepository contactRepository;

    @GetMapping("/contact-message")
    public String getContact(Model model, String keyword) {
        model.addAttribute("contacts", contactRepository.searchContact(keyword));
        return CONTACT_MESSAGE;
    }

    @GetMapping("/contact")
    public String saveContact(Model model) {
        model.addAttribute("contactObject", new Contact());
        return CONTACT_PAGE;
    }

    @PostMapping("/contact")
    public String saveContact2(@ModelAttribute Contact contact, Model model, Client client) throws Exception {
        model.addAttribute("contactObject", contact);
        if (contact.getUsername().equals((client.getUsername()))) {
            contactRepository.save(contact);
            return REDIRECT_TO_CONTACT;
        } else
            throw new Exception("Wrong username");
    }

    @RequestMapping(path = "/delete-contact/{id}")
    public String deleteContact(@PathVariable("id") Integer id) {
        contactRepository.deleteById(id);
        return CONTACT_MESSAGE;
    }
}