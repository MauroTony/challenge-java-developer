package br.com.neurotech.challenge.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
public class CreditType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CreditOption creditOption;

    @ManyToOne
    private NeurotechClient client;

    public enum CreditOption {
        FIXED, VARIABLE, CONSIGNED, NOT_ELIGIBLE
    }
}
