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
