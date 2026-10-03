# Hero Cycle Pricing System 

In this project I built a dynamic pricing system for Hero Cycles, built with Spring Boot. It allows salespeople to create cycle configurations dynamically from different components, with automated total price calculation.

## Architecture

This project solves the pricing engine problem with a dynamic, loosely coupled design:

1. **Component Model (Data Model)**: I created a single generic `Component` entity (e.g., Tyre, GearSet, Frame) instead of static classes. This makes it easy to add new component types (like electric engines) dynamically without changing the database schema.
2. **Cycle Configuration (Class Model)**: A `CycleConfiguration` holds multiple `ConfigurationItem`s. Each item links to a `Component` and stores its `quantity`.
3. **Services (Service Model)**: 
   - `ComponentService`: Manages adding, updating, and removes when ever component is not available(temporary).
   - `CycleConfigurationService`: Uses Dependency Injection (DI) to fetch active components and dynamically calculate the total price based on `quantity × price`.
4. **Soft Deletes(temporary delete when it is not available)**: Components are marked as `active = false` instead of being hard-deleted, so old configurations don't break.

## Tech Stack
* Java
* Spring Boot
* Spring Data JPA
* MySQL Database
* Lombok
* Validation

## API Endpoints

### Components (`/api/components`)
* `POST /api/components` - Add a new part
* `GET /api/components` - List all available parts
* `GET /api/components/{id}` - Get part details
* `PUT /api/components/{id}` - Update part details and price
* `DELETE /api/components/{id}` - Soft-delete part

### Configurations (`/api/configurations`)
* `POST /api/configurations` - Create a cycle configuration and calculate total price
* `GET /api/configurations` - List all cycle configurations
* `GET /api/configurations/{id}` - Get configuration details
* `DELETE /api/configurations/{id}` - Deactivate configuration
