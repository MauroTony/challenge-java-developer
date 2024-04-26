package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
    Optional<CreditType> findByClientId(Long clientId);
}