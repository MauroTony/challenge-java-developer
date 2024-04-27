package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.BodyError;
import br.com.neurotech.challenge.service.ClientService;
import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Controller", description = "Operations related to clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Objeto criado com sucesso", headers = { @Header(name = "Location", description = "Url do objeto criado")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<String> createClient(@Valid @RequestBody @Parameter(description  = "Client Data Model", required = true) NeurotechClientDTO clientDTO) {

        NeurotechClient client = new NeurotechClient();
        BeanUtils.copyProperties(clientDTO, client);
        String clientId = clientService.save(client);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objeto encontrado com sucesso", content = { @Content(schema = @Schema(implementation = NeurotechClientDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado", content = { @Content(schema = @Schema(implementation = BodyError.class)) }) })
    public ResponseEntity<NeurotechClientDTO> getClient(@PathVariable @Parameter(description = "ID referring to the client", required = true) String id) {
        NeurotechClient client = clientService.get(id);
        return client != null ? new ResponseEntity<>(new NeurotechClientDTO(client.getName(), client.getAge(), client.getIncome()), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Objeto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Objeto não encontrado", content = { @Content(schema = @Schema(implementation = BodyError.class)) }) })
    public ResponseEntity<Void> deleteClient(@PathVariable @Parameter(description = "ID referring to the client", required = true) String id) {
        clientService.deleteClient(Long.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
