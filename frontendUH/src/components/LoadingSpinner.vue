<template>
  <div class="spinner-container">
    <div class="spinner"></div>
    <p v-if="message" class="message">{{ message }}</p>
  </div>
</template>

<script setup>
defineProps({
  message: {
    type: String,
    default: 'Cargando...'
  }
})
</script>

<style scoped>
.spinner-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 32px;
  min-height: 200px;
  text-align: center;
  background-color: #f9f9f9;
  border-radius: 12px;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid transparent;
  border-top: 5px solid #3498db;
  border-radius: 50%;
  animation: spin 0.8s ease-in-out infinite;
  background-image: linear-gradient(45deg, #3498db, #9b59b6);
  background-clip: padding-box;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.message {
  margin-top: 16px;
  font-size: 1.1rem;
  color: #555;
  font-weight: 500;
}
</style>
