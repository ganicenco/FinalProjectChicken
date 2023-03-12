package ro.itschool.mapper;

import org.springframework.stereotype.Component;
import ro.itschool.controller.model.ClientDTO;
import ro.itschool.entity.Client;
@Component
public class ClientMapper {
        public ClientDTO fromEntity (Client client) {
            return new ClientDTO(client.getName(), client.getSurname(), client.getPhoneNumber(), client.getRoles());
        }

        public Client toEntity(ClientDTO clientDTO) {
            return new Client(clientDTO.getName(), clientDTO.getSurname(), clientDTO.getPhoneNumber(), clientDTO.getRoles());
        }
    }

