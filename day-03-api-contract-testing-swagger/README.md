# Day 3 - API Contract, API Testing & Swagger

## Goal

Peserta belajar membuat API contract sederhana, melakukan API testing, dan menggunakan Swagger UI untuk membaca serta mencoba endpoint REST API.

## Why This Day Is Important

API adalah kontrak antara backend dan client. Client perlu tahu endpoint, HTTP method, request body, response body, status code, dan error response sebelum bisa memakai API dengan benar.

API juga perlu diuji agar behavior yang dibuat backend sesuai dengan contract. Jika contract mengatakan endpoint create customer mengembalikan `201 Created`, maka hasil testing juga harus menunjukkan status tersebut.

Swagger membantu dokumentasi API menjadi lebih mudah dibaca. Swagger UI bisa digunakan untuk mencoba API langsung dari browser tanpa membuat request manual dari awal.

Dokumentasi API yang baik membantu backend, frontend, mobile, QA, dan mentor bekerja lebih rapi karena semua pihak membaca sumber informasi yang sama.

## Learning Objectives

Setelah menyelesaikan Day 3, peserta diharapkan mampu:

- Memahami API contract.
- Memahami isi API contract.
- Memahami DTO request dan DTO response.
- Memahami POST, GET, PUT, PATCH.
- Membuat project Spring Boot dari Spring Initializr.
- Menambahkan dependency Spring Web dan Validation.
- Menambahkan dependency Swagger menggunakan `springdoc-openapi-ui`.
- Membuat Customer REST API sederhana.
- Melakukan API testing dengan Postman.
- Melakukan API testing dengan Swagger UI.
- Membaca OpenAPI JSON dari `/v3/api-docs`.
- Memahami perbedaan API contract dan API documentation.

## Tools

- Java 8
- Maven
- Spring Boot 2.7.x
- Spring Initializr
- IntelliJ IDEA or VS Code
- Postman
- Browser
- Swagger UI

## Expected Output

Di akhir Day 3, peserta diharapkan memiliki:

- Project Spring Boot bisa berjalan di `localhost:8080`.
- Customer REST API memiliki endpoint POST, GET, PUT, PATCH.
- API contract ditulis dalam markdown.
- API bisa dites menggunakan Postman.
- Swagger UI bisa dibuka dari browser.
- Endpoint bisa dicoba dari Swagger UI.
- OpenAPI JSON bisa dibuka dari `/v3/api-docs`.

## Not Covered Today

Day 3 belum membahas:

- Database.
- Authentication.
- Authorization.
- JWT.
- Role based access.
- Docker.
- Deployment.
- Advanced OpenAPI customization.

-------------------------------------------------------------

## API Contract

### POST /api/v1/customers
- Method: POST
- URL: /api/v1/customers
- Description: Create a new customer.
- Request body: 
``` json
{
"full_name": "edith",
"email": "edith@mail.com",
"phone_number": "082233445566"
}
```
- Success response: New customer created.
``` json
{
    "email": "edith@mail.com",
    "full_name": "edith",
    "id": 1,
    "phone_number": "082233445566"
}
``` json
- Error response: Invalid user input.
``` json
{
    "code": "VALIDATION_ERROR",
    "message": "Invalid request",
    "errors": [
        {
            "field": "fullName",
            "message": "size must be between 3 and 100"
        }
    ]
}
```
- Status code: 201 Created

### GET /api/v1/customers
- Method: GET
- URL: /api/v1/customers
- Description: Get all customers.
- Request body: -
- Success response: All customers retrieved successfully.
``` json
{
    "email": "edith@mail.com",
    "full_name": "edith",
    "id": 1,
    "phone_number": "082233445566"
}
```
- Error response: -
- Status code: 200 OK

### GET /api/v1/customers/{id}
- Method: GET
- URL: /api/v1/customers/{id}
- Description: Get customer data by id.
- Request body: -
- Success response: Customer data retrieved successfully.
```json
{
    "email": "edith@mail.com",
    "full_name": "edith",
    "id": 1,
    "phone_number": "082233445566"
}
```
- Error response: Customer not found.
```json
{
    "code": "CUSTOMER_NOT_FOUND",
    "message": "Customer not found with id: 2",
    "errors": null
}
```
- Status code: 200 OK

### DELETE /api/v1/customers/{id}
- Method: DELETE
- URL: /api/v1/customers/{id}
- Description: Get customer data by id.
- Request body: -
- Success response: Customer data deleted successfully.
- Error response: Customer not found.
- Status code: 200 OK

### PUT /api/v1/customers/{id}
- Method: PUT
- URL: /api/v1/customers/{id}
- Description: Updates all fields in customer data based on id.
- Request body: -
- Success response: All fields in customer data updated successfully.
- Error response: Customer not found.
- Status code: 200 OK

`PATCH /api/v1/customers/{id}`
