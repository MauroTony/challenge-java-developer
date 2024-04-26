package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.CreditTypeDTO;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("/check/{clientId}/{model}")
    public ResponseEntity<Boolean> checkCredit(@PathVariable String clientId, @PathVariable VehicleModel model) {
        boolean isEligible = creditService.checkCredit(clientId, model);
        return ResponseEntity.ok(isEligible);
    }

    @GetMapping("/type/{clientId}")
    public ResponseEntity<CreditTypeDTO> getCreditType(@PathVariable Long clientId) {
        CreditType creditOption = creditService.getCreditType(clientId);
        CreditTypeDTO creditTypeDTO = new CreditTypeDTO(creditOption.getCreditOption());
        return ResponseEntity.ok(creditTypeDTO);
    }

}