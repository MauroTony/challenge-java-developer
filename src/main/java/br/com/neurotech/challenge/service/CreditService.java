package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.VehicleModel;

public interface CreditService {

	/**
	 * Efetua a checagem se o cliente está apto a receber crédito
	 * para um determinado modelo de veículo.
	 *
	 * @param clientId O identificador do cliente.
	 * @param model O modelo do veículo.
	 * @return true se o cliente está apto para crédito para o modelo especificado, false caso contrário.
	 */
	boolean checkCredit(String clientId, VehicleModel model);

	/**
	 * Retorna o tipo de crédito para um cliente baseado no seu ID.
	 *
	 * @param clientId O identificador do cliente.
	 * @return Um Optional contendo o tipo de crédito do cliente se presente, ou um Optional vazio se nenhum crédito foi encontrado.
	 */
	CreditType getCreditType(Long clientId);
}
