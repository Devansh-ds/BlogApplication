## **📝 Blog Application - Spring Boot Backend**  

A **backend-only** blog application built with **Spring Boot**. It supports **JWT authentication**, **image storage in MySQL (BLOB with compression & decompression)**, and **AI-powered summarization** using Hugging Face.  

### **🚀 Features**  
✅ **User Authentication** (JWT-based login & registration)  
✅ **Create, Update, Delete, and Fetch Blogs**  
✅ **AI-Powered Blog Summarization** (via `facebook/bart-large-cnn`)  
✅ **Pagination & Sorting** (for efficient blog retrieval)  
✅ **Blog Image Uploads** (Stored in MySQL as compressed BLOBs)  
✅ **Comments System** (Users can comment on blogs)  

---

## **🛠 Tech Stack**  
- **Spring Boot 3.4.0** - Backend framework  
- **Spring Security & JWT** - Authentication & Authorization  
- **Spring Data JPA & MySQL** - Database & ORM  
- **WebClient** - External API calls (Hugging Face)  
- **Docker** - Deployment (Planned)  

---

## **📦 Setup & Installation**  

### **1️⃣ Clone the Repository**  
```sh
git clone https://github.com/Devansh-ds/BlogApplication
cd BlogApplication
```

### **2️⃣ Configure Environment Variables**  
Edit the `application.yml` file and set:  
```
URL=jdbc:mysql://localhost:3306/<your-schema-name>
USERNAME= <your-username>
PASSWORD= <your-passowrd>
HUGGINGFACE_API_KEY= Bearer <hugging-face-token>
```

### **3️⃣ Run the Application**  
```sh
mvn spring-boot:run
```
or  
```sh
docker-compose up --build
```

---

## **🔗 API Endpoints**  
### **Authentication**  
- `POST /auth/register` → Register a new user  
- `POST /auth/login` → Authenticate & get JWT  
- `POST /auth/logout` → To logout from the application

### **Blog Management**  
- `POST /api/v1/blog` → Create a new blog  
- `GET /api/v1/blog` → Fetch all blogs (with pagination & sorting)  
- `GET /api/v1/blog/{id}` → Get a blog by ID  
- `GET /api/v1/blog/{id}/summarize` → Get AI-generated summary  
- `DELETE /api/v1/blog/{id}/` → Delete blog by blogId
- `PUT /api/v1/blog/{id}/` → Update blog by blogId

### **Comments**  
- `POST /api/v1/comment/blog/{id}` → Add a comment  
- `GET /api/v1/comment/blogs/{id}` → Get all comments for a blog  
- `GET /api/v1/comment/{id}` → Get comment by comment Id
- `GET /api/v1/comment/{id}` → Get comment by comment Id
- `PUT /api/v1/comment/{id}` → Update comment made by the user
- `DELETE /api/v1/comment/{id}` → Delete comment made by the user

---

## **🖼 Blog Image Storage (BLOB in MySQL)**  
- Images are stored in **MySQL as `BLOB (byte[])`**.  
- Compression is applied **before saving**, and **decompression** happens while fetching.  

---

## **🚀 Future Enhancements**  
- **Role-Based Access Control (RBAC)** (Currently, Admin role is not implemented)  
- **Swagger API Documentation**  

---

## **📄 License**  
This project is **open-source** under the **MIT License**.  

---
