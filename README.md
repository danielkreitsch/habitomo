# Habitomo

Habitomo is a habit tracking application designed to help users build and maintain healthy habits.

> [!NOTE]  
> This project is still in development.

## Projects

### Backend

The backend handles data management, API services, and integrations.

![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Kotlin](https://img.shields.io/badge/-Kotlin-0095D5?style=flat-square&logo=kotlin&logoColor=white)
![Notion API](https://img.shields.io/badge/-Notion_API-000000?style=flat-square&logo=notion&logoColor=white)

### App

The app provides a user-friendly interface for habit tracking.

![Angular](https://img.shields.io/badge/-Angular-DD0031?style=flat-square&logo=angular&logoColor=white)
![Ionic](https://img.shields.io/badge/-Ionic-3880FF?style=flat-square&logo=ionic&logoColor=white)

## Installation

### Prerequisites

- Node.js
- Docker
- Java 17

### Step 1: Clone the repository

```shell
git clone https://github.com/danielkreitsch/habitomo.git
```

### Step 2: Navigate to the repository directory

```shell
cd habitomo
```

### Step 3: Install dependencies

```shell
npm install
```

## Running the Projects

### Backend

To serve the database via Docker, use the following command:

```shell
nx infra
```

To serve the backend, use the following command:

```shell
nx serve backend
```

### App

To serve the app, use the following command:

```shell
nx serve app
```
