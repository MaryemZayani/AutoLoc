
package tn.esprit.autoloc.service.impl;

import java.util.List;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    private void valider(Vehicule vehicule) {
        if (vehicule == null) {
            throw new IllegalArgumentException("Véhicule obligatoire");
        }
        if (vehicule.getImmatriculation() == null ||
                vehicule.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException(
                    "L'immatriculation est obligatoire");
        }
        if (vehicule.getMarque() == null ||
                vehicule.getMarque().isBlank()) {
            throw new IllegalArgumentException(
                    "La marque est obligatoire");
        }
        if (vehicule.getModele() == null ||
                vehicule.getModele().isBlank()) {
            throw new IllegalArgumentException(
                    "Le modèle est obligatoire");
        }
        if (vehicule.getCategorie() == null) {
            throw new IllegalArgumentException(
                    "La catégorie est obligatoire");
        }
        if (vehicule.getTarifJournalier() == null ||
                vehicule.getTarifJournalier()
                        .compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier doit être positif ou nul");
        }
        if (vehicule.getStatut() == null) {
            throw new IllegalArgumentException(
                    "Le statut du véhicule est obligatoire");
        }
    }

    @Override
    public Vehicule create(Vehicule vehicule) {
        valider(vehicule);

        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'ID");
        }

        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        valider(vehicule);

        Vehicule existant = findById(id);

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }

        vehiculeRepository.deleteById(id);
    }
}
