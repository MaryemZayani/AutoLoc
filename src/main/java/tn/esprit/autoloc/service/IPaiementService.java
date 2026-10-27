
package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;
import java.util.List;

public interface IPaiementService {

    Paiement create(Paiement paiement);

    Paiement findById(Long id);

    List<Paiement> findAll();

    Paiement update(Long id, Paiement paiement);

    void deleteById(Long id);
}
