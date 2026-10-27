package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

@Tag(name = "paiements", description = "Consultation des paiements uniquement. POST, PUT et DELETE ne sont pas exposés (405 Method Not Allowed).")
@RestController
@RequestMapping("/paiements")
public class PaiementController {

    private final IPaiementService paiementService;

    public PaiementController(IPaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @Operation(summary = "Lister les paiements", description = "Retourne la liste des paiements ; une liste vide est retournée si aucune ressource n’existe.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    })
    @GetMapping
    public Iterable<Paiement> getAll() {
        return paiementService.findAll();
    }

    @Operation(summary = "Consulter une ressource Paiement", description = "Retourne la ressource Paiement correspondant à l’identifiant fourni.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ressource trouvée"),
        @ApiResponse(responseCode = "400", description = "Identifiant non numérique", content = @Content),
        @ApiResponse(responseCode = "404", description = "Ressource introuvable (réponse attendue ; traduction HTTP des exceptions non configurée actuellement)", content = @Content)
    })
    @GetMapping("/{id}")
    public Paiement getById(@Parameter(description = "Identifiant de la ressource Paiement", example = "1", required = true) @PathVariable Long id) {
        return paiementService.findById(id);
    }
}