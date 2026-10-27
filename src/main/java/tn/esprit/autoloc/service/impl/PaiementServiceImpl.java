
package tn.esprit.autoloc.service.impl;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@Service
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    public PaiementServiceImpl(
            IPaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    // CREATE : Ajouter un paiement
    @Override
    public Paiement create(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    // READ : Afficher tous les paiements
    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    // READ : Rechercher un paiement par ID
    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Paiement introuvable avec ID : " + id
                        ));
    }

    // UPDATE : Modifier un paiement
    @Override
    public Paiement update(Long id, Paiement paiement) {

        Paiement existing = findById(id);

        paiement.setIdPaiement(existing.getIdPaiement());

        return paiementRepository.save(paiement);
    }

    // DELETE : Supprimer un paiement
    @Override
    public void deleteById(Long id) {

        Paiement existing = findById(id);

        paiementRepository.delete(existing);
    }
}
