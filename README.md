# 🚆 Terminal-Based Reservation System (Spring Boot + Hibernate)

A simple reservation system backend built using **Spring Boot**, **Hibernate**, and **PostgreSQL**. It allows users to register, search for trains, make/cancel reservations, and manage waiting lists automatically based on seat availability.

---

## 🧩 Modules

### 🔹 User Module
- Register a user
- Update or delete user
- Fetch user by username or get all users

### 🔹 Train Module
- Add/update/delete individual or multiple trains
- View all trains or by train number
- Search trains by source & destination

### 🔹 Reservation Module
- Reserve tickets (auto assign confirmed or waiting)
- Cancel reservation (auto promote waiting to confirmed)
- View user’s reservations
- Check available seats per train

---

## 🛠️ Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## 🚀 Running the Application

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/train-reservation-system.git
cd train-reservation-system
