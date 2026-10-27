
package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@Tag(name = "employés", description = "Gestion des employés.")
@RestController
@RequestMapping("/employes")
public class EmployeController {

    private final IEmployeService employeService;

    public EmployeController(IEmployeService employeService) {
        this.employeService = employeService;
    }

    // GET : Afficher tous les employés
    @Operation(summary = "Lister les employés", description = "Retourne la liste des employés ; une liste vide est retournée si aucune ressource n’existe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping
    public Iterable<Employe> getAll() {
        return employeService.findAll();
    }

    // GET : Rechercher un employé par ID
    @Operation(summary = "Consulter une ressource Employe", description = "Retourne la ressource Employe correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @GetMapping("/{id}")
    public Employe getById(@Parameter(description = "Identifiant de la ressource Employe", example = "1", required = true) @PathVariable Long id) {
        return employeService.findById(id);
    }

    // POST : Ajouter un employé
    @Operation(summary = "Créer une ressource Employe", description = "Crée une ressource Employe à partir du corps JSON fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ressource créée"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    // PUT : Modifier un employé
    @Operation(summary = "Modifier une ressource Employe", description = "Met à jour la ressource Employe correspondant à l’identifiant fourni avec les champs pris en charge par le service.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource mise à jour"),
        @ApiResponse(responseCode = "400", description = "Requête incorrecte : identifiant non numérique ou corps JSON illisible", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @PutMapping("/{id}")
    public Employe update(@Parameter(description = "Identifiant de la ressource Employe", example = "1", required = true) @PathVariable Long id,
                          @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    // DELETE : Supprimer un employé
    @Operation(summary = "Supprimer une ressource Employe", description = "Supprime la ressource Employe correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ressource supprimée, sans corps de réponse", content = @Content),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant de la ressource Employe", example = "1", required = true) @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
