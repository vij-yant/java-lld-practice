# Parking Lot – Low Level Design Problem

## 📌 Problem Statement

Design and implement a **Parking Lot system** using **Object-Oriented principles**.

The system should be capable of managing parking spaces for different types of vehicles and support parking and unparking operations efficiently.

The focus of this problem is on **Low Level Design (LLD)**, clean code, and extensibility.

---

## 🚗 Vehicle Types
The parking lot should support the following vehicle types:
- Bike
- Car
- Truck

---

## 🅿️ Parking Slot Types
Each parking slot is designed to accommodate a specific vehicle type:
- Bike Slot
- Car Slot
- Truck Slot

A vehicle can only be parked in a compatible slot.

---

## 🎟️ Ticket
- When a vehicle is parked, a **parking ticket** should be generated.
- The ticket should uniquely identify the parked vehicle and the allocated slot.
- The ticket is required to unpark the vehicle.

---

## 🔁 Functional Requirements

1. Park a vehicle
2. Unpark a vehicle using a ticket
3. Track available parking slots
4. Allocate the nearest available slot
5. Prevent parking when no suitable slot is available

---

## 🚫 Constraints
- The system should not allow parking if no compatible slot is available.
- A parking slot can hold only one vehicle at a time.
- The system should be extensible for future vehicle or slot types.

---

## 🧠 Design Expectations
- Use proper **OOP principles**
- Follow **SOLID principles**
- Avoid hard-coded logic

---

## 🔄 Assumptions
- In-memory system
- Single entry and exit
- No pricing logic
- No UI

---

## 🎯 Objective
Design a clean and extensible Parking Lot system for **LLD interviews**.
