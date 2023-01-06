# codingweek-15

## Utilisation

Le projet se lance avec la commande `./launch.sh`.

## Fonctionnement

### Build System

Ce projet utilise Maven en tant que build system. L'utilitaire `mvn` est donc nécessaire pour l'utilisation correcte de celui-ci.

### Packaging

Le projet peut être compilé en un seul fichier `.jar` avec les dépendances gràce à la commande `./assemble.sh`.

Il sera enregistré dans le dossier `target`.

### Base de données

Les données de l'application sont stockées dans une base de donées SQLite nommée `amplet.db` enregistrée dans le dossier temporaire de la machine de l'utilisateur (`/tmp` sous Linux, `%TMP%` sous Windows).

L'application interface avec cette base de données grâce à l'ORM (Object Relational Mapper) [ORMLite](https://ormlite.com/) et le driver [sqlite-jdbc](https://github.com/xerial/sqlite-jdbc) de Xerial.

## Tests

Les tests peuvent être lancés avec la commande `./test.sh`.

Ils sont implémentés grâce au framework [JUnit5](https://junit.org/junit5/) et lancés grâce au plugin [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/).

Les tests couvrent les opérations sur la base de données et la sérialisation/désérialisation des cartes et piles en JSON.

## Releases

### Jour 1

Nous n'avons pas pensé à faire un jar à la fin du premier jour. Nous avons réalisé 

### Jour 2

Le fichier `.jar` de la release du jour 2 est disponible [ici](https://cdn.discordapp.com/attachments/1060246427755888742/1060246467853439108/flashcards-DAY2-jar-with-dependencies.jar) (le fichier est trop volumineux pour passer sur GitLab). Nous avons réalisé la majorité des vues (3 vues non terminés). La création de pile et l'ajout de carte est fonctionnel. Par contre il n'était pas encore possible de jouer correctement. 

### Jour 3

Nous n'avons pas fait de jar ce jour la. Mais le mode entrainement et le mode compétition étaient fonctionnels (bien que quelques bugs subsistaient). Il manque encore la gestion des données après l'apprentissage. Certains designs ont été retravaillés. Les imports, export de cartes ont été géré au format JSON. 


### Jour 4

Nous avons effectué un refactor pour réduire le couplage et correctement séparer les vues du modèle.
L'ajout d'image est maintenant possible sur les flashcards.
Nous avons rendu fonctionnelle la page statistique et la page de résultat après apprentissage. Nous avons fini de lier la page ApprParam et la page ApprIg afin que l'on puisse correctement jouer au jeu.  L'après-midi a consisté en finalisation de projet, test et correction de bug.  
