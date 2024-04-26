package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.NeurotechClient;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class NeurotechClientRepositoryTests {

    @Autowired
    private NeurotechClientRepository clientRepository;

    @Test
    public void testSaveClient() {
        NeurotechClient client = new NeurotechClient();
        client.setName("John Doe");
        client.setAge(30);
        client.setIncome(12000.00);
        NeurotechClient savedClient = clientRepository.save(client);
        assertThat(savedClient.getId()).isNotNull();
    }
}