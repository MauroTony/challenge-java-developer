package br.com.neurotech.challenge.exception;
import lombok.Data;

@Data
public class BodyError {
    private String message;
    private String details;
}
