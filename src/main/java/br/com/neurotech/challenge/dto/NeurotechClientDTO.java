package br.com.neurotech.challenge.dto;
import jakarta.validation.constraints.*;

public record NeurotechClientDTO (
    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 1, message = "O nome não pode estar vazio")
    String name,

    @NotNull(message = "A idade não pode ser nula")
    @Min(value = 18, message = "A idade deve ser maior ou igual a 18")
    Integer age,

    @NotNull(message = "A renda não pode ser nula")
    @DecimalMin(value = "0.0", inclusive = false, message = "A renda deve ser maior que 0")
    Double income

) {}