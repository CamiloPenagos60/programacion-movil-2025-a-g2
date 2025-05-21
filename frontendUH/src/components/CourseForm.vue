<template>
  <form @submit.prevent="handleSubmit" class="course-form">
    <h2 class="form-title">{{ title }}</h2>
    
    <div class="form-group">
      <label class="input-label">Nombre del Curso</label>
      <input 
        v-model="formData.name" 
        required
        class="form-input"
        placeholder="Ej: Matemáticas Avanzadas"
      >
      <span class="input-icon">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
          <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
        </svg>
      </span>
    </div>
    
    <div class="form-group">
      <label class="input-label">Horario</label>
      <input 
        v-model="formData.schedule" 
        required
        class="form-input"
        placeholder="Ej: Lunes y Miércoles 14:00-16:00"
      >
      <span class="input-icon">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"></circle>
          <polyline points="12 6 12 12 16 14"></polyline>
        </svg>
      </span>
    </div>
    
    <div class="form-actions">
      <button type="submit" class="submit-btn">
        <span>Guardar</span>
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
          <polyline points="17 21 17 13 7 13 7 21"></polyline>
          <polyline points="7 3 7 8 15 8"></polyline>
        </svg>
      </button>
      <button type="button" @click="$emit('cancel')" class="cancel-btn">
        Cancelar
      </button>
    </div>
  </form>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  title: {
    type: String,
    default: 'Nuevo Curso'
  }
})

const formData = ref({
  name: '',
  schedule: ''
})

const emit = defineEmits(['submit', 'cancel'])

const handleSubmit = () => {
  emit('submit', { ...formData.value })
  formData.value = { name: '', schedule: '' } // Reset form
}
</script>

<style scoped>
.course-form {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  max-width: 500px;
  margin: 0 auto;
  transition: all 0.3s ease;
}

.course-form:hover {
  box-shadow: 0 6px 24px rgba(0, 0, 0, 0.12);
}

.form-title {
  color: #2c3e50;
  margin-bottom: 1.5rem;
  text-align: center;
  font-size: 1.5rem;
  font-weight: 600;
}

.form-group {
  margin-bottom: 1.5rem;
  position: relative;
}

.input-label {
  display: block;
  margin-bottom: 0.5rem;
  color: #4a5568;
  font-weight: 500;
  font-size: 0.9rem;
}

.form-input {
  width: 100%;
  padding: 0.75rem 1rem 0.75rem 2.5rem;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 0.95rem;
  transition: all 0.3s ease;
  background-color: #f8fafc;
}

.form-input:focus {
  outline: none;
  border-color: #4299e1;
  box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.2);
  background-color: white;
}

.form-input::placeholder {
  color: #a0aec0;
}

.input-icon {
  position: absolute;
  left: 1rem;
  top: 2.5rem;
  color: #a0aec0;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.submit-btn, .cancel-btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 8px;
  font-weight: 500;
  font-size: 0.95rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.submit-btn {
  background: linear-gradient(135deg, #4299e1 0%, #3182ce 100%);
  color: white;
  box-shadow: 0 2px 6px rgba(66, 153, 225, 0.3);
}

.submit-btn:hover {
  background: linear-gradient(135deg, #3182ce 0%, #2b6cb0 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(66, 153, 225, 0.4);
}

.cancel-btn {
  background: white;
  color: #4a5568;
  border: 1px solid #e2e8f0;
}

.cancel-btn:hover {
  background: #f7fafc;
  border-color: #cbd5e0;
}

@media (max-width: 600px) {
  .course-form {
    padding: 1.5rem;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .submit-btn, .cancel-btn {
    width: 100%;
  }
}
</style>