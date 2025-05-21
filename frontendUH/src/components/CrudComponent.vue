<template>
  <ion-page class="crud-page">
    <ion-header>
      <ion-toolbar color="primary">
        <ion-title class="page-title">Gestión de {{ title }}</ion-title>
        <ion-buttons slot="end">
          <ion-button @click="goBack" fill="outline" color="light">
            <ion-icon slot="start" :icon="arrowBackOutline"></ion-icon>
            Regresar
          </ion-button>
          <ion-button @click="openNewModal" fill="solid" color="secondary">
            <ion-icon slot="start" :icon="addOutline"></ion-icon>
            Nuevo
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <ion-list lines="none" class="items-list">
        <ion-card 
          v-for="item in items" 
          :key="item.id" 
          class="item-card"
          :class="{'course-card': title.toLowerCase().includes('curso')}"
        >
          <ion-card-header>
            <ion-card-title class="item-title">{{ itemTitle(item) }}</ion-card-title>
            <ion-card-subtitle v-if="title.toLowerCase().includes('curso')" class="item-subtitle">
              {{ formatDate(item.fechaInicio) }}
            </ion-card-subtitle>
          </ion-card-header>
          
          <ion-card-content>
            <!-- Mejora para cursos -->
            <div v-if="title.toLowerCase().includes('curso')" class="course-details">
              <ion-item lines="none" class="detail-item">
                <ion-icon slot="start" :icon="timeOutline" color="medium"></ion-icon>
                <ion-label>{{ item.horas }} horas</ion-label>
              </ion-item>
              
              <ion-item lines="none" class="detail-item">
                <ion-icon slot="start" :icon="cashOutline" color="medium"></ion-icon>
                <ion-label>${{ item.precio }}</ion-label>
              </ion-item>
              
              <ion-item lines="none" class="detail-item">
                <ion-icon slot="start" :icon="personOutline" color="medium"></ion-icon>
                <ion-label>{{ item.estudiantesId?.nombre || 'N/A' }}</ion-label>
              </ion-item>
              
              <ion-item lines="none" class="detail-item">
                <ion-icon slot="start" :icon="schoolOutline" color="medium"></ion-icon>
                <ion-label>{{ item.profesoresId?.nombre || 'N/A' }}</ion-label>
              </ion-item>
            </div>

            <!-- Por defecto si no es curso -->
            <div v-else class="default-details">
              <ion-item 
                v-for="(value, key) in itemDetails(item)" 
                :key="key" 
                lines="none"
                class="detail-item"
              >
                <ion-label>
                  <strong>{{ formatKey(key) }}:</strong> {{ formatValue(value) }}
                </ion-label>
              </ion-item>
            </div>

            <div class="card-actions">
              <ion-button 
                fill="outline" 
                color="primary" 
                @click="handleEdit(item)"
                class="action-button"
              >
                <ion-icon slot="start" :icon="createOutline"></ion-icon>
                Editar
              </ion-button>
              <ion-button 
                fill="outline" 
                color="danger" 
                @click.stop="deleteItem(item.id)"
                class="action-button"
              >
                <ion-icon slot="start" :icon="trashOutline"></ion-icon>
                Eliminar
              </ion-button>
            </div>
          </ion-card-content>
        </ion-card>
      </ion-list>

      <!-- Modal -->
      <ion-modal 
        :is-open="isModalOpen" 
        @didDismiss="closeModal"
        class="crud-modal"
      >
        <ion-header>
          <ion-toolbar color="primary">
            <ion-title class="modal-title">{{ modalTitle }}</ion-title>
            <ion-buttons slot="end">
              <ion-button @click="closeModal" fill="clear" color="light">
                <ion-icon slot="icon-only" :icon="closeOutline"></ion-icon>
              </ion-button>
            </ion-buttons>
          </ion-toolbar>
        </ion-header>
        <ion-content class="ion-padding">
          <form @submit.prevent="save" class="modal-form">
            <slot name="form"></slot>
            <ion-button 
              type="submit" 
              expand="block" 
              color="primary"
              class="submit-button"
            >
              Confirmar Cambios
            </ion-button>
          </form>
        </ion-content>
      </ion-modal>
    </ion-content>
  </ion-page>
</template>

<script>
import { 
  arrowBackOutline,
  addOutline,
  createOutline,
  trashOutline,
  closeOutline,
  timeOutline,
  cashOutline,
  personOutline,
  schoolOutline
} from 'ionicons/icons';

export default {
  name: 'CrudComponent',
  props: {
    title: {
      type: String,
      required: true
    },
    items: {
      type: Array,
      default: () => []
    },
    currentItem: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      isModalOpen: false,
      modalTitle: ''
    };
  },
  setup() {
    return {
      arrowBackOutline,
      addOutline,
      createOutline,
      trashOutline,
      closeOutline,
      timeOutline,
      cashOutline,
      personOutline,
      schoolOutline
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    openNewModal() {
      this.modalTitle = `Nuevo ${this.title}`;
      this.$emit('update:currentItem', {});
      this.isModalOpen = true;
    },
    openEditModal() {
      this.modalTitle = `Editar ${this.title}`;
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },
    save() {
      this.$emit('save');
      this.closeModal();
    },
    deleteItem(id) {
      this.$emit('delete', id);
      this.closeModal();
    },
    handleEdit(item) {
      this.$emit('edit', item);
      this.openEditModal();
    },
    itemTitle(item) {
      return item.nombre || 'Sin título';
    },
    itemDetails(item) {
      const { id, ...details } = item;
      return details;
    },
    formatDate(dateStr) {
      if (!dateStr) return 'Sin fecha';
      const date = new Date(dateStr);
      return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    },
    formatKey(key) {
      return key.charAt(0).toUpperCase() + key.slice(1);
    },
    formatValue(value) {
      return value || 'N/A';
    }
  }
};
</script>

<style scoped>
.crud-page {
  --ion-background-color: #f8f9fa;
}

.page-title {
  font-weight: 600;
  text-align: center;
}

.items-list {
  background: transparent;
}

.item-card {
  border-radius: 12px;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.item-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
}

.course-card {
  border-left: 4px solid var(--ion-color-primary);
}

.item-title {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--ion-color-dark);
}

.item-subtitle {
  font-size: 0.9rem;
  color: var(--ion-color-medium);
}

.detail-item {
  --padding-start: 0;
  --inner-padding-end: 0;
  --min-height: 36px;
}

.detail-item ion-icon {
  font-size: 1.1rem;
  margin-right: 10px;
}

.card-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid var(--ion-color-light-shade);
}

.action-button {
  --border-radius: 8px;
  --padding-start: 12px;
  --padding-end: 12px;
}

.crud-modal {
  --width: 90%;
  --max-width: 500px;
  --border-radius: 16px;
}

.modal-title {
  font-weight: 600;
  text-align: center;
}

.modal-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.submit-button {
  --border-radius: 8px;
  margin-top: 24px;
  font-weight: 600;
}

@media (min-width: 768px) {
  .item-card {
    margin-bottom: 20px;
  }
  
  .item-title {
    font-size: 1.3rem;
  }
}
</style>