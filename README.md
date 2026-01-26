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

## Solution

## Domain Model

- **ParkingLot** – Contains multiple floors
- **ParkingFloor** – Contains spots, manages floor-level locks
- **ParkingSpot** – Individual spot, has `ReentrantLock` for atomic occupy/free
- **Vehicle** – Abstract base class for all vehicles
- **Ticket** – Tracks parking session, entry time, vehicle details
- **Receipt** – Tracks unpark details, fee, entry/exit time
- **FeeCalculator / PricingStrategy** – Calculates parking fees per vehicle type
- **ParkingService** – Main service handling park/unpark logic
- **TicketService** – Creates, processes, and closes tickets

---

## Concurrency Design

- **ParkingSpot** – `ReentrantLock` for atomic occupy/free
- **ParkingFloor** – `ReentrantLock` for allocation scan (`tryLock()` for non-blocking)
- **Ticket** – `synchronized` intrinsic lock for lifecycle

**Lock order:**
- Park: `Floor + VehicleType` → `Spot`
- Unpark: `Ticket` → `Spot`

---

## Concurrency Scenarios

1. **Concurrent Park Requests**
    - Multiple threads try to park at the same floor/type
    - Spot-level locks prevent double allocation

2. **Single-threaded Unpark**
    - With single entry/exit, only spot + ticket locks needed

3. **Multiple Floors**
    - Threads scan multiple floors using `tryLock()` (non-blocking)
    - Improves throughput without blocking threads

4. **Race Conditions**
    - Without spot locks, two threads could pick the same free spot
    - Spot-level lock ensures atomic occupy

5. **High Traffic / Vehicle Type Flood**
    - Many threads attempt to park the same vehicle type simultaneously
    - Floor-level `tryLock()` + spot locks allow non-blocking allocation and fairness

---