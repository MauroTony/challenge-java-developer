package br.com.neurotech.challenge.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CreditServiceImplTest {

    @Mock
    private NeurotechClientRepository clientRepository;

    @InjectMocks
    private CreditServiceImpl creditService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckCreditForHatchEligible() {
        NeurotechClient client = new NeurotechClient();
        client.setAge(30);
        client.setIncome(7000.0);  // Within the range for HATCH

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        boolean isEligible = creditService.checkCredit("1", VehicleModel.HATCH);

        assertTrue(isEligible);
    }

    @Test
    public void testCheckCreditForSuvNotEligible() {
        NeurotechClient client = new NeurotechClient();
        client.setAge(19);  // Not eligible due to age
        client.setIncome(9000.0);

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        boolean isEligible = creditService.checkCredit("1", VehicleModel.SUV);

        assertFalse(isEligible);
    }

    @Test
    public void testCheckCreditForSuvEligible() {
        NeurotechClient client = new NeurotechClient();
        client.setAge(25);  // Not eligible due to age
        client.setIncome(10000.0);

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        boolean isEligible = creditService.checkCredit("1", VehicleModel.SUV);

        assertTrue(isEligible);
    }
}
