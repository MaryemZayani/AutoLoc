
package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

@Tag(name = "maintenances", description = "Gestion des maintenances.")
@RestController
@RequestMapping("/maintenances")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    public MaintenanceController(IMaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @Operation(summary = "Lister les maintenances", description = "Retourne la liste des maintenances ; une liste vide est retournée si aucune ressource n’existe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping
    public Iterable<Maintenance> getAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une ressource Maintenance", description = "Retourne la ressource Maintenance correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @GetMapping("/{id}")
    public Maintenance getById(@Parameter(description = "Identifiant de la ressource Maintenance", example = "1", required = true) @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Créer une ressource Maintenance", description = "Crée une ressource Maintenance à partir du corps JSON fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @Operation(summary = "Modifier une ressource Maintenance", description = "Met à jour la ressource Maintenance correspondant à l’identifiant fourni avec les champs pris en charge par le service.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource mise à jour"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @PutMapping("/{id}")
    public Maintenance update(@Parameter(description = "Identifiant de la ressource Maintenance", example = "1", required = true) @PathVariable Long id,
                              @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @Operation(summary = "Supprimer une ressource Maintenance", description = "Supprime la ressource Maintenance correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ressource supprimée, sans corps de réponse", content = @Content),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant de la ressource Maintenance", example = "1", required = true) @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
