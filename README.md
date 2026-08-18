# Authentification Forte

Projet personnel développé afin de tester et améliorer mes compétences en développement **Full Stack**, notamment avec **Spring Boot**, **Angular** et les mécanismes d'authentification.

Le projet évoluera progressivement et de nouvelles fonctionnalités seront ajoutées au fur et à mesure de son développement.

## Objectif du projet

J'ai créé ce projet principalement pour **tester mes capacités, expérimenter et améliorer mes compétences techniques** autour du développement Full Stack et de la sécurité des systèmes d'authentification.

Ce projet me permet notamment de travailler sur :

- Angular
- Spring Boot
- Spring Security
- Les API REST
- PostgreSQL
- JWT
- La sécurisation d'une authentification
- La communication entre un frontend et un backend
- Les bonnes pratiques de développement

L'objectif est également de faire évoluer progressivement le système d'authentification avec des mécanismes plus avancés.

---

## Technologies utilisées

### Backend

- Java
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Flyway
- Maven
- Lombok

### Frontend

- Angular
- TypeScript
- HTML
- CSS
- Angular Reactive Forms
- Angular Router

---

## Fonctionnalités actuelles

Le projet permet actuellement :

- Création d'un compte
- Connexion avec email et mot de passe
- Hashage des mots de passe
- Génération d'un JWT après authentification
- Signature et vérification des JWT
- Gestion de l'expiration des JWT
- Protection des endpoints backend
- Protection des routes Angular
- Gestion de l'authentification côté frontend
- Migrations de base de données avec Flyway

---

# Installation

## Prérequis

Avant de lancer le projet, il faut installer :

- Java
- Maven
- Node.js
- npm
- Angular CLI
- PostgreSQL
- Git
- Visual Studio Code

Pour installer Angular CLI :

```bash
npm install -g @angular/cli
```

Vous pouvez vérifier les différentes installations avec :

```bash
java --version
mvn --version
node --version
npm --version
ng version
```

---

## Cloner le projet

```bash
git clone <URL_DU_REPOSITORY>
```

Puis se déplacer dans le projet :

```bash
cd Authentification-Forte
```

Ouvrir le projet avec Visual Studio Code :

```bash
code .
```

---

# Configuration de PostgreSQL

Créer une base de données PostgreSQL :

```text
authentication
```

Configurer ensuite la connexion à PostgreSQL dans la configuration du backend.

Exemple pour un environnement local :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/authentication
spring.datasource.username=postgres
spring.datasource.password=postgres
```

Les identifiants, clés JWT et autres secrets utilisés en production ne doivent pas être commit dans le repository.

Les migrations de la base de données sont gérées avec **Flyway**.

---

# Lancer le Backend

Depuis Visual Studio Code, ouvrir un terminal et se déplacer dans le dossier Backend :

```bash
cd Backend
```

Sous Windows, installer et compiler le projet avec :

```powershell
.\mvnw.cmd clean install
```

Puis lancer Spring Boot :

```powershell
.\mvnw.cmd spring-boot:run
```

Sur Linux/macOS :

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Par défaut, le backend est accessible sur :

```text
http://localhost:8080
```

---

# Lancer le Frontend

Ouvrir un deuxième terminal dans Visual Studio Code :

```bash
cd Frontend
```

Installer les dépendances :

```bash
npm install
```

Puis lancer Angular :

```bash
ng serve
```

Le frontend est ensuite accessible sur :

```text
http://localhost:4200
```

---

# Structure du projet

```text
Authentification-Forte/
│
├── Backend/
│   └── Application Spring Boot
│
├── Frontend/
│   └── Application Angular
│
├── .github/
│   └── CODEOWNERS
│
└── README.md
```

---

# Évolutions prévues

Le projet étant en cours de développement, plusieurs fonctionnalités seront ajoutées progressivement.

Parmi les évolutions envisagées :

- Authentification OTP
- Authentification à deux facteurs
- Gestion des appareils de confiance
- Gestion des sessions
- Refresh tokens
- Amélioration de la gestion des JWT
- Renforcement de la sécurité
- Amélioration de l'interface utilisateur
- Tests automatisés
- Conteneurisation
- Déploiement

Cette liste évoluera en fonction de l'avancement du projet et des fonctionnalités que je souhaiterai expérimenter.

---

## Statut du projet

**Projet en cours de développement**

L'architecture, les fonctionnalités et la documentation sont susceptibles d'évoluer au fur et à mesure de l'avancement du projet.