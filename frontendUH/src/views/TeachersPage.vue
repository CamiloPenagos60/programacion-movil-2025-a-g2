<template>
  <ion-page class="teachers-page">
    <ion-header>
      <ion-toolbar color="primary">
        <ion-title class="page-title">Gestión de Profesores</ion-title>
        <ion-buttons slot="end">
          <ion-button @click="$refs.crudComponent.openAddModal()" fill="solid" color="light">
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <CrudComponent
        ref="crudComponent"
        title="Profesores"
        :items="profesores"
        :current-item="currentItem"
        @save="saveProfesores"
        @delete="deleteProfesores"
        @edit="editProfesores"
      >
        <template #form>
          <ion-list class="form-container">
            <ion-item fill="outline" class="form-item">
              <ion-label position="floating" color="medium">Nombre *</ion-label>
              <ion-input 
                v-model="currentItem.nombre" 
                required
                clear-input
                placeholder="Ej: María González"
              ></ion-input>
            </ion-item>

            <ion-item fill="outline" class="form-item">
              <ion-label position="floating" color="medium">Especialidad *</ion-label>
              <ion-input 
                v-model="currentItem.especialidad" 
                required
                clear-input
                placeholder="Ej: Matemáticas"
              ></ion-input>
            </ion-item>

            <ion-item fill="outline" class="form-item">
              <ion-label position="floating" color="medium">Email</ion-label>
              <ion-input 
                v-model="currentItem.email" 
                type="email"
                clear-input
                placeholder="Ej: profesor@escuela.com"
              ></ion-input>
            </ion-item>
          </ion-list>
        </template>

        <template #item="{ item }">
          <ion-card class="teacher-card">
            <ion-card-header>
              <ion-card-title class="teacher-name">{{ item.nombre }}</ion-card-title>
              <ion-card-subtitle class="teacher-specialty">{{ item.especialidad }}</ion-card-subtitle>
            </ion-card-header>
            <ion-card-content class="teacher-info">
              <ion-item lines="none" class="info-item">
                <ion-icon :icon="mailOutline" slot="start"></ion-icon>
                <ion-label>{{ item.email || 'No registrado' }}</ion-label>
              </ion-item>
            </ion-card-content>
          </ion-card>
        </template>
      </CrudComponent>
    </ion-content>
  </ion-page>
</template>

<script>
import CrudComponent from '@/components/CrudComponent.vue';
import { 
  getAllProfesores,
  saveProfesores,
  updateProfesores,
  deleteProfesores
} from '@/services/profesores-service';
import { 
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonItem,
  IonLabel,
  IonInput,
  IonList,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardSubtitle,
  IonCardContent,
  IonIcon,
  IonButton,
  IonButtons
} from '@ionic/vue';
import { addOutline, mailOutline } from 'ionicons/icons';

export default {
  name: 'TeachersPage',
  components: {
    CrudComponent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonItem,
    IonLabel,
    IonInput,
    IonList,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardSubtitle,
    IonCardContent,
    IonIcon,
    IonButton,
    IonButtons
  },
  setup() {
    return {
      addOutline,
      mailOutline
    }
  },
  data() {
    return {
      currentItem: {
        id: null,
        nombre: '',
        especialidad: '',
        email: ''
      },
      profesores: []
    };
  },
  async mounted() {
    await this.loadProfesores();
  },
  methods: {
    async loadProfesores() {
      try {
        this.profesores = await getAllProfesores();
      } catch (error) {
        console.error('Error al cargar profesores:', error);
        alert('Error al cargar la lista de profesores');
      }
    },
    async saveProfesores() {
      try {
        if (this.currentItem.id) {
          await updateProfesores(this.currentItem, this.currentItem.id);
        } else {
          await saveProfesores(this.currentItem);
        }
        await this.loadProfesores();
        this.resetForm();
      } catch (error) {
        console.error('Error al guardar profesores:', error);
        alert('Error al guardar el profesor');
      }
    },
    async deleteProfesores(id) {
      try {
        await deleteProfesores(id);
        await this.loadProfesores();
      } catch (error) {
        console.error('Error al eliminar profesor:', error);
        alert('Error al eliminar el profesor');
      }
    },
    editProfesores(profesor) {
      this.currentItem = { ...profesor };
      this.$refs.crudComponent.openEditModal();
    },
    resetForm() {
      this.currentItem = {
        id: null,
        nombre: '',
        especialidad: '',
        email: ''
      };
    }
  }
};
</script>

<style scoped>
.teachers-page {
  --ion-background-color: #f8f9fa;
}

.page-title {
  font-weight: 600;
  text-align: center;
}

.form-container {
  background: transparent;
  padding: 0;
}

.form-item {
  margin-bottom: 16px;
  --border-radius: 8px;
  --background: #ffffff;
  --highlight-color-focused: var(--ion-color-primary);
}

.teacher-card {
  border-radius: 12px;
  margin: 10px 0;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.teacher-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.teacher-name {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--ion-color-dark);
}

.teacher-specialty {
  font-size: 0.9rem;
  color: var(--ion-color-primary);
  margin-top: 4px;
}

.teacher-info {
  padding-top: 0;
}

.info-item {
  --padding-start: 0;
  --inner-padding-end: 0;
  --min-height: 32px;
}

.info-item ion-icon {
  font-size: 1.2rem;
  margin-right: 8px;
  color: var(--ion-color-medium);
}

ion-button[slot="end"] {
  --border-radius: 8px;
  margin-right: 8px;
}

@media (min-width: 768px) {
  .teacher-card {
    margin: 12px 0;
  }
  
  .teacher-name {
    font-size: 1.2rem;
  }
}
</style>