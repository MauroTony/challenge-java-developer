package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.CheckEligibleDTO;
import br.com.neurotech.challenge.dto.CreditTypeDTO;
import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.exception.BodyError;
import br.com.neurotech.challenge.exception.VehicleNotFoundException;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/credit")
@Tag(name = "Credit Controller", description = "Operations related to credit client")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("/check/{clientId}/{vehicleModel}")
    @Operation(summary = "Check if client is eligible for vehicle credit (hatch or suv)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client is eligible for credit", content = { @Content(schema = @Schema(implementation = CheckEligibleDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Vehicle model not found", content = { @Content(schema = @Schema(implementation = BodyError.class)) }) })
    public ResponseEntity<CheckEligibleDTO> checkCredit(
            @PathVariable @Parameter(description = "ID referring to the client", required = true) String clientId,
            @PathVariable @Parameter(description = "Name referring to the type of vehicle", required = true, example = "HATCH ou SUV") String vehicleModel) {
        try{
            VehicleModel vehicle = VehicleModel.valueOf(vehicleModel.toUpperCase());
            boolean isEligible = creditService.checkCredit(clientId, vehicle);

            return ResponseEntity.ok(new CheckEligibleDTO(vehicleModel, isEligible));
        }
        catch (IllegalArgumentException e){
            throw new VehicleNotFoundException("Invalid vehicle model: " + vehicleModel);
        }
    }

    @GetMapping("/type/{clientId}")
    @Operation(summary = "Get credit type for a client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credit type found", content = { @Content(schema = @Schema(implementation = CreditTypeDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Credit type not found", content = { @Content(schema = @Schema(implementation = BodyError.class)) }) })
    public ResponseEntity<CreditTypeDTO> getCreditType(@PathVariable Long clientId) {
        CreditType creditOption = creditService.getCreditType(clientId);
        CreditTypeDTO creditTypeDTO = new CreditTypeDTO(creditOption.getCreditOption());
        return ResponseEntity.ok(creditTypeDTO);
    }

    @GetMapping("/credit-fixed/clients-profile/{vehicleModel}")
    @Operation(summary = "Get clients profile who have fixed credit eligible for vehicle credit (hatch or suv)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clients profile found", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = NeurotechClientDTO.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Vehicle model not found", content = { @Content(schema = @Schema(implementation = BodyError.class)) }) })
    public ResponseEntity<List<NeurotechClientDTO>> getClientProfile(
            @PathVariable @Parameter(description = "Name referring to the type of vehicle", example = "HATCH ou SUV") String vehicleModel,
            @RequestParam(required = false, defaultValue = "23") int minAge,
            @RequestParam(required = false, defaultValue = "49")  int maxAge
    ) {
        List<NeurotechClient> clients = creditService.getClientsEligibleForVehicleModel(CreditType.CreditOption.FIXED, minAge, maxAge, vehicleModel);
        List<NeurotechClientDTO> clientDTOs = clients.stream()
                .map(client -> new NeurotechClientDTO(client.getName(), client.getAge(), client.getIncome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientDTOs);
    }
}