package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
    Optional<CreditType> findByClientId(Long clientId);
    Optional<CreditType> deleteByClientId(Long clientId);
    @Query("SELECT c, ct FROM CreditType ct " +
            "JOIN FETCH ct.client c " +
            "WHERE ct.creditOption = :creditOption AND c.age > :minAge AND c.age < :maxAge")
    List<NeurotechClient> findClientsByCreditOptionAndAgeRangeAndIncomeRange(CreditType.CreditOption creditOption, int minAge, int maxAge);
}