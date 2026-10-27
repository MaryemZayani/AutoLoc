
package tn.esprit.autoloc.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@Tag(name = "contrats", description = "Gestion des contrats.")
@RestController
@RequestMapping("/contrats")
public class ContratController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContratController.class);

    private final IContratService contratService;

    public ContratController(IContratService contratService) {
        this.contratService = contratService;
    }

    // GET : tous les contrats
    @Operation(summary = "Lister les contrats", description = "Retourne la liste des contrats ; une liste vide est retournée si aucune ressource n’existe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping
    public List<Contrat> getAll() {
        return contratService.findAll();
    }

    // GET : contrat par ID
    @Operation(summary = "Consulter une ressource Contrat", description = "Retourne la ressource Contrat correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @GetMapping("/{id}")
    public Contrat getById(@Parameter(description = "Identifiant de la ressource Contrat", example = "1", required = true) @PathVariable Long id) {
        return contratService.findById(id);
    }

    // POST : créer un contrat
    @Operation(summary = "Créer une ressource Contrat", description = "Crée une ressource Contrat à partir du corps JSON fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    // PUT : modifier un contrat
    @Operation(summary = "Modifier une ressource Contrat", description = "Met à jour la ressource Contrat correspondant à l’identifiant fourni avec les champs pris en charge par le service.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource mise à jour"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @PutMapping("/{id}")
    public Contrat update(
            @Parameter(description = "Identifiant de la ressource Contrat", example = "1", required = true) @PathVariable Long id,
            @RequestBody Contrat contrat) {

        return contratService.update(id, contrat);
    }

    // DELETE : supprimer un contrat
    @Operation(summary = "Supprimer une ressource Contrat", description = "Supprime le contrat et ses paiements associés ; la réservation est conservée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ressource supprimée, sans corps de réponse", content = @Content),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant de la ressource Contrat", example = "1", required = true) @PathVariable Long id) {

        LOGGER.info(">>> DELETE CONTROLLER : {}", id);

        contratService.deleteById(id);
    }
}
