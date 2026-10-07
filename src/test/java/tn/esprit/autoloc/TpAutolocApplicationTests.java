package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Vehicule;

import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    // =========================================================
    // INJECTION DES REPOSITORIES
    // =========================================================

    @Autowired
    private IAgenceRepository agenceRepo;

    @Autowired
    private IClientRepository clientRepo;

    @Autowired
    private IVehiculeRepository vehiculeRepo;


    // =========================================================
    // TEST 1 : AGENCE
    // =========================================================

    @Test
    void testIAgenceRepository() {

        System.out.println("\n========== TEST AGENCE ==========");

        // 1. Création d'une agence
        Agence agence = new Agence();

        agence.setNom("Agence Tunis Centre");
        agence.setAdresse("Tunis");

        System.out.println(
                "Agence avant enregistrement : " + agence
        );


        // 2. SAVE()
        // Enregistrer l'agence dans la base
        Agence agenceSaved =
                agenceRepo.save(agence);

        System.out.println(
                "Agence enregistrée : " + agenceSaved
        );

        assertNotNull(agenceSaved);


        // 3. FINDALL()
        // Récupérer toutes les agences
        List<Agence> agences =
                agenceRepo.findAll();

        System.out.println(
                "Liste des agences : " + agences
        );

        System.out.println(
                "Nombre d'agences dans la liste : "
                        + agences.size()
        );

        assertNotNull(agences);


        // 4. FINDBYID()
        // Rechercher l'agence créée avec son ID
        Agence agenceTrouvee =
                agenceRepo
                        .findById(
                                agenceSaved.getIdAgence()
                        )
                        .orElse(null);

        System.out.println(
                "Agence trouvée par ID : "
                        + agenceTrouvee
        );

        assertNotNull(agenceTrouvee);


        // 5. EXISTSBYID()
        // Vérifier que l'agence existe
        boolean existe =
                agenceRepo.existsById(
                        agenceSaved.getIdAgence()
                );

        System.out.println(
                "L'agence existe ? " + existe
        );

        assertTrue(existe);


        // 6. COUNT()
        // Compter le nombre total d'agences
        long nombreAgences =
                agenceRepo.count();

        System.out.println(
                "Nombre total d'agences : "
                        + nombreAgences
        );


        // PAS DE DELETE
        // L'agence reste dans la base de données

        System.out.println(
                "L'agence reste enregistrée dans la base."
        );

        System.out.println(
                "========== FIN TEST AGENCE ==========\n"
        );
    }


    // =========================================================
    // TEST 2 : CLIENT
    // =========================================================

    @Test
    void testIClientRepository() {

        System.out.println("\n========== TEST CLIENT ==========");


        // 1. Création du premier client
        Client client1 = new Client();

        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed@gmail.com");
        client1.setTelephone("20111222");


        // 2. Création du deuxième client
        Client client2 = new Client();

        client2.setNom("Trabelsi");
        client2.setPrenom("Sarra");
        client2.setEmail("sarra@gmail.com");
        client2.setTelephone("95123456");


        // 3. SAVE() du premier client
        Client clientSaved1 =
                clientRepo.save(client1);


        // 4. SAVE() du deuxième client
        Client clientSaved2 =
                clientRepo.save(client2);


        System.out.println(
                "Client 1 enregistré : "
                        + clientSaved1
        );

        System.out.println(
                "Client 2 enregistré : "
                        + clientSaved2
        );

        assertNotNull(clientSaved1);
        assertNotNull(clientSaved2);


        // 5. FINDALL()
        // Récupérer tous les clients
        List<Client> clients =
                clientRepo.findAll();

        System.out.println(
                "Liste des clients : "
                        + clients
        );

        System.out.println(
                "Nombre de clients dans la liste : "
                        + clients.size()
        );

        assertNotNull(clients);


        // 6. FINDBYID()
        // Rechercher le premier client
        Client clientTrouve =
                clientRepo
                        .findById(
                                clientSaved1.getIdClient()
                        )
                        .orElse(null);

        System.out.println(
                "Client trouvé par ID : "
                        + clientTrouve
        );

        assertNotNull(clientTrouve);


        // 7. EXISTSBYID()
        // Tester avec un ID existant
        boolean clientExiste =
                clientRepo.existsById(
                        clientSaved1.getIdClient()
                );

        System.out.println(
                "Le client avec l'ID "
                        + clientSaved1.getIdClient()
                        + " existe ? "
                        + clientExiste
        );

        assertTrue(clientExiste);


        // 8. EXISTSBYID()
        // Tester avec un ID inexistant
        boolean clientInexistant =
                clientRepo.existsById(
                        999999L
                );

        System.out.println(
                "Le client avec l'ID 999999 existe ? "
                        + clientInexistant
        );

        assertFalse(clientInexistant);


        // 9. COUNT()
        // Compter tous les clients
        long nombreClients =
                clientRepo.count();

        System.out.println(
                "Nombre total de clients : "
                        + nombreClients
        );


        // PAS DE DELETE
        // Les deux clients restent dans la base

        System.out.println(
                "Les deux clients restent enregistrés dans la base."
        );

        System.out.println(
                "========== FIN TEST CLIENT ==========\n"
        );
    }


    // =========================================================
    // TEST 3 : VEHICULE
    // =========================================================

    @Test
    void testIVehiculeRepository() {

        System.out.println(
                "\n========== TEST VEHICULE =========="
        );


        // 1. Création d'un véhicule
        Vehicule vehicule =
                new Vehicule();

        vehicule.setImmatriculation(
                "123 TUN 4567"
        );

        vehicule.setMarque(
                "Peugeot"
        );

        vehicule.setModele(
                "208"
        );


        // 2. SAVE()
        // Enregistrer le véhicule
        Vehicule vehiculeSaved =
                vehiculeRepo.save(vehicule);

        System.out.println(
                "Véhicule enregistré : "
                        + vehiculeSaved
        );

        assertNotNull(vehiculeSaved);


        // 3. FINDALL()
        // Récupérer tous les véhicules
        List<Vehicule> vehicules =
                vehiculeRepo.findAll();

        System.out.println(
                "Liste des véhicules : "
                        + vehicules
        );

        System.out.println(
                "Nombre de véhicules dans la liste : "
                        + vehicules.size()
        );

        assertNotNull(vehicules);


        // 4. COUNT()
        // Compter les véhicules
        long nombreVehicules =
                vehiculeRepo.count();

        System.out.println(
                "Nombre total de véhicules : "
                        + nombreVehicules
        );


        // 5. MODIFIER UNE PROPRIÉTÉ
        // Le modèle était "208"

        System.out.println(
                "Ancien modèle : "
                        + vehiculeSaved.getModele()
        );


        // On change 208 en 308
        vehiculeSaved.setModele(
                "308"
        );


        System.out.println(
                "Nouveau modèle : "
                        + vehiculeSaved.getModele()
        );


        // 6. SAVE()
        // Comme le véhicule possède déjà un ID,
        // save() enregistre la modification
        Vehicule vehiculeModifie =
                vehiculeRepo.save(
                        vehiculeSaved
                );

        System.out.println(
                "Véhicule après modification : "
                        + vehiculeModifie
        );


        // 7. FINDBYID()
        // Relire le véhicule depuis la base
        Vehicule vehiculeTrouve =
                vehiculeRepo
                        .findById(
                                vehiculeSaved.getIdVehicule()
                        )
                        .orElse(null);

        System.out.println(
                "Véhicule trouvé dans la base : "
                        + vehiculeTrouve
        );

        assertNotNull(vehiculeTrouve);


        // 8. EXISTSBYID()
        boolean vehiculeExiste =
                vehiculeRepo.existsById(
                        vehiculeSaved.getIdVehicule()
                );

        System.out.println(
                "Le véhicule existe ? "
                        + vehiculeExiste
        );

        assertTrue(vehiculeExiste);


        // PAS DE DELETE
        // Le véhicule reste dans la base

        System.out.println(
                "Le véhicule reste enregistré dans la base."
        );


        System.out.println(
                "========== FIN TEST VEHICULE ==========\n"
        );
    }
}