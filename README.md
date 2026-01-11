# Multi-Tenant Order Processing Service

This repository contains a Spring Boot application demonstrating **clean backend architecture** with **CQRS**, **multi-tenant validation**, **Saga orchestration**, and the **Transaction Outbox pattern**.

The focus of this project is **correctness, extensibility, and reliability**, not UI or framework tricks.

---

## ▶️ How to Run the Application

### Prerequisites

* Java 21+
* Any Java IDE (IntelliJ IDEA recommended)

> **Note:** Maven does not need to be installed separately.
> This project uses **Maven Wrapper (`mvnw`)**.

### Steps

```bash
./mvnw spring-boot:run
```

* Application runs on: `http://localhost:8080`
* Database: **H2 (in-memory)**

---

## 🏗 Overall Architecture

The system is designed around **asynchronous order processing** with strong consistency guarantees.

High-level flow:

```
Client
  ↓
REST Controller
  ↓
Application Service
  ↓
Command / Query Handler (CQRS)
  ↓
Database (Order + Outbox Event)
  ↓
Outbox Worker (Scheduled)
  ↓
Saga Processor
```

Key architectural patterns used:

* **CQRS** – separate write and read paths
* **Transaction Outbox** – reliable async processing
* **Saga** – long-running business workflow
* **Strategy + Factory** – tenant-specific validation

---

## 🧱 How Commands and Queries Are Structured (CQRS)

### Command Side (Write Path)

* Commands represent **intent to change state**
* Each command has a dedicated handler
* No read logic is mixed into commands

Example:

* `CreateOrderCommand`
* `CreateOrderHandler`

Flow:

```
Controller → ApplicationService → Command → CommandHandler → DB
```

---

### Query Side (Read Path)

* Queries represent **intent to read data**
* Queries do not modify state
* Dedicated query handlers are used

Example:

* `GetOrderQuery`
* `GetOrderQueryHandler`

Flow:

```
Controller → QueryService → Query → QueryHandler → Repository → DB
```

This separation keeps responsibilities clear and avoids accidental coupling.

---

## 🏢 How Tenant Validation Works

The system supports **multiple tenants**, each with different business rules.

### Design

* A common interface: `TenantOrderValidator`
* One implementation per tenant (e.g. `TenantAValidator`, `TenantBValidator`)
* A factory (`TenantValidatorFactory`) resolves the correct validator at runtime

### How resolution works

* Spring injects all `TenantOrderValidator` beans into a `Map`
* **Key** = tenantId (bean name)
* **Value** = validator implementation

This allows:

* Adding new tenants without modifying existing code
* Full compliance with the **Open–Closed Principle**

---

## 🔁 How the Outbox + Saga Flow Works

### Step 1: Order Creation

When an order is created:

* Order is saved with status `PENDING`
* An `OutboxEvent` is saved **in the same transaction**

This guarantees atomicity.

---

### Step 2: Outbox Worker

* Runs every 5 seconds (`@Scheduled`)
* Picks events with status `PENDING` or `IN_PROGRESS`
* Marks event as `IN_PROGRESS`
* Triggers saga processing

This design supports **retry after crashes**.

---

### Step 3: Saga Processing

The saga:

1. Loads the order
2. Resolves tenant-specific validator
3. Executes business validation
4. Updates order state:

   * `PROCESSED` if valid
   * `FAILED` if validation fails (with failure reason)

Order creation is **never rolled back** — only state transitions occur.

---

## 🔐 Reliability Guarantees

This system guarantees:

* **Safe event processing** (order + outbox in one transaction)
* **Retry support** (IN_PROGRESS events are reprocessed)
* **Consistency across restarts** (state stored in DB, worker is stateless)

These properties match real-world distributed system behavior.

---

## 🗄 H2 Database Access & Viewing Tables

The application uses an **in-memory H2 database**.

### H2 Console

* URL: `http://localhost:8080/h2-console`
* JDBC URL: `jdbc:h2:mem:testdb`
* Username: `sa`
* Password: *(leave empty)*

### Tables to Inspect

* `ORDERS`
* `OUTBOX_EVENT`

You can verify:

* Order state transitions (`PENDING → PROCESSED / FAILED`)
* Outbox event status (`PENDING / IN_PROGRESS / PROCESSED`)

---

## 🔌 Example API Requests (Postman & curl)

Both **Postman** and **curl** can be used to test the APIs.

---

### Create Order (POST)

**Endpoint**

```
POST /api/v1/orders
```

#### Postman

* Method: **POST**
* URL: `http://localhost:8080/api/v1/orders`
* Headers:

   * `Content-Type: application/json`
* Body → raw → JSON:

```json
{
  "tenantId": "TENANT_B",
  "amount": 1000,
  "quantity": 20
}
```

#### curl

```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "tenantId": "TENANT_B",
    "amount": 1000,
    "quantity": 20
  }'
```

**Response**

```json
{
  "orderId": 1,
  "status": "PENDING"
}
```

---

### Get Order (GET)

**Endpoint**

```
GET /api/v1/orders/{orderId}
```

#### Postman

* Method: **GET**
* URL: `http://localhost:8080/api/v1/orders/1`

#### Browser (GET only)

```
http://localhost:8080/api/v1/orders/1
```

#### curl

```bash
curl http://localhost:8080/api/v1/orders/1
```

**Response**

```json
{
  "orderId": 1,
  "tenantId": "TENANT_B",
  "amount": 1000,
  "quantity": 20,
  "status": "PROCESSED",
  "failureMessage": null
}
```

---

## 🧠 Summary

This project demonstrates:

* Clean CQRS separation
* Reliable async processing using Outbox + Saga
* Multi-tenant extensibility
* Strong consistency and retry guarantees

