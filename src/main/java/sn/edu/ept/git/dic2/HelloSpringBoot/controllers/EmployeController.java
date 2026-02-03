package sn.edu.ept.git.dic2.HelloSpringBoot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sn.edu.ept.git.dic2.HelloSpringBoot.entities.Employe;
import sn.edu.ept.git.dic2.HelloSpringBoot.exceptions.ApiException;
import sn.edu.ept.git.dic2.HelloSpringBoot.services.EmployeService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employes")
//@CrossOrigin(origins = "http://localhost:4200")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping
    public List<Employe> findAll(Authentication authentication) {

        // Véfifier si le user s'est connecté
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Employe) {
            Employe employe = (Employe) principal;
            log.info("Nom Employé={}", employe.getPrenom());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Object credentials = authentication.getCredentials();
        log.info("Credentials={}", credentials);

        // Récupération des roles
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            log.info("Droits={}", grantedAuthority.getAuthority());
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return employeService.findAll();
    }

    @Tag(name = "Gestion employés")
    @GetMapping("/{id}")
    public Employe findById(@PathVariable(name = "id") Integer idEmploye) {
        return employeService.findById(idEmploye);
    }

    @Tag(name = "Gestion employés")
    @Operation(
            summary = "Enregistrer un employe",
            description = "Permet d'enregsitrer un employe, vous ne devrez pas fournir l'ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'enregistrement est effectue avec succes",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            summary = "l'employe enregistré",
                                            value = "{\n" +
                                                    "  \"id\": 3,\n" +
                                                    "  \"nom\": \"DIOP\",\n" +
                                                    "  \"prenom\": \"ADJA\",\n" +
                                                    "  \"salary\": 2000.0\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "251",
                            description = "Le nom est absent"
                    ),
                    @ApiResponse(
                            responseCode = "250",
                            description = "Le prenom est absent"
                    )
            }
    )
    @PutMapping
    public ResponseEntity<?> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "L'employe à enregistrer",
                    content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\n" +
                                    "  \"salary\": 2000,\n" +
                                    "  \"nom\": \"DIOP\",\n" +
                                    "  \"prenom\": \"ADJA\"\n" +
                                    "}"
                    )
            ))
            @RequestBody Employe employe) {

        Employe result = employeService.save(employe);

        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable(name = "id") Integer id,
            @RequestBody Employe employe) {
            return ResponseEntity.status(201).body(employeService.save(employe));

    }

}
