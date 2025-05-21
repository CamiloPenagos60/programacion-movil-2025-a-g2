<template>
  <header class="app-header">
    <div class="header-content">
      <h1 class="logo">{{ title }}</h1>
      <nav class="nav-links">
        <router-link 
          v-for="link in links" 
          :key="link.path" 
          :to="link.path"
          class="nav-link"
        >
          {{ link.text }}
        </router-link>
      </nav>
    </div>
  </header>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: 'Coursys'
  },
  links: {
    type: Array,
    default: () => [
      { path: '/courses', text: 'Cursos' },
      { path: '/students', text: 'Estudiantes' },
      { path: '/teachers', text: 'Profesores' }
    ]
  }
})
</script>

<style scoped>
.app-header {
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  color: white;
  padding: 1rem 2rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  margin: 0;
  font-size: 1.8rem;
  font-weight: 700;
  background: linear-gradient(to right, #fff, #f8f9fa);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
}

.nav-links {
  display: flex;
  gap: 1.5rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: 500;
  padding: 0.5rem 0;
  position: relative;
  transition: all 0.3s ease;
}

.nav-link:hover {
  color: #f8f9fa;
}

.nav-link::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background-color: #f8f9fa;
  transition: width 0.3s ease;
}

.nav-link:hover::after {
  width: 100%;
}

.nav-link.router-link-active {
  color: #f8f9fa;
  font-weight: 600;
}

.nav-link.router-link-active::after {
  width: 100%;
  background-color: #4cc9f0;
}

@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    gap: 1rem;
  }
  
  .nav-links {
    gap: 1rem;
  }
}
</style>  