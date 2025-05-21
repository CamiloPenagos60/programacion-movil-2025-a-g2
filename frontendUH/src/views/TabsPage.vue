<template>
  <ion-page class="home-page">
    <ion-header class="header">
      <ion-toolbar color="primary">
        <ion-title class="app-title">
          <div class="logo-container">
            <ion-icon :icon="schoolOutline" class="logo-icon"></ion-icon>
            <span>COURSYS</span>
          </div>
        </ion-title>
      </ion-toolbar>
    </ion-header>
    
    <ion-content class="content">
      <div class="centered-container">
        <div class="welcome-section">
          <h1 class="welcome-title">Bienvenido al Sistema</h1>
          <p class="welcome-subtitle">Gestiona fácilmente estudiantes, profesores y cursos</p>
        </div>

        <div class="cards-container">
          <div class="card-item" v-for="(card, index) in cards" :key="index">
            <h3 class="card-title" style="color: #000000">{{ card.title }}</h3>
            <p class="card-description">{{ card.description }}</p>
            <img :src="card.imageSrc" :alt="card.title" class="card-image">
            <ion-button 
              :router-link="card.pageLink" 
              expand="block" 
              :color="card.color"
              class="action-button"
            >
              {{ card.buttonText }}
            </ion-button>
          </div>
        </div>
      </div>
    </ion-content>
  </ion-page>
</template>

<script>
import { 
  IonPage, 
  IonHeader, 
  IonToolbar, 
  IonTitle, 
  IonContent,
  IonButton,
  IonIcon 
} from '@ionic/vue';
import { schoolOutline } from 'ionicons/icons';

export default {
  name: 'HomePage',
  components: {
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonButton,
    IonIcon
  },
  setup() {
    return {
      schoolOutline
    }
  },
  data() {
    return {
      cards: [
        {
          title: "Estudiantes",
          description: "Administrar registro de estudiantes en el sistema.",
          buttonText: "GESTIONAR ESTUDIANTES",
          pageLink: "/students",
          imageSrc: "/src/imagenes/Estudiantes.png",
          color: "secondary"
        },
        {
          title: "Profesores",
          description: "Administrar registro de profesores en el sistema.",
          buttonText: "GESTIONAR PROFESORES",
          pageLink: "/teachers",
          imageSrc: "/src/imagenes/Profesores.png",
          color: "tertiary"
        },
        {
          title: "Cursos",
          description: "Administrar registro de cursos.",
          buttonText: "GESTIONAR CURSOS",
          pageLink: "/courses",
          imageSrc: "/src/imagenes/Cursos.png",
          color: "success"
        }
      ]
    }
  }
};
</script>

<style scoped>
.home-page {
  --ion-background-color: #f8f9fa;
}

.header {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.app-title {
  font-weight: 700;
  font-size: 1.5rem;
  letter-spacing: 0.5px;
  text-align: center;
}

.logo-container {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.logo-icon {
  font-size: 1.8rem;
}

.content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 56px);
}

.centered-container {
  width: 100%;
  max-width: 800px;
  padding: 20px;
  margin: 0 auto;
}

.welcome-section {
  text-align: center;
  margin-bottom: 40px;
}

.welcome-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--ion-color-primary);
  margin-bottom: 10px;
}

.welcome-subtitle {
  font-size: 1.1rem;
  color: var(--ion-color-medium);
}

.cards-container {
  display: flex;
  flex-direction: column;
  gap: 30px;
  width: 100%;
}

.card-item {
  background: white;
  border-radius: 12px;
  padding: 25px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  text-align: center;
}

.card-title {
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 10px;
}

.card-description {
  color: var(--ion-color-medium);
  margin-bottom: 20px;
}

.card-image {
  width: 100%;
  height: 180px;
  object-fit: contain;
  margin-bottom: 20px;
}

.action-button {
  margin-top: 15px;
  font-weight: 600;
  --border-radius: 8px;
}

@media (min-width: 768px) {
  .cards-container {
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .card-item {
    width: calc(50% - 15px);
    min-width: 300px;
  }
  
  .welcome-title {
    font-size: 2.2rem;
  }
}

@media (min-width: 992px) {
  .card-item {
    width: calc(33.333% - 20px);
  }
  
  .welcome-title {
    font-size: 2.5rem;
  }
}
</style>