package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeurotechClientRepository extends JpaRepository<NeurotechClient, Long> {
}
