package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.repository.CreditTypeRepository;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.ClientService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientServiceImpl implements ClientService {
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	private final NeurotechClientRepository clientRepository;
	private final CreditTypeRepository creditTypeRepository;

	@Autowired
	public ClientServiceImpl(NeurotechClientRepository clientRepository, CreditTypeRepository creditTypeRepository) {
		this.clientRepository = clientRepository;
		this.creditTypeRepository = creditTypeRepository;
	}

	@Override
	@Transactional
	public String save(NeurotechClient client) {
		log.debug("Saving client: {}", client);
		NeurotechClient savedClient = clientRepository.save(client);
		CreditType creditType = new CreditType();
		creditType.setClient(savedClient);
		if(savedClient.getAge() >= 21 && savedClient.getAge() <= 65 && savedClient.getIncome() >= 5000 && savedClient.getIncome() <= 15000) {
			creditType.setCreditOption(CreditType.CreditOption.VARIABLE);
		} else if (savedClient.getAge() >= 18 && savedClient.getAge() <= 25) {
			creditType.setCreditOption(CreditType.CreditOption.FIXED);
		} else if (savedClient.getAge() > 65) {
			creditType.setCreditOption(CreditType.CreditOption.CONSIGNED);
		} else {
			creditType.setCreditOption(CreditType.CreditOption.NOT_ELIGIBLE);
		}
		log.info("Client is {}", creditType.getCreditOption());
		creditTypeRepository.save(creditType);
		log.info("Client saved with ID: {}", savedClient.getId());
		return savedClient.getId().toString();
	}

	@Override
	@Transactional(readOnly = true)
	public NeurotechClient get(String id) {
		log.debug("Retrieving client with ID: {}", id);
		return clientRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ClientNotFoundException("Client with ID " + id + " not found."));
	}
}
