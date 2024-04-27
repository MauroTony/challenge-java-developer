package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.exception.ClientNotFoundException;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * 
	 * @return ID do cliente recém-salvo
	 */
	String save(NeurotechClient client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 */
	NeurotechClient get(String id);

    void deleteClient(Long clientId) throws ClientNotFoundException;
}
