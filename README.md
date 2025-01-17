# 📦 Packet Viewer Project

An end-to-end network packet visualization tool that captures, logs, and displays UDP packet data in real-time. This project was created with AWS / Java / React / Nginx.
---

## 🌐 Live Demo

🎥 **Project Walkthrough:** [Loom Video](https://www.loom.com/embed/f6f981b89da248dbadac8f6eba3d6290?sid=b66f6fef-fee3-46c7-bafe-b5f70f0aba94)

---

---

## 🚀 Project Overview

**Frontend:**  
- Built with **React**, **Vite**, **TypeScript**, and **ESLint** for a fast and efficient user interface.  
- Real-time packet log visualization.  
- Responsive and modern UI design.

**Backend:**  
- Developed in **Java** using **Maven** for dependency management.  
- Captures UDP packets and exposes logs through a RESTful **HTTP endpoint**.  
- Implements real-time data handling with **Gson** for JSON serialization.

**Deployment:**  
- Hosted on an **AWS EC2** instance running **Linux**.  
- Configured **Nginx** as a reverse proxy for serving the frontend and backend.  
- Utilized **UFW** for firewall management and security.

**Networking:**  
- Simulates and processes **UDP packets**.  
- Real-time data is exposed through HTTP for frontend consumption.



## 🛠️ Tech Stack

### Frontend  
- **React**  
- **Vite**  
- **TypeScript**  
- **ESLint**  

### Backend  
- **Java**  
- **Maven**  
- **Gson** (for JSON handling)

### DevOps / Deployment  
- **AWS EC2** (Cloud hosting)  
- **Linux** (Ubuntu)  
- **Nginx** (Reverse Proxy)  
- **UFW** (Firewall configuration)

### Networking  
- **UDP Packet** Creation and Processing  
- **HTTP Endpoints** for data sharing

---

## 📂 Project Structure

```
packet-viewer/
├── backend/              # Java backend with Maven
│   ├── src/
│   └── pom.xml
├── frontend/             # React frontend (Vite + TypeScript)
│   ├── src/
│   └── package.json
├── nginx/                # Nginx configuration files
└── README.md
```

---

## 🏆 Key Features

- **Real-Time Packet Logging:** Captures and displays network packets instantly.  
- **Interactive UI:** Dynamic and responsive user interface.  
- **Scalable Backend:** Efficient packet processing with Java.  
- **Secure Deployment:** Hosted on AWS with secure Nginx proxy and firewall configuration.

---

