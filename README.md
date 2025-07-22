# BilZone

**Version :** 1.0-SNAPSHOT
**API Minecraft :** 1.21.6
**Auteur :** Bilskoww

## Description

BilZone est un plugin Minecraft pour Paper/Spigot (API 1.21.6) qui permet de créer, gérer et manipuler des zones personnalisées dans le monde.
Il permet de définir des zones cubiques via deux points, de détecter l’entrée et la sortie des joueurs dans ces zones, et de gérer ces zones via des commandes simples.

---

## Fonctionnalités principales

* Création et suppression de zones
* Consultation d’informations sur les zones
* Sélection des positions via un outil "wand"
* Détection des entrées/sorties des joueurs dans les zones
* Commandes intuitives pour gérer les zones

---

## Commandes

* `/zone create <nom>` : Crée une zone nommée.
* `/zone remove <nom>` : Supprime une zone existante.
* `/zone info <nom>` : Affiche les détails d’une zone.
* `/zone wand` : Donne un outil pour sélectionner les positions des zones.

---

## Installation

1. Place le fichier `BilZone-1.0-SNAPSHOT.jar` dans le dossier `plugins` de ton serveur Paper/Spigot 1.21.6.
2. Lance ou redémarre ton serveur.
3. Le plugin génère automatiquement un fichier `config.yml` avec les valeurs par défaut.
4. Utilise les commandes `/zone` pour commencer à gérer tes zones.

---

## Configuration

Le fichier `config.yml` est créé automatiquement au premier lancement.
Tu peux modifier les paramètres et messages dans ce fichier pour adapter le plugin à tes besoins.
