<template>
  <ion-page>
    <ion-header class="ion-no-border">
      <ion-toolbar color="primary">
        <ion-title class="ion-text-center" style="font-weight: 600">Gestión de Estudiantes</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding">
      <CrudComponent
        ref="crudComponent"
        title="Estudiantes"
        :items="processedEstudiantes"
        :current-item="currentItem"
        @save="saveEstudiantes"
        @delete="deleteEstudiantes"
        @edit="editEstudiantes"
      >
        <template #form>
          <div class="form-container">
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Nombre Completo</ion-label>
              <ion-input 
                v-model="currentItem.nombre" 
                required
                clear-input
                placeholder="Ej: Juan Pérez"
              ></ion-input>
            </ion-item>

            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Cédula</ion-label>
              <ion-input 
                v-model="currentItem.cedula" 
                required
                clear-input
                placeholder="Ej: 1234567890"
              ></ion-input>
            </ion-item>

            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Email</ion-label>
              <ion-input 
                v-model="currentItem.email" 
                type="email"
                clear-input
                placeholder="Ej: estudiante@example.com"
              ></ion-input>
            </ion-item>

            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Teléfono</ion-label>
              <ion-input 
                v-model="currentItem.telefono"
                type="tel"
                pattern="[0-9]{10}"
                maxlength="10"
                clear-input
                placeholder="Ej: 3123456789"
              ></ion-input>
            </ion-item>
          </div>
        </template>
      </CrudComponent>
    </ion-content>
  </ion-page>
</template>

<script>
import CrudComponent from '@/components/CrudComponent.vue';
import { 
  getAllEstudiantes, 
  saveEstudiantes as saveEstudianteService, 
  updateEstudiantes, 
  deleteEstudiantes as deleteEstudianteService 
} from '@/services/estudiantes-service';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonItem,
  IonLabel,
  IonInput,
  toastController
} from '@ionic/vue';

export default {
  name: 'StudentsPage',
  components: {
    CrudComponent,
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonItem,
    IonLabel,
    IonInput
  },
  data() {
    return {
      currentItem: {
        id: null,
        nombre: '',
        cedula: '',
        email: '',
        telefono: ''
      },
      estudiantes: [],
      loading: false
    };
  },
  computed: {
    processedEstudiantes() {
      return this.estudiantes.map(est => ({
        ...est,
        cedula: est.cedula || 'No registrada',
        email: est.email || 'No registrado',
        telefono: est.telefono || 'No registrado'
      }));
    }
  },
  methods: {
    async saveEstudiantes() {
      console.log('Guardando estudiante:', this.currentItem);
      try {
        if (this.currentItem.id) {
          await updateEstudiantes(this.currentItem, this.currentItem.id);
          this.presentToast('Estudiante actualizado correctamente');
        } else {
          await saveEstudianteService(this.currentItem);
          this.presentToast('Estudiante guardado correctamente');
        }
        this.loadEstudiantes();
        this.resetForm();
      } catch (error) {
        this.presentToast('Error al guardar el estudiante');
        console.error(error);
      }
    },
    async deleteEstudiantes(id) {
      try {
        await deleteEstudianteService(id);
        this.presentToast('Estudiante eliminado correctamente');
        this.loadEstudiantes();
      } catch (error) {
        this.presentToast('Error al eliminar el estudiante');
        console.error(error);
      }
    },
    editEstudiantes(item) {
      console.log('Editando:', item); // asegúrate de que tenga un "id"
      this.currentItem = { ...item };
    },
    async loadEstudiantes() {
      try {
        this.loading = true;
        const data = await getAllEstudiantes();
        this.estudiantes = data;
      } catch (error) {
        console.error('Error al cargar estudiantes:', error);
      } finally {
        this.loading = false;
      }
    },
    resetForm() {
      this.currentItem = {
        id: null,
        nombre: '',
        cedula: '',
        email: '',
        telefono: ''
      };
    },
    async presentToast(message) {
      const toast = await toastController.create({
        message,
        duration: 2000,
        position: 'bottom'
      });
      await toast.present();
    }
  },
  mounted() {
    this.loadEstudiantes();
  }
};
</script>

<style scoped>
.form-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}
.form-item {
  margin-bottom: 10px;
}
</style>