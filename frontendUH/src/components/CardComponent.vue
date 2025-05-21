<template>
  <ion-col size="12" size-md="4">
    <ion-card 
      class="card-container" 
      @click="handleCardClick"
      :class="{'card-disabled': loading}"
    >
      <div class="card-badge" v-if="badgeText">{{ badgeText }}</div>
      <ion-card-header>
        <div class="image-container">
          <div class="image-overlay"></div>
          <ion-img 
            v-if="imageSrc" 
            :src="imageSrc" 
            class="card-image"
            :alt="`Imagen de ${title}`"
          />
          <ion-icon 
            v-else
            :icon="defaultIcon" 
            class="default-icon"
          ></ion-icon>
        </div>
      </ion-card-header>
      
      <ion-card-content>
        <div class="card-content-wrapper">
          <ion-card-title class="card-title">{{ title }}</ion-card-title>
          <p class="card-description">{{ description }}</p>
          
          <ion-button 
            expand="block" 
            @click.stop="navigateToPage"
            :color="color"
            :disabled="loading"
            class="action-button"
            shape="round"
          >
            <ion-spinner v-if="loading" name="lines"></ion-spinner>
            <span v-else>{{ buttonText }}</span>
            <ion-icon :icon="arrowForward" slot="end" v-if="!loading"></ion-icon>
          </ion-button>
        </div>
      </ion-card-content>
    </ion-card>
  </ion-col>
</template>

<script>
import {
  IonCol,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonButton,
  IonImg,
  IonIcon,
  IonSpinner
} from '@ionic/vue';
import { schoolOutline, arrowForward } from 'ionicons/icons';

export default {
  name: 'CardComponent',
  components: {
    IonCol,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardContent,
    IonButton,
    IonImg,
    IonIcon,
    IonSpinner
  },
  props: {
    title: {
      type: String,
      required: true
    },
    description: {
      type: String,
      required: true
    },
    buttonText: {
      type: String,
      required: true,
      default: 'Acceder'
    },
    pageLink: {
      type: String,
      required: true,
      validator: value => value.startsWith('/')
    },
    imageSrc: {
      type: String,
      default: ''
    },
    color: {
      type: String,
      default: 'primary'
    },
    loading: {
      type: Boolean,
      default: false
    },
    clickableCard: {
      type: Boolean,
      default: true
    },
    badgeText: {
      type: String,
      default: ''
    }
  },
  setup() {
    return {
      defaultIcon: schoolOutline,
      arrowForward: arrowForward
    }
  },
  methods: {
    navigateToPage() {
      if (!this.pageLink) return;
      
      this.$emit('navigate', this.pageLink);
      
      if (this.$router) {
        this.$router.push(this.pageLink)
          .catch(err => {
            console.error('Navigation error:', err);
            this.$emit('navigation-error', err);
          });
      } else {
        console.warn('Router not available, using window.location');
        window.location.href = this.pageLink;
      }
    },
    handleCardClick() {
      if (this.clickableCard && !this.loading) {
        this.navigateToPage();
      }
    }
  }
};
</script>

<style scoped>
.card-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  border-radius: 12px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  border: none;
}

.card-container:hover {
  transform: translateY(-8px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.card-disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.card-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  background: var(--ion-color-danger);
  color: white;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 0.7rem;
  font-weight: 600;
  z-index: 2;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.image-container {
  height: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom, rgba(0,0,0,0.1) 0%, rgba(0,0,0,0.3) 100%);
  z-index: 1;
}

.card-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.card-container:hover .card-image {
  transform: scale(1.05);
}

.default-icon {
  font-size: 80px;
  color: var(--ion-color-medium);
  z-index: 1;
}

.card-content-wrapper {
  padding: 16px;
}

.card-title {
  font-size: 1.3rem;
  font-weight: 700;
  margin-bottom: 12px;
  color: var(--ion-color-dark);
}

.card-description {
  color: var(--ion-color-medium);
  font-size: 0.95rem;
  margin-bottom: 24px;
  line-height: 1.5;
}

.action-button {
  margin-top: auto;
  --border-radius: 20px;
  --box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  font-weight: 600;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
}

.action-button:hover {
  --box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

@media (max-width: 768px) {
  .image-container {
    height: 140px;
  }
  
  .card-title {
    font-size: 1.1rem;
  }
  
  .card-description {
    font-size: 0.85rem;
    margin-bottom: 16px;
  }
  
  .action-button {
    font-size: 0.85rem;
  }
}
</style>