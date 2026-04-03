# 🚀 KitchenIQ: AI-Powered Culinary Intelligence Platform

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react)](https://react.dev/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind%20CSS-v4-38B2AC?style=for-the-badge&logo=tailwind-css)](https://tailwindcss.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

**KitchenIQ** is a *Culinary Decision Engine* designed to move food service operations from **reactive guesswork** to **proactive intelligence**. By integrating predictive analytics with real-time inventory and order management, it addresses the multibillion-dollar problem of food waste and operational inefficiency.

---

## 🌟 Core Innovations

### 🔮 Predictive Demand Forecasting
Analyzes historical day-wise and time-slot trends using a custom **Trend Analysis Engine**.  
Generates precise production targets to ensure kitchens prepare exactly what they sell, reducing overproduction by up to **30%**.

---

### ⚡ AI-Driven Surge Pricing & Throttling
A live **Kitchen IQ score** calculates operational load.

- **Dynamic Pricing:** Activates a 10–20% surge multiplier to increase margins  
- **Smart Throttling:** Limits incoming orders to maintain quality and accurate prep-time estimates  

---

### 🌱 Intelligent Waste Mitigation (SDG 12)
Monitors inventory shelf-life in real time.

- Triggers **Expiration Alerts**  
- Suggests automated **Daily Specials** for near-expiry ingredients  
- Converts potential waste into revenue  

---

### 📊 High-Fidelity Executive Dashboard
A data-rich interface providing:

- **Peak Demand Windows:** Shift scheduling insights  
- **Profit Engines:** High-margin vs low-performing items  
- **VIP Retention:** Auto-identifies and rewards loyal customers  

---

## 🏗️ Technical Architecture

### 🔧 Backend (Spring Boot 3.x)

- **Architecture:** Controller → Service → Repository pattern with DTO decoupling  
- **Database:** MySQL with Spring Data JPA  
- **Security:** Stateless authentication using **JWT (JSON Web Tokens)**  
- **AI Layer:** Custom Java-based forecasting & pricing logic  

---

### 🎨 Frontend (React 19 & Tailwind v4)

- **UI/UX:** Modern design using Tailwind CSS + Lucide icons  
- **Visualization:** Data charts via **Recharts**  
- **State Management:** React Hooks + Axios for API integration  

---

## 🚀 Quick Start

### ✅ Prerequisites
- Java 17+
- Maven
- Node.js 18+
- MySQL Server

---

### 1️⃣ Backend Setup

```bash
# Configure database
src/main/resources/application.properties

# Run backend
./mvnw spring-boot:run
```

### 2️⃣ Frontend Setup
```bash
cd frontend
npm install
npm run dev
```
---

## 📂 Project Roadmap
- [x] Predictive Demand Forecasting Engine
- [x] Dynamic Surge Pricing Logic
- [x] Real-time Inventory Expiration Tracking
- [ ] Integration with External Weather APIs (Rain/Heatwave impact)
- [ ] Automated Vendor Supply Chain Re-ordering

 ---

 ## 🌍 Impact
 By digitizing intuition, KitchenIQ helps small businesses achieve **SDG 12 (Responsible Consumption and Production)** goals. We empower local vendors with the same AI-grade tools used by global fast-food giants.

 ---

# Developed by [Prasad Dayal](https://github.com/PrasadDayal)
