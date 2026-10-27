# AutoLoc — Documentation de l’Atelier 5

Date de rédaction : 27 octobre 2026. Périmètre : Spring MVC, SpringDoc, échanges JSON et qualité du code.

Ce document repose sur les neuf contrôleurs, les neuf interfaces et implémentations Service, les neuf entités et leurs énumérations, `OpenApiConfig.java`, `application.properties` et `pom.xml`. Les constats issus de la lecture du code sont distingués des vérifications exécutées pendant cette session. Aucun fichier Java ni aucune donnée MySQL n’a été modifié pour rédiger ce rapport.

## A. Tableau des endpoints REST

### Configuration et organisation

L’application utilise Java 17, Spring Boot 4.1.1, Spring MVC, Spring Data JPA, MySQL et SpringDoc 3.1.1. L’adresse de base locale est `http://localhost:8080/api`. Le préfixe `/api` provient de `server.servlet.context-path=/api` ; les contrôleurs déclarent uniquement `/agences`, `/clients`, etc.

Chaque contrôleur délègue à une interface Service injectée par constructeur. L’implémentation correspondante est nommée `<Ressource>ServiceImpl`, annotée `@Service`, et utilise un repository JPA. Les noms de services du tableau désignent les interfaces appelées par les contrôleurs.

### Lecture des codes

- **200** : réponse GET ou PUT réussie ; un GET de collection vide doit retourner une liste vide, pas 404.
- **201** : création réussie, configurée par `@ResponseStatus(HttpStatus.CREATED)`.
- **204** : suppression réussie, sans corps, configurée par `@ResponseStatus(HttpStatus.NO_CONTENT)`.
- **400** : erreur de conversion MVC, par exemple un identifiant non numérique, un corps JSON illisible ou absent pour POST/PUT. La validation métier n’est pas automatiquement traduite en 400.
- **404 attendu** : ressource absente selon la documentation Swagger. Ce code n’est pas actuellement garanti : il manque la traduction HTTP des exceptions métier.
- **500 possible** : exception métier non gérée, erreur de persistance ou de sérialisation ; comportement déduit du code, pas résultat d’un test HTTP de chaque endpoint.

Les codes de succès ci-dessous décrivent le chemin nominal, et non une preuve d’exécution de chaque opération. Les erreurs 400/404 sont documentées avec `@ApiResponse` pour les opérations concernées ; les annotations Swagger décrivent le contrat sans imposer le comportement HTTP.

| Contrôleur | Méthode | URL | Service appelé | Succès nominal | Erreurs / limites actuelles |
|---|---|---|---|---|---|
| AgenceController | GET | `/api/agences` | `IAgenceService.findAll()` | 200 | 500 possible |
| AgenceController | GET | `/api/agences/{id}` | `IAgenceService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| AgenceController | POST | `/api/agences` | `IAgenceService.create(corps)` | 201 | 400 ; 500 possible |
| AgenceController | PUT | `/api/agences/{id}` | `IAgenceService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| AgenceController | DELETE | `/api/agences/{id}` | `IAgenceService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| ClientController | GET | `/api/clients` | `IClientService.findAll()` | 200 | 500 possible |
| ClientController | GET | `/api/clients/{id}` | `IClientService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| ClientController | POST | `/api/clients` | `IClientService.create(corps)` | 201 | 400 ; 500 possible |
| ClientController | PUT | `/api/clients/{id}` | `IClientService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| ClientController | DELETE | `/api/clients/{id}` | `IClientService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| ContratController | GET | `/api/contrats` | `IContratService.findAll()` | 200 | 500 possible |
| ContratController | GET | `/api/contrats/{id}` | `IContratService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| ContratController | POST | `/api/contrats` | `IContratService.create(corps)` | 201 | 400 ; 500 possible |
| ContratController | PUT | `/api/contrats/{id}` | `IContratService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| ContratController | DELETE | `/api/contrats/{id}` | `IContratService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| EmployeController | GET | `/api/employes` | `IEmployeService.findAll()` | 200 | 500 possible |
| EmployeController | GET | `/api/employes/{id}` | `IEmployeService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| EmployeController | POST | `/api/employes` | `IEmployeService.create(corps)` | 201 | 400 ; 500 possible |
| EmployeController | PUT | `/api/employes/{id}` | `IEmployeService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| EmployeController | DELETE | `/api/employes/{id}` | `IEmployeService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| EquipementController | GET | `/api/equipements` | `IEquipementService.findAll()` | 200 | 500 possible |
| EquipementController | GET | `/api/equipements/{id}` | `IEquipementService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| EquipementController | POST | `/api/equipements` | `IEquipementService.create(corps)` | 201 | 400 ; 500 possible |
| EquipementController | PUT | `/api/equipements/{id}` | `IEquipementService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| EquipementController | DELETE | `/api/equipements/{id}` | `IEquipementService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| MaintenanceController | GET | `/api/maintenances` | `IMaintenanceService.findAll()` | 200 | 500 possible |
| MaintenanceController | GET | `/api/maintenances/{id}` | `IMaintenanceService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| MaintenanceController | POST | `/api/maintenances` | `IMaintenanceService.create(corps)` | 201 | 400 ; 500 possible |
| MaintenanceController | PUT | `/api/maintenances/{id}` | `IMaintenanceService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| MaintenanceController | DELETE | `/api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| PaiementController | GET | `/api/paiements` | `IPaiementService.findAll()` | 200 | 500 possible |
| PaiementController | GET | `/api/paiements/{id}` | `IPaiementService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| ReservationController | GET | `/api/reservations` | `IReservationService.findAll()` | 200 | 500 possible |
| ReservationController | GET | `/api/reservations/{id}` | `IReservationService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| ReservationController | POST | `/api/reservations` | `IReservationService.create(corps)` | 201 | 400 ; 500 possible |
| ReservationController | PUT | `/api/reservations/{id}` | `IReservationService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| ReservationController | DELETE | `/api/reservations/{id}` | `IReservationService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |
| VehiculeController | GET | `/api/vehicules` | `IVehiculeService.findAll()` | 200 | 500 possible |
| VehiculeController | GET | `/api/vehicules/{id}` | `IVehiculeService.findById(id)` | 200 | 400 ; 404 attendu, 500 possible |
| VehiculeController | POST | `/api/vehicules` | `IVehiculeService.create(corps)` | 201 | 400 ; 500 possible |
| VehiculeController | PUT | `/api/vehicules/{id}` | `IVehiculeService.update(id, corps)` | 200 | 400 ; 404 attendu, 500 possible |
| VehiculeController | DELETE | `/api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | 204 | 400 ; 404 attendu, 500 possible |

**Total : 42 endpoints REST métier, répartis dans neuf contrôleurs.** Les ressources SpringDoc présentées en section C sont des routes techniques supplémentaires, pas des méthodes de ces contrôleurs.

### Comportements et limites observés dans les services

- `ClientServiceImpl`, `EmployeServiceImpl`, `EquipementServiceImpl`, `MaintenanceServiceImpl`, `ReservationServiceImpl` et `VehiculeServiceImpl` effectuent des validations explicites et refusent un ID non nul lors de la création. Leurs `IllegalArgumentException` ne sont pas traduites en 400 par un gestionnaire applicatif.
- Les recherches et suppressions de ces six ressources et des agences utilisent `ResourceNotFoundException`. Cette classe étend `RuntimeException` sans `@ResponseStatus`. Aucun `@ControllerAdvice` ou `@ExceptionHandler` applicatif n’a été trouvé.
- Les recherches et mises à jour des contrats absents lancent `NoSuchElementException` ; leur suppression lance `IllegalArgumentException`. La consultation d’un paiement absent lance une simple `RuntimeException`. Il ne faut donc pas annoncer un 404 effectivement obtenu sans mesure ou correction de la couche HTTP.
- `AgenceServiceImpl.update()` charge et sauvegarde l’agence, mais ne recopie actuellement aucun champ du corps reçu. Un succès HTTP nominal ne prouve pas une mise à jour effective des attributs.
- Les PUT recopient les champs prévus par chaque service ; ils ne remplacent pas systématiquement toutes les associations JPA. `ContratServiceImpl.update()` modifie la date de signature, le montant total, la validité et éventuellement la réservation, pas la liste des paiements.
- La création d’un contrat rattache ses paiements au contrat. Sa suppression retire la référence inverse `reservation.contrat` avant `delete()` et `flush()`. Le code prévoit la suppression en cascade des paiements et la conservation de la réservation ; l’effet réel en base reste à vérifier après correction.
- `IPaiementService` et `PaiementServiceImpl` possèdent aussi `create`, `update` et `deleteById`, mais ces opérations ne sont pas exposées par `PaiementController`.
- Les suppressions peuvent propager des cascades JPA selon les relations existantes. Aucun test destructif n’a été exécuté pour ce rapport.

## B. Annotations Jackson

### Inventaire des annotations réellement présentes

| Annotation | Emplacement exact | Rôle dans le JSON |
|---|---|---|
| `@JsonManagedReference("contrat-paiements")` | `Contrat.paiements` | Sérialise le côté parent et les paiements imbriqués. |
| `@JsonBackReference("contrat-paiements")` | `Paiement.contrat` | Omet la référence de retour vers le contrat dans le JSON ; participe à la reconstruction du lien lors de la désérialisation du graphe parent-enfants. |
| `@JsonIgnore` | `Agence.employes` | Exclut la collection de la représentation JSON. |
| `@JsonIgnore` | `Agence.vehicules` | Exclut la collection de la représentation JSON. |
| `@JsonIgnore` | `Client.reservations` | Exclut les réservations du client du JSON. |
| `@JsonIgnore` | `Vehicule.reservations` | Exclut les réservations du véhicule du JSON. |
| `@JsonIgnore` | `Vehicule.maintenances` | Exclut les maintenances du véhicule du JSON. |

### Justification des choix

La paire nommée `contrat-paiements` évite le parcours récursif `Contrat → paiements → contrat → …`. La référence gérée conserve les paiements dans le JSON du contrat ; la référence arrière retire le retour vers le parent. Les deux annotations portent le même nom pour identifier leur association. Lors d’une consultation directe d’un paiement, le champ `contrat` est également omis.

`@JsonIgnore` est utilisé pour ne pas développer certaines collections, alléger la réponse et couper notamment les cycles `Agence ↔ Employe`, `Agence ↔ Vehicule`, `Client ↔ Reservation`, `Vehicule ↔ Reservation` et `Vehicule ↔ Maintenance`. En l’absence de configuration contraire, la propriété est aussi ignorée en entrée : envoyer ces collections dans un corps JSON ne constitue pas un moyen fiable de les modifier.

Ces annotations contrôlent la représentation JSON ; elles ne suppriment ni les relations JPA ni les données MySQL. Elles ne remplacent pas les cascades, les transactions ou les règles métier. `@Getter`, `@Setter`, `@Entity` et `@OneToMany` ne sont pas des annotations Jackson.

### Limites à vérifier

Les relations `Contrat.reservation ↔ Reservation.contrat` et `Vehicule.equipements ↔ Equipement.vehicules` ne possèdent actuellement aucune protection Jackson dédiée. Un graphe chargé et renseigné dans les deux sens peut encore provoquer une récursion de sérialisation. Aucune correction n’est apportée dans ce rapport, et aucun succès global de sérialisation de toutes les entités n’est affirmé.

## C. Postman et Swagger

### Différences et complémentarité

**Postman** est un client HTTP indépendant. Il permet de construire des requêtes, gérer des environnements, enregistrer des collections et écrire des assertions. Il peut tester une API sans document OpenAPI.

**SpringDoc** génère la description OpenAPI depuis l’application et ses annotations. **Swagger UI** affiche cette description et propose « Try it out » pour envoyer des requêtes. Une opération affichée dans Swagger ne prouve pas que sa logique métier fonctionne.

`OpenApiConfig.java` contient le bean `OpenAPI` avec le titre `AutoLoc API`, une description et la version documentaire `1.0.0`. Le fichier existant est conservé. Les contrôleurs utilisent `@Tag`, `@Operation`, `@ApiResponses`, `@ApiResponse` et `@Parameter` pour les identifiants. Le préfixe `/api` n’est pas répété dans `@RequestMapping`.

Adresses locales lorsque l’application fonctionne :

- Swagger UI : <http://localhost:8080/api/swagger-ui/index.html>.
- Document OpenAPI JSON : <http://localhost:8080/api/v3/api-docs>.
- Configuration de Swagger UI : <http://localhost:8080/api/v3/api-docs/swagger-config>.

### Exemple concret : consultation et restriction des paiements

Dans Postman, créer une requête `GET http://localhost:8080/api/paiements`, puis envoyer la requête. Dans Swagger UI, ouvrir le groupe `paiements`, sélectionner `GET /paiements`, puis « Try it out » et « Execute ». Les deux outils sollicitent la même méthode `PaiementController.getAll()`, qui appelle `IPaiementService.findAll()`. Le succès nominal est 200 avec une liste JSON ; cet exemple de consultation n’est pas présenté comme un test exécuté pendant la session.

Un cas effectivement vérifié par une requête HTTP locale est :

```http
POST /api/paiements HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{}
```

Résultat observé : **405 Method Not Allowed**. Aucun endpoint POST n’est déclaré sur cette ressource et aucun paiement n’est créé par cette requête. Pour reproduire ce contrôle dans Postman, utiliser cette méthode, cette URL et ce corps. Swagger UI n’affiche pas d’opération POST pour les paiements.

### Vérifications réellement effectuées pendant la session

| Vérification | Résultat observé | Portée de la preuve |
|---|---|---|
| Démarrage Spring Boot | Application démarrée, port 8080, contexte `/api`, connexion MySQL établie | Ne valide pas toutes les opérations métier. |
| Compilation après ajout des annotations Swagger | `mvnw.cmd -q -DskipTests compile`, code de sortie 0 | Compilation uniquement, pas exécution des tests. |
| GET `/api/v3/api-docs` | 200 ; titre `AutoLoc API`, 42 opérations et 9 tags | Contrôle du document généré, pas test des 42 opérations. |
| Contrôle du document OpenAPI | Résumés, descriptions, tags, codes de succès et paramètres ID présents ; 400/404 présents pour les routes avec ID | Vérification des métadonnées. |
| GET `/api/swagger-ui/index.html` | 200 | Page HTML accessible ; aucune validation visuelle complète du navigateur. |
| GET `/api/v3/api-docs/swagger-config` | URL de définition `/api/v3/api-docs` | Liaison de Swagger UI à la définition. |
| Serveur déclaré dans OpenAPI | `http://localhost:8080/api`, aucun chemin métier avec double `/api` | Vérification du préfixe. |
| POST `/api/paiements` avec `{}` | 405 | Restriction HTTP confirmée par une requête locale ; pas un test effectué depuis Postman. |
| Compilation après correction SLF4J | `mvnw.cmd -q -DskipTests compile`, code de sortie 0 | Le contrôleur corrigé compile. |

Ces résultats proviennent des sorties de commandes de la session de travail. Aucun export Postman, aucune capture Swagger et aucun rapport de tests exhaustif ne sont fournis dans le dépôt. Aucun nouveau test HTTP ni aucune nouvelle compilation n’a été lancé pour la seule rédaction de ce document.

### Tests restant à vérifier

- Exécuter les parcours CRUD sur des données de test, pour les huit ressources qui les exposent, et contrôler le contenu des réponses.
- Vérifier les GET des paiements, y compris un identifiant absent et une liste vide.
- Mesurer les réponses aux identifiants non numériques, JSON invalides et données métier invalides ; ne pas assimiler les 400/404 documentés à des réponses déjà observées.
- Refaire la suppression d’un contrat après correction, vérifier les DELETE SQL et l’état des contrats, paiements et réservations dans une nouvelle transaction. Le 204 initial rapporté par l’utilisateur ne prouvait pas la suppression en base.
- Tester les graphes JSON contenant les associations bidirectionnelles encore non protégées.
- Vérifier manuellement « Try it out » dans le navigateur et conserver des preuves Postman/Swagger pour le rapport final.
- Les tests Java présents dans `TpAutolocApplicationTests.java` ne constituent pas une preuve d’exécution récente. Certains créent des données ; ils n’ont pas été lancés dans cette session.

## D. Analyse SonarQube

### Origine et portée des preuves

Les anomalies ci-dessous ont été réellement détectées par **SonarQube for IDE**, selon les signalements communiqués par l’utilisateur. Les corrections sont vérifiées dans le code et les compilations correspondantes ont réussi pendant la session. Aucun résultat de nouvelle analyse n’a encore été communiqué : la disparition des alertes n’est donc pas affirmée. La lecture du code et une compilation ne remplacent pas une analyse SonarQube.

### Anomalie initiale — `java:S106` dans ContratController

- **Fichier :** `src/main/java/tn/esprit/autoloc/web/controller/ContratController.java`.
- **Ligne signalée initialement :** 89.
- **Règle exacte communiquée :** `java:S106`.
- **Message :** `Replace this use of System.out by a logger.`
- **Problème :** journalisation par `System.out.println` plutôt que par le système de logs applicatif.
- **Correction réalisée :** ajout de `Logger` et `LoggerFactory`, déclaration du logger statique et remplacement par `LOGGER.info(">>> DELETE CONTROLLER : {}", id)`.
- **Vérification technique :** compilation Maven réussie après correction ; aucun `System.out` restant dans ce contrôleur lors du contrôle.
- **Nouvelle analyse SonarQube :** résultat à communiquer par l’utilisateur ; résolution non confirmée par réanalyse.

### Anomalie de duplication dans ContratServiceImpl

| Élément | Constat documenté |
|---|---|
| Fichier | `src/main/java/tn/esprit/autoloc/service/impl/ContratServiceImpl.java`. |
| Détection réelle | Signalement SonarQube for IDE communiqué par l’utilisateur. |
| Message exact communiqué | `Define a constant instead of duplicating this literal "Contrat introuvable : " 3 times.` |
| Identifiant exact de la règle | **À communiquer depuis l’extension.** Le message est connu, mais aucun identifiant n’a été fourni ; aucun identifiant n’est supposé ici. |
| Problème | Le même préfixe de message est répété dans `findById`, `update` et `deleteById`, ce qui rend une modification future moins fiable. |
| Correction réalisée | Déclaration de `private static final String CONTRAT_INTROUVABLE = "Contrat introuvable : ";`, puis remplacement des trois expressions par `CONTRAT_INTROUVABLE + id`. |
| Vérification dans le code actuel | Constante ligne 21 ; utilisations lignes 43, 70 et 105 lors de la mise à jour du rapport. |
| Compilation | `mvnw.cmd -q -DskipTests compile` : code de sortie 0 après les corrections du service. Les tests ne sont pas exécutés par cette commande. |
| Résultat de la nouvelle analyse | **À communiquer par l’utilisateur.** Aucune disparition d’alerte n’est attestée à ce stade. |

Avant, dans trois constructions d’exceptions :

```java
"Contrat introuvable : " + id
```

Après :

```java
private static final String CONTRAT_INTROUVABLE = "Contrat introuvable : ";

// Dans les trois constructions d’exceptions :
CONTRAT_INTROUVABLE + id
```

Les types d’exceptions et les messages produits sont conservés.

### Deux occurrences de `java:S106` dans ContratServiceImpl

| Élément | Première occurrence | Deuxième occurrence |
|---|---|---|
| Ligne du signalement initial | 95 | 112 |
| Règle exacte communiquée | `java:S106` | `java:S106` |
| Message exact communiqué | `Replace this use of System.out by a logger.` | `Replace this use of System.out by a logger.` |
| Emplacement fonctionnel | Début de `deleteById` | Fin de `deleteById` |
| Problème | Écriture directe dans la sortie standard. | Écriture directe dans la sortie standard. |
| Correction réalisée | `LOGGER.info(">>> DEBUT SUPPRESSION CONTRAT : {}", id);` | `LOGGER.info(">>> FIN SUPPRESSION CONTRAT : {}", id);` |
| Ligne actuelle lors de la rédaction | 100 | 117 |
| Résultat de la nouvelle analyse | À communiquer ; résolution non confirmée par réanalyse. | À communiquer ; résolution non confirmée par réanalyse. |

Le service importe désormais `org.slf4j.Logger` et `org.slf4j.LoggerFactory` et déclare :

```java
private static final Logger LOGGER =
        LoggerFactory.getLogger(ContratServiceImpl.class);
```

Les deux appels `System.out.println` ont été remplacés par les appels SLF4J paramétrés ci-dessus. Le système de logs gère ainsi le niveau, le format et la destination des messages. Aucun `System.out` ne subsiste dans ce service lors de la vérification du code. La logique métier, les signatures, les transactions, les opérations de persistance et les endpoints REST n’ont pas été modifiés par ces corrections.

### Résultats de réanalyse à compléter

| Information attendue de l’utilisateur | État actuel |
|---|---|
| Date et périmètre de la nouvelle analyse | Non communiqués. |
| Identifiant exact de la règle du littéral dupliqué | Non communiqué. |
| Résultat pour la duplication dans ContratServiceImpl | Non communiqué. |
| Résultat pour `java:S106`, ancienne ligne 95 | Non communiqué. |
| Résultat pour `java:S106`, ancienne ligne 112 | Non communiqué. |
| Résultat pour l’occurrence initiale de ContratController | Non communiqué. |
| Capture ou export permettant d’attester les résultats | Non fourni. |

Les lignes 95 et 112 désignent les positions avant correction ; les imports et les champs ajoutés ont décalé les positions actuelles. Les références de ligne servent à retrouver les occurrences, pas à prouver leur résolution.

### Bilan de l’exigence SonarQube

Les signalements réels communiqués couvrent désormais quatre occurrences : une dans `ContratController` et trois dans `ContratServiceImpl`. Les corrections correspondantes sont présentes dans le code, et les compilations après correction ont réussi. La deuxième anomalie n’est donc plus à identifier : les nouvelles détections sont documentées ici. L’identifiant de la règle de duplication reste à fournir et la validation après réanalyse reste **À VÉRIFIER**.

Aucune analyse sur un serveur SonarQube n’est attestée par les éléments disponibles : aucun tableau de bord, journal de scanner, résultat de Quality Gate ou `report-task.txt` n’a été retrouvé lors de la vérification précédente. Cela ne remet pas en cause les détections dans SonarQube for IDE communiquées par l’utilisateur ; cela distingue les deux sources de preuve.

## E. Questions de compréhension

L’énoncé original de l’Atelier 5 n’a pas été trouvé dans le projet ni fourni intégralement. Les cinq questions ci-dessous sont formulées d’après les cinq thèmes demandés ; leur correspondance mot pour mot avec l’énoncé reste à vérifier.

### 1. Quel est le rôle de DispatcherServlet ?

`DispatcherServlet` est le contrôleur frontal de Spring MVC. Il reçoit les requêtes destinées à l’application, recherche la méthode de contrôleur correspondant au chemin et à la méthode HTTP, organise la conversion des paramètres et du corps, puis la production de la réponse. Pour `GET /api/clients/{id}`, le contexte retire le préfixe `/api` du chemin de mapping, Spring sélectionne `ClientController.getById()`, convertit `id` en `Long`, puis le contrôleur délègue au service. Un convertisseur HTTP utilisant Jackson transforme ensuite l’objet retourné en JSON. La gestion des exceptions détermine les statuts d’erreur.

### 2. Pourquoi injecter une interface Service dans un contrôleur ?

L’interface définit le contrat métier sans imposer son implémentation. `ClientController` dépend de `IClientService`, et Spring injecte `ClientServiceImpl`. Cette séparation réduit le couplage, permet de tester le contrôleur avec un substitut et maintient les validations et accès aux données dans les couches appropriées. Une méthode déclarée dans une interface ne devient pas automatiquement un endpoint : il faut une méthode de contrôleur avec un mapping HTTP.

### 3. Que signifient les codes HTTP utilisés dans AutoLoc ?

200 indique un succès avec une représentation, 201 une création et 204 une suppression réussie sans corps. 400 indique une requête invalide, 404 une ressource introuvable et 405 une méthode HTTP non autorisée sur une route existante. 500 correspond à une erreur serveur non gérée. Dans AutoLoc, les annotations `@ResponseStatus` fixent 201 et 204 ; les `@ApiResponse` documentent les réponses mais ne transforment pas les exceptions en 400/404. Un 204 observé doit être complété par un contrôle en base lorsque l’objectif est de prouver une suppression persistante.

### 4. Pourquoi PaiementController expose-t-il seulement deux GET ?

Le contrôleur actuel est un point d’accès en consultation : `getAll()` et `getById()` sont ses seules méthodes mappées. Les opérations d’écriture disponibles dans `IPaiementService` ne sont pas exposées en REST. Les paiements peuvent notamment être associés à la création d’un contrat dans le service des contrats. `POST /api/paiements` retourne 405, comme vérifié pendant la session, et non 404 puisque la route existe pour GET. Il n’est pas nécessaire d’ajouter un POST qui renvoie volontairement une erreur.

### 5. Pourquoi utiliser les annotations Jackson sur les relations JPA ?

Les objets Java peuvent former un graphe cyclique, alors qu’une réponse JSON doit rester finie. `@JsonManagedReference` conserve le côté parent-enfants dans la représentation et `@JsonBackReference` retire la référence de retour ; `@JsonIgnore` exclut une propriété sans établir de paire parent-enfants. Dans AutoLoc, la paire `Contrat.paiements` / `Paiement.contrat` et les cinq collections ignorées limitent les récursions et le volume des réponses. Ces annotations concernent le JSON et non les clés étrangères ou les cascades JPA. Les cycles non couverts doivent encore être testés.