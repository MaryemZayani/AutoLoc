
package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;

    private void valider(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException(
                    "La réservation est obligatoire");
        }

        if (reservation.getDateDebut() == null ||
                reservation.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de réservation sont obligatoires");
        }

        if (reservation.getDateFin()
                .isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }

        if (reservation.getStatut() == null) {
            throw new IllegalArgumentException(
                    "Le statut de réservation est obligatoire");
        }
    }

    @Override
    public Reservation create(Reservation reservation) {
        valider(reservation);

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'ID");
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        valider(reservation);

        Reservation existante = findById(id);

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }

        reservationRepository.deleteById(id);
    }
}
