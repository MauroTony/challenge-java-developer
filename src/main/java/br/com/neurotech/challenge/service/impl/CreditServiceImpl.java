package br.com.neurotech.challenge.service.impl;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.exception.CreditCheckException;
import br.com.neurotech.challenge.exception.VehicleNotFoundException;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.repository.CreditTypeRepository;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.CreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
		return creditTypeRepository.findById(clientId).orElseThrow(() -> new CreditCheckException("Credit type not found for client ID " + clientId));
	}

	@Override
	@Transactional(readOnly = true)
	public List<NeurotechClient> getClientsEligibleForVehicleModel(CreditType.CreditOption creditOption, int minAge, int maxAge, String vehicleModel) {
		List<NeurotechClient> Clients = creditTypeRepository.findClientsByCreditOptionAndAgeRangeAndIncomeRange(creditOption, minAge, maxAge);
		try{
			VehicleModel vehicle = VehicleModel.valueOf(vehicleModel.toUpperCase());
			if (vehicle == VehicleModel.HATCH)
				Clients = Clients.stream().filter(client -> client.getIncome() >= 5000 && client.getIncome() <= 15000).collect(Collectors.toList());
			else if (vehicle == VehicleModel.SUV)
				Clients = Clients.stream().filter(client -> client.getIncome() >= 8000 && client.getAge() > 20).collect(Collectors.toList());
		}
		catch (IllegalArgumentException e){
			log.error("Invalid vehicle model: {}", vehicleModel);
			throw new VehicleNotFoundException("Invalid vehicle model: " + vehicleModel);
		}
		log.debug("Found {} clients eligible for credit option {} with age between {} and {}", Clients.size(), creditOption, minAge, maxAge);
		log.trace("Clients: {}", Clients);
		return Clients;
	}
}
