
package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;

import tn.esprit.autoloc.repository.IAgenceRepository;

import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.service.IVehiculeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    // ==========================================
    // ANCIEN TRAVAIL : TESTS REPOSITORY
    // ==========================================

    @Test
    void contextLoads() {
        // Vérifie le démarrage de Spring Boot
    }

    @Test
    void testAjouterAgence() {

        Agence agence = new Agence();
        agence.setNom("agence1");
        agence.setAdresse("Tunis");

        Agence agenceEnregistree = agenceRepository.save(agence);

        assertNotNull(agenceEnregistree);
        assertNotNull(agenceEnregistree.getIdAgence());

        System.out.println("Agence ajoutée : " + agenceEnregistree);
    }

    @Test
    void testAfficherAgences() {

        List<Agence> agences = agenceRepository.findAll();

        assertNotNull(agences);

        System.out.println("Liste des agences :");

        for (Agence agence : agences) {
            System.out.println(agence);
        }
    }

    // ==========================================
    // ATELIER 4 - PARTIE 2 : AGENCE SERVICE
    // ==========================================

    @Test
    void testAgenceService() {

        Agence agence = new Agence();
        agence.setNom("Agence Atelier 4");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71000000");

        Agence sauvegardee = agenceService.create(agence);

        assertNotNull(sauvegardee);
        assertNotNull(sauvegardee.getIdAgence());

        Long id = sauvegardee.getIdAgence();

        System.out.println("Agence créée : " + sauvegardee);

        List<Agence> agences = agenceService.findAll();
        assertNotNull(agences);

        System.out.println("Liste des agences : " + agences);

        Agence trouvee = agenceService.findById(id);
        assertNotNull(trouvee);

        System.out.println("Agence trouvée : " + trouvee);

        System.out.println("Nombre d'agences : " + agences.size());

        agenceService.deleteById(id);

        boolean existe = agenceService.findAll()
                .stream()
                .anyMatch(a -> id.equals(a.getIdAgence()));

        assertFalse(existe);

        System.out.println("Agence supprimée !");
    }

    // ==========================================
    // ATELIER 4 - PARTIE 3 : CLIENT SERVICE
    // ==========================================

    @Test
    void testClientService() {

        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed@example.com");
        client1.setTelephone("22000000");
        client1.setNumPermis("PERMIS001");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Sarra");
        client2.setEmail("sarra@example.com");
        client2.setTelephone("23000000");
        client2.setNumPermis("PERMIS002");
        client2.setDateInscription(LocalDate.now());

        Client sauvegarde1 = clientService.create(client1);
        Client sauvegarde2 = clientService.create(client2);

        assertNotNull(sauvegarde1.getIdClient());
        assertNotNull(sauvegarde2.getIdClient());

        System.out.println("Clients créés : "
                + sauvegarde1.getIdClient() + ", "
                + sauvegarde2.getIdClient());

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);

        System.out.println("Liste des clients : " + clients);

        Client trouve = clientService.findById(
                sauvegarde1.getIdClient()
        );

        assertNotNull(trouve);

        System.out.println("Client trouvé : " + trouve);

        System.out.println("Nombre de clients : " + clients.size());

        Long id1 = sauvegarde1.getIdClient();
        Long id2 = sauvegarde2.getIdClient();

        clientService.deleteById(id1);
        clientService.deleteById(id2);

        assertFalse(clientService.findAll()
                .stream()
                .anyMatch(c -> id1.equals(c.getIdClient())
                        || id2.equals(c.getIdClient())));

        System.out.println("Clients supprimés !");
    }

    // ==========================================
    // ATELIER 4 - PARTIE 4 : VEHICULE SERVICE
    // ==========================================

    @Test
    void testVehiculeService() {

        Vehicule vehicule = new Vehicule();

        vehicule.setImmatriculation("123-TUN-456");
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setTarifJournalier(
                new BigDecimal("120.00")
        );

        // Les valeurs de categorie et statut
        // dépendent des constantes de tes enums.
        vehicule.setCategorie(CategorieVehicule.values()[0]);
        vehicule.setStatut(StatutVehicule.values()[0]);

        Vehicule sauvegarde = vehiculeService.create(vehicule);

        assertNotNull(sauvegarde);
        assertNotNull(sauvegarde.getIdVehicule());

        Long id = sauvegarde.getIdVehicule();

        System.out.println("Véhicule créé : " + sauvegarde);

        List<Vehicule> vehicules = vehiculeService.findAll();

        assertNotNull(vehicules);

        System.out.println("Liste des véhicules : " + vehicules);

        Vehicule trouve = vehiculeService.findById(id);

        assertNotNull(trouve);

        System.out.println("Véhicule trouvé : " + trouve);

        System.out.println(
                "Nombre de véhicules : " + vehicules.size()
        );

        vehiculeService.deleteById(id);

        assertFalse(vehiculeService.findAll()
                .stream()
                .anyMatch(v -> id.equals(v.getIdVehicule())));

        System.out.println("Véhicule supprimé !");
    }
}
