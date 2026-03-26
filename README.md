# CHaSP – Community Health & Services Portal

A full-stack web platform designed to centralise community health services, enabling residents, healthcare providers, and volunteers to connect efficiently.

---

##  Overview

CHaSP is a scalable, multi-role system that allows users to access healthcare services, manage appointments, and interact with an AI-powered assistant.

The platform is built using modern full-stack technologies with a focus on security, modular architecture, and real-world usability.

##  Key Features

-  JWT-based Authentication & Role-Based Access Control (RBAC)
-  Multi-role system (Residents, Providers, Volunteers, Admin)
-  AI-powered chatbot (OpenAI API integration)
-  Secure payment integration (Stripe & PayPal)
-  Admin dashboard with analytics
-  RESTful API architecture
-  Responsive UI with modern design

---

##  Architecture

Frontend → Backend → Database → AI Service

- **Frontend:** React + TypeScript + Tailwind CSS  
- **Backend:** Spring Boot (Java)  
- **AI Service:** Python (Flask / FastAPI)  
- **Database:** MySQL  
- **Authentication:** JWT  
- **API Communication:** REST  

## System Design

- Modular multi-tier architecture  
- Separation of concerns (UI, business logic, data layer)  
- Secure API design with token-based authentication  
- Scalable backend structure for future microservices  


##  Tech Stack

### Frontend
- React
- TypeScript
- Tailwind CSS

### Backend
- Java (Spring Boot)
- Node.js (optional services)

### AI Integration
- Python (Flask / FastAPI)
- OpenAI API

### Database
- MySQL

### Tools
- Git & GitHub
- Postman (API testing)
- Maven

---

##  Security Features

- JWT authentication  
- Role-based authorization  
- Secure API endpoints  
- Input validation and error handling  

##  Use Cases

- Residents can find healthcare services and book appointments  
- Providers can manage services and interact with users  
- Volunteers can support community services  
- Admin can monitor system usage and analytics  

---

##  Project Highlights

- Designed a scalable full-stack system using industry-standard architecture  
- Integrated AI chatbot to automate user support  
- Implemented secure payment processing using Stripe & PayPal  
- Developed REST APIs supporting multi-role interactions  


##  Installation & Setup

### 1. Clone Repository
```bash
git clone https://github.com/Lathurzan/chasp-project.git
cd chasp-project
