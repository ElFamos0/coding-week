# codingweek-15

## Utilisation

Le projet se lance avec la commande `./launch.sh`.

## Fonctionnement

### Build System

Ce projet utilise Maven en tant que build system.

### Base de données

Les données de l'application sont stockées dans une base de donées SQLite nommée `amplet.db` enregistrée dans le dossier temporaire de la machine de l'utilisateur (`/tmp` sous Linux, `%TMP%` sous Windows).

L'application interface avec cette base de données grâce à l'ORM (Object Relational Mapper) [ORMLite](https://ormlite.com/) et le driver [sqlite-jdbc](https://github.com/xerial/sqlite-jdbc) de Xerial.
