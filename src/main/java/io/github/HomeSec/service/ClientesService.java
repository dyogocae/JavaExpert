package io.github.HomeSec.service;

import io.github.HomeSec.model.Cliente;
import io.github.HomeSec.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    private ClientesRepository repositoryCli;

    @Autowired
    public ClientesService (ClientesRepository repositoryCli){
      this.repositoryCli = repositoryCli;
    }

    public void SalvarCliente(Cliente cliente) {
        validarCliente(cliente);
        this.repositoryCli.persistir(cliente);
    }

    private void validarCliente(Cliente cliente) {
        //aplica as validações no cliente
    }
}
