package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.exception.CreditCheckException;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.repository.CreditTypeRepository;
import br.com.neurotech.challenge.entity.VehicleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditServiceImpl implements CreditService {
	private static final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

	private final NeurotechClientRepository clientRepository;
	private final CreditTypeRepository creditTypeRepository;

	@Autowired
	public CreditServiceImpl(NeurotechClientRepository clientRepository, CreditTypeRepository creditTypeRepository) {
		this.clientRepository = clientRepository;
		this.creditTypeRepository = creditTypeRepository;
	}

	@Override
	public boolean checkCredit(String clientId, VehicleModel model) {
		NeurotechClient client = clientRepository.findById(Long.parseLong(clientId)).orElse(null);
		if (client == null) {
			log.debug("No client found with ID: {}", clientId);
			return false;
		}
		log.debug("Checking credit for client: {}", client);
		return switch (model) {
			case HATCH -> client.getIncome() != null && client.getIncome() >= 5000.00 && client.getIncome() <= 15000.00;
			case SUV -> client.getIncome() != null && client.getIncome() > 8000.00 && client.getAge() != null && client.getAge() > 20;
		};
	}

	@Override
	@Transactional(readOnly = true)
	public CreditType getCreditType(Long clientId) {
		return creditTypeRepository.findById(clientId).orElse(null);
	}
}
