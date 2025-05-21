¡Aquí tienes una guía básica de **Git**! 🚀

## 📦 **Configuración inicial**
Antes de empezar, configura tu identidad:

```bash
# Configura tu nombre de usuario
git config --global user.name "Tu Nombre"

# Configura tu correo electrónico
git config --global user.email "tuemail@example.com"

# Verifica la configuración
git config --list
```

---

## 📁 **Iniciar un repositorio**
- **Iniciar un nuevo repositorio:**
```bash
git init
```

- **Clonar un repositorio existente:**
```bash
git clone <URL-del-repo>
```

---

## 🏷️ **Estados de los archivos**
En Git, un archivo puede estar en uno de estos estados:
- **Untracked**: El archivo es nuevo, Git aún no lo sigue.
- **Modified**: El archivo ha cambiado desde el último commit.
- **Staged**: El archivo está listo para ser enviado en el próximo commit.
- **Committed**: Los cambios están guardados de forma permanente en el historial del repositorio.

---

## ✅ **Añadir y confirmar cambios**
- **Añadir un archivo al área de staging:**
```bash
git add <archivo>
```

- **Añadir todos los archivos modificados:**
```bash
git add .
```

- **Confirmar (commit) los cambios:**
```bash
git commit -m "Mensaje descriptivo del cambio"
```

---

## 🌿 **Ramas (Branches)**
- **Ver las ramas:**
```bash
git branch
```

- **Crear una nueva rama:**
```bash
git branch <nombre-rama>
```

- **Cambiar de rama:**
```bash
git checkout <nombre-rama>
```

- **Crear y cambiar a una nueva rama:**
```bash
git checkout -b <nombre-rama>
```

---

## 🔄 **Sincronizar con un repositorio remoto**
- **Agregar un repositorio remoto:**
```bash
git remote add origin <URL-del-repo>
```

- **Ver los repositorios remotos:**
```bash
git remote -v
```

- **Enviar cambios al repositorio remoto:**
```bash
git push origin <nombre-rama>
```

- **Obtener los últimos cambios:**
```bash
git pull origin <nombre-rama>
```

---

## 🕵️ **Ver el historial de cambios**
- **Historial de commits:**
```bash
git log
```

- **Historial simplificado:**
```bash
git log --oneline
```

---

## 🚨 **Deshacer cambios**
- **Quitar un archivo del área de staging:**
```bash
git reset <archivo>
```

- **Deshacer el último commit (sin borrar cambios):**
```bash
git reset --soft HEAD~1
```

- **Deshacer el último commit (borrando cambios):**
```bash
git reset --hard HEAD~1
```

---