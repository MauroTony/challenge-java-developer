package br.com.neurotech.challenge.dto;

import br.com.neurotech.challenge.entity.CreditType;

public record CreditTypeDTO (
    CreditType.CreditOption creditOption
) {}
