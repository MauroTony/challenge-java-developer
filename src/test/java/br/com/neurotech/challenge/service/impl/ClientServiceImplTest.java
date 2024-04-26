package br.com.neurotech.challenge.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.repository.CreditTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ClientServiceImplTest {

    @Mock
    private NeurotechClientRepository clientRepository;
    @Mock
    private CreditTypeRepository creditTypeRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setupClientWithId(NeurotechClient client, Long id) {
        client.setId(id);
        when(clientRepository.save(any(NeurotechClient.class))).thenReturn(client);
    }

    @Test
    public void testSaveClientVariableCredit() {
        NeurotechClient client = new NeurotechClient();
        client.setAge(30);
        client.setIncome(10000.0);
        setupClientWithId(client, 1L);

        doAnswer(invocation -> {
            CreditType creditType = invocation.getArgument(0);
            assertEquals(CreditType.CreditOption.VARIABLE, creditType.getCreditOption());
            return null;
        }).when(creditTypeRepository).save(any(CreditType.class));

        String clientId = clientService.save(client);
        assertEquals("1", clientId);

        verify(creditTypeRepository, times(1)).save(any(CreditType.class));
    }

    @Test
    public void testSaveClientFixedCredit() {
        NeurotechClient client = new NeurotechClient();
        client.setAge(23);
        client.setIncome(3000.0);
        setupClientWithId(client, 2L);

        doAnswer(invocation -> {
            CreditType creditType = invocation.getArgument(0);
            assertEquals(CreditType.CreditOption.FIXED, creditType.getCreditOption());
            return null;
        }).when(creditTypeRepository).save(any(CreditType.class));

        String clientId = clientService.save(client);
        assertEquals("2", clientId);

        verify(creditTypeRepository, times(1)).save(any(CreditType.class));
    }

    @Test
    public void testSaveClientConsignedCredit() {
        NeurotechClient client = new NeurotechClient();
        client.setAge(70);
        setupClientWithId(client, 3L);

        doAnswer(invocation -> {
            CreditType creditType = invocation.getArgument(0);
            assertEquals(CreditType.CreditOption.CONSIGNED, creditType.getCreditOption());
            return null;
        }).when(creditTypeRepository).save(any(CreditType.class));

        String clientId = clientService.save(client);
        assertEquals("3", clientId);

        verify(creditTypeRepository, times(1)).save(any(CreditType.class));
    }
}
