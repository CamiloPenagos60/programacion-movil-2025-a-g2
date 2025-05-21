import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import TabsPage from '@/views/TabsPage.vue'
import CoursesPage from '@/views/CoursesPage.vue';
import StudentsPage from '@/views/StudentsPage.vue';
import TeachersPage from '@/views/TeachersPage.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/tabs'
  },
  {
    path: '/tabs',
    name: 'Tabs',
    component: TabsPage
  },
  {
    path: '/courses',
    name: 'Courses',
    component: CoursesPage
    
  },
  {
    path: '/students',
    name: 'Students',
    component: StudentsPage
  },
  {
    path: '/teachers',
    name: 'Teachers',
    component: TeachersPage
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;