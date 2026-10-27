
package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

@Tag(name = "agences", description = "Gestion des agences.")
@RestController
@RequestMapping("/agences")
public class AgenceController {

    private final IAgenceService agenceService;

    public AgenceController(IAgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @Operation(summary = "Lister les agences", description = "Retourne la liste des agences ; une liste vide est retournée si aucune ressource n’existe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping
    public Iterable<Agence> getAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "Consulter une ressource Agence", description = "Retourne la ressource Agence correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @GetMapping("/{id}")
    public Agence getById(@Parameter(description = "Identifiant de la ressource Agence", example = "1", required = true) @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(summary = "Créer une ressource Agence", description = "Crée une ressource Agence à partir du corps JSON fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation(summary = "Modifier une ressource Agence", description = "Met à jour la ressource Agence correspondant à l’identifiant fourni avec les champs pris en charge par le service.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource mise à jour"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @PutMapping("/{id}")
    public Agence update(@Parameter(description = "Identifiant de la ressource Agence", example = "1", required = true) @PathVariable Long id,
                         @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @Operation(summary = "Supprimer une ressource Agence", description = "Supprime la ressource Agence correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ressource supprimée, sans corps de réponse", content = @Content),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant de la ressource Agence", example = "1", required = true) @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
