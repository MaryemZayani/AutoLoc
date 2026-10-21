
package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    private void valider(Maintenance maintenance) {
        if (maintenance == null) {
            throw new IllegalArgumentException(
                    "La maintenance est obligatoire");
        }

        if (maintenance.getDateDebut() == null ||
                maintenance.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de maintenance sont obligatoires");
        }

        if (maintenance.getDateFin()
                .isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }

        if (maintenance.getDescription() == null ||
                maintenance.getDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "La description de maintenance est obligatoire");
        }
    }

    @Override
    public Maintenance create(Maintenance maintenance) {
        valider(maintenance);

        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle maintenance ne doit pas avoir d'ID");
        }

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        valider(maintenance);

        Maintenance existante = findById(id);

        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());

        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }

        maintenanceRepository.deleteById(id);
    }
}
