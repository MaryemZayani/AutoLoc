
package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@Tag(name = "véhicules", description = "Gestion des véhicules.")
@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    public VehiculeController(IVehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @Operation(summary = "Lister les véhicules", description = "Retourne la liste des véhicules ; une liste vide est retournée si aucune ressource n’existe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping
    public Iterable<Vehicule> getAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Consulter une ressource Vehicule", description = "Retourne la ressource Vehicule correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @GetMapping("/{id}")
    public Vehicule getById(@Parameter(description = "Identifiant de la ressource Vehicule", example = "1", required = true) @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Créer une ressource Vehicule", description = "Crée une ressource Vehicule à partir du corps JSON fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @Operation(summary = "Modifier une ressource Vehicule", description = "Met à jour la ressource Vehicule correspondant à l’identifiant fourni avec les champs pris en charge par le service.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource mise à jour"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @PutMapping("/{id}")
    public Vehicule update(@Parameter(description = "Identifiant de la ressource Vehicule", example = "1", required = true) @PathVariable Long id,
                           @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer une ressource Vehicule", description = "Supprime la ressource Vehicule correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ressource supprimée, sans corps de réponse", content = @Content),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant de la ressource Vehicule", example = "1", required = true) @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
