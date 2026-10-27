
package tn.esprit.autoloc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContratServiceImpl implements IContratService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContratServiceImpl.class);
    private static final String CONTRAT_INTROUVABLE = "Contrat introuvable : ";

    private final IContratRepository contratRepository;

    public ContratServiceImpl(IContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    // GET : tous les contrats
    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    // GET : contrat par ID
    @Override
    @Transactional(readOnly = true)
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                CONTRAT_INTROUVABLE + id
                        )
                );
    }

    // POST : créer un contrat avec ses paiements
    @Override
    @Transactional
    public Contrat create(Contrat contrat) {

        if (contrat.getPaiements() != null) {
            for (Paiement paiement : contrat.getPaiements()) {
                paiement.setContrat(contrat);
            }
        }

        return contratRepository.saveAndFlush(contrat);
    }

    // PUT : modifier un contrat
    @Override
    @Transactional
    public Contrat update(Long id, Contrat nouveauContrat) {

        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                CONTRAT_INTROUVABLE + id
                        )
                );

        contrat.setDateSignature(
                nouveauContrat.getDateSignature()
        );

        contrat.setMontantTotal(
                nouveauContrat.getMontantTotal()
        );

        contrat.setValide(
                nouveauContrat.getValide()
        );

        if (nouveauContrat.getReservation() != null) {
            contrat.setReservation(
                    nouveauContrat.getReservation()
            );
        }

        return contratRepository.saveAndFlush(contrat);
    }

    // DELETE : supprimer un contrat et ses paiements
    @Override
    @Transactional
    public void deleteById(Long id) {

        LOGGER.info(">>> DEBUT SUPPRESSION CONTRAT : {}", id);

        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                CONTRAT_INTROUVABLE + id
                        )
                );

        // Retirer la reference inverse pour eviter la cascade PERSIST au flush.
        if (contrat.getReservation() != null) {
            contrat.getReservation().setContrat(null);
        }

        contratRepository.delete(contrat);
        contratRepository.flush();

        LOGGER.info(">>> FIN SUPPRESSION CONTRAT : {}", id);
    }
}
