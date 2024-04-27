package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.ClientNotFoundException;
import br.com.neurotech.challenge.repository.CreditTypeRepository;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.neurotech.challenge.contants.Credit.*;

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
		if(	savedClient.getAge() >= AGE_MIN_CREDIT_VARIABLE
			&& savedClient.getAge() <= AGE_MAX_CREDIT_VARIABLE
			&& savedClient.getIncome() > INCOME_MIN_CREDIT_VARIABLE
			&& savedClient.getIncome() < INCOME_MAX_CREDIT_VARIABLE
		) {
			creditType.setCreditOption(CreditType.CreditOption.VARIABLE);
		} else if (savedClient.getAge() >= AGE_MIN_CREDIT_FIXED && savedClient.getAge() <= AGE_MAX_CREDIT_FIXED) {
			creditType.setCreditOption(CreditType.CreditOption.FIXED);
		} else if (savedClient.getAge() > AGE_MIN_CREDIT_CONSIGNED) {
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

	@Override
	@Transactional
	public void deleteClient(Long clientId) throws ClientNotFoundException {
		if (!clientRepository.existsById(clientId)) {
			throw new ClientNotFoundException("Client with ID " + clientId + " not found.");
		}

		creditTypeRepository.deleteByClientId(clientId);

		clientRepository.deleteById(clientId);
		log.info("Deleted client with ID: {}", clientId);
	}

}
