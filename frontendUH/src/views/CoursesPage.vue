<template>
  <ion-page>
    <ion-header class="ion-no-border">
      <ion-toolbar color="primary">
        <ion-title class="ion-text-center" style="font-weight: 600">Gestión de Cursos</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true" class="ion-padding">
      <CrudComponent
        ref="crudComponent"
        title="Cursos"
        :items="processedCursos"
        :current-item="currentItem"
        :loading="loading"
        @save="saveCursos"
        @delete="deleteCursos"
        @edit="editCursos"
      >
        <template #form>
          <div class="form-container">
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Nombre del Curso</ion-label>
              <ion-input 
                v-model="currentItem.nombre" 
                required
                clear-input
                :disabled="loading"
              ></ion-input>
            </ion-item>
            
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Estudiante</ion-label>
              <ion-select 
                v-model="currentItem.estudiantesId.id" 
                interface="popover"
                :disabled="estudiantes.length === 0 || loading"
              >
                <ion-select-option 
                  v-for="estudiante in estudiantes" 
                  :key="estudiante.id" 
                  :value="estudiante.id"
                >
                  {{ estudiante.nombre }}
                </ion-select-option>
              </ion-select>
              <ion-note slot="helper" color="danger" v-if="estudiantes.length === 0">
                No hay estudiantes disponibles
              </ion-note>
            </ion-item>
            
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Profesor</ion-label>
              <ion-select 
                v-model="currentItem.profesoresId.id" 
                interface="popover"
                :disabled="profesores.length === 0 || loading"
              >
                <ion-select-option 
                  v-for="profesor in profesores" 
                  :key="profesor.id" 
                  :value="profesor.id"
                >
                  {{ profesor.nombre }}
                </ion-select-option>
              </ion-select>
              <ion-note slot="helper" color="danger" v-if="profesores.length === 0">
                No hay profesores disponibles
              </ion-note>
            </ion-item>
            
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Horas del Curso</ion-label>
              <ion-input 
                v-model="currentItem.horas" 
                type="number"
                min="1"
                :disabled="loading"
              ></ion-input>
            </ion-item>
            
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Precio ($)</ion-label>
              <ion-input 
                v-model="currentItem.precio" 
                type="number"
                min="0"
                step="0.01"
                :disabled="loading"
              ></ion-input>
            </ion-item>
            
            <ion-item class="form-item" fill="outline">
              <ion-label position="floating">Fecha de Inicio</ion-label>
              <ion-datetime
                v-model="currentItem.fechaInicio"
                display-format="DD/MM/YYYY"
                min="2000-01-01"
                max="2100-12-31"
                :disabled="loading"
                placeholder="Selecciona una fecha"
              ></ion-datetime>
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
  getAllCursos, 
  saveCursos, 
  updateCursos, 
  deleteCursos} from '@/services/cursos-service';
import { getAllProfesores } from '@/services/profesores-service';
import { getAllEstudiantes } from '@/services/estudiantes-service';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonItem,
  IonLabel,
  IonInput,
  IonSelect,
  IonSelectOption,
  IonDatetime,
  IonNote,
  toastController
} from '@ionic/vue';

export default {
  name: 'CoursesPage',
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
    IonSelect,
    IonSelectOption,
    IonDatetime,
    IonNote
  },
  data() {
    return {
      currentItem: {
        id: null,
        nombre: '',
        estudiantesId: { id: null },
        profesoresId: { id: null },
        horas: null,
        precio: null,
        fechaInicio: new Date().toISOString()
      },
      cursos: [],
      profesores: [],
      estudiantes: [],
      loading: false
    };
  },
  computed: {
    processedCursos() {
      return this.cursos.map(item => ({
        ...item,
        estudiantesId: item.estudiantesId || { id: null, nombre: 'No asignado' },
        profesoresId: item.profesoresId || { id: null, nombre: 'No asignado' },
        fechaInicio: item.fechaInicio ? new Date(item.fechaInicio).toLocaleDateString() : 'No definida'
      }));
    }
  },
  async mounted() {
    await this.loadData();
  },
  methods: {
    async loadData() {
      this.loading = true;
      try {
        await Promise.all([
          this.loadCursos(),
          this.loadProfesores(),
          this.loadEstudiantes()
        ]);
      } catch (error) {
        this.showToast('Error al cargar datos', 'danger');
        console.error('Error loading data:', error);
      } finally {
        this.loading = false;
      }
    },
    
    async loadCursos() {
      try {
        this.cursos = await getAllCursos();
      } catch (error) {
        console.error('Error al cargar cursos:', error);
        throw error;
      }
    },
    
    async loadProfesores() {
      try {
        this.profesores = await getAllProfesores();
      } catch (error) {
        console.error('Error al cargar profesores:', error);
        throw error;
      }
    },
    
    async loadEstudiantes() {
      try {
        this.estudiantes = await getAllEstudiantes();
      } catch (error) {
        console.error('Error al cargar estudiantes:', error);
        throw error;
      }
    },
    
    async saveCursos() {
      if (!this.validateForm()) return;
      
      this.loading = true;
      try {
        if (this.currentItem.id) {
          await updateCursos(this.currentItem, this.currentItem.id);
          this.showToast('Curso actualizado con éxito');
        } else {
          await saveCursos(this.currentItem);
          this.showToast('Curso creado con éxito');
        }
        await this.loadCursos();
        this.resetForm();
      } catch (error) {
        this.showToast('Error al guardar el curso', 'danger');
        console.error('Error saving curso:', error);
      } finally {
        this.loading = false;
      }
    },
    
    validateForm() {
      if (!this.currentItem.nombre) {
        this.showToast('El nombre del curso es requerido', 'warning');
        return false;
      }
      if (!this.currentItem.horas || this.currentItem.horas < 1) {
        this.showToast('Las horas deben ser mayor a 0', 'warning');
        return false;
      }
      if (!this.currentItem.precio || this.currentItem.precio < 0) {
        this.showToast('El precio debe ser válido', 'warning');
        return false;
      }
      return true;
    },
    
    async deleteCursos(id) {
      this.loading = true;
      try {
        await deleteCursos(id);
        await this.loadCursos();
        this.showToast('Curso eliminado con éxito');
      } catch (error) {
        this.showToast('Error al eliminar el curso', 'danger');
        console.error('Error deleting curso:', error);
      } finally {
        this.loading = false;
      }
    },
    
    editCursos(curso) {
      this.currentItem = { 
        ...curso,
        estudiantesId: { id: curso.estudiantesId?.id || null },
        profesoresId: { id: curso.profesoresId?.id || null }
      };
    },
    
    resetForm() {
      this.currentItem = {
        id: null,
        nombre: '',
        estudiantesId: { id: null },
        profesoresId: { id: null },
        horas: null,
        precio: null,
        fechaInicio: new Date().toISOString()
      };
    },
    
    async showToast(message, color = 'success') {
      const toast = await toastController.create({
        message,
        duration: 2000,
        color,
        position: 'top'
      });
      await toast.present();
    }
  }
};
</script>

<style scoped>
.form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 16px;
}

.form-item {
  margin-bottom: 20px;
  --border-radius: 8px;
  --border-color: var(--ion-color-medium);
}

ion-datetime {
  width: 100%;
}

ion-select {
  width: 100%;
  justify-content: space-between;
}

@media (max-width: 768px) {
  .form-container {
    padding: 8px;
  }
  
  .form-item {
    margin-bottom: 16px;
  }
}
</style>