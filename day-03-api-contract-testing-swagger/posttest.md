# Posttest - API Contract, API Testing & Swagger

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari API Contract, API Testing, dan Swagger.

### 1. Apa itu API contract?

Jawaban:

```text
API contract adalah sekumpulan kesepakatan untuk berjalannya API antara frontend dan backend.
```

### 2. Apa saja isi API contract?

Jawaban:

```text
HTTP status code, endpoint, request header & body, response header & body, path parameter, query parameter, request method, naming convention, dsb.
```

### 3. Kenapa API contract penting untuk frontend/mobile developer?

Jawaban:

```text
Agar komunikasi bisa berjalan dengan baik dan setiap request dapat diarahkan ke service yang sesuai, menjalankan logic yang seharusnya, dan me-return response yang sesuai.
```

### 4. Apa itu DTO?

Jawaban:

```text
DTO (Data Transfer Object) adalah object yang berisi data-data yang dikirim untuk menjalankan komunikasi antara client dan server, seperti request body dan response body.
```

### 5. Apa bedanya request DTO dan response DTO?

Jawaban:

```text
Request DTO adalah object yang berisi data-data yang dikirim dari client ke server, sedangkan response DTO berisi data-data yang dikirim dari server ke client.
```

### 6. Kenapa DTO dan model sebaiknya dipisah?

Jawaban:

```text
Karena model merupakan representasi dari database, sedangkan DTO adalah object yang digunakan untuk menerima dan merespon data dari client. Keduanya memiliki fungsi yang berbeda (DTO untuk berkomunikasi dengan client, sedangkan model untuk connect ke database) sehingga sebaiknya dipisah.
```

### 7. Apa fungsi @JsonProperty?

Jawaban:

```text
Untuk menyesuaikan field name dengan naming convention JSON (snake_case)
```

### 8. Apa fungsi @RequestBody?

Jawaban:

```text
Sebagai anotasi untuk mendeklarasikan parameter sebagai Request Body.
```

### 9. Apa fungsi @PathVariable?

Jawaban:

```text
Sebagai anotasi untuk mendeklarasikan parameter sebagai Path Parameter, misalnya @PathVariable Long id, maka id berfungsi sebagai path parameter.
```

### 10. Apa fungsi @Valid?

Jawaban:

```text
Untuk menjalankan validasi-validasi yang sudah di-set di DTO, seperti @NotBlank, @NotNull, dst.
```

### 11. Apa perbedaan POST dan GET?

Jawaban:

```text
POST berguna untuk menambah data atau write data dalam database, misalnya POST Customer berguna untuk menambahkan data customer baru. Sedangkan GET berguna untuk mengambil 
```

### 12. Apa perbedaan PUT dan PATCH?

Jawaban:

```text
Saat menggunakan PUT, kita harus mengirim ulang semua data atau attribute meskipun mungkin hanya sebagian attribute saja yang ingin di-update. Sedangkan pada PATCH, kita bisa mengirim data yang ingin di-update saja.
```

### 13. Kapan menggunakan 201 Created?

Jawaban:

```text
Ketika menjalankan operasi POST dan object berhasil dibuat.
```

### 14. Kapan menggunakan 200 OK?

Jawaban:

```text
Ketika request yang dikirim berhasil diproses dengan sukses, misalnya GET data customers berhasil me-return semua data customer.
```

### 15. Kapan menggunakan 400 Bad Request?

Jawaban:

```text
Ketika input dari user tidak valid.
```

### 16. Kapan menggunakan 404 Not Found?

Jawaban:

```text
Ketika resource yang di-request tidak ditemukan.
```

### 17. Apa itu API testing?

Jawaban:

```text
API Testing adalah proses testing untuk mengecek apakah API nya berjalan dan mengirimkan request dan mengembalikan response yang sesuai.
```

### 18. Apa saja yang perlu dicek saat API testing?

Jawaban:

```text
Endpoint dan method, HTTP status code, response header & body, request header & body , error handling, dsb.
```

### 19. Apa perbedaan actual response dan expected response?

Jawaban:

```text
Expected Response adalah response yang diharapkan sebagai standar yang seharusnya dihasilkan setelah request dikirim, sedangkan actual response adalah response aktual yang dikirimkan oleh server.
```

### 20. Apa itu Swagger?

Jawaban:

```text
Swagger adalah tools untuk membantu melihat dan mencoba endpoint API dari browser.
```

### 21. Apa itu OpenAPI?

Jawaban:

```text
OpenAPI adalah format dokumentasi API yang bisa dibaca oleh tools.
```

### 22. Apa manfaat Swagger UI?

Jawaban:

```text
Untuk membantu melihat dan mencoba endpoint API dari browser.
```

### 23. URL apa yang digunakan untuk membuka Swagger UI?

Jawaban:

```text
http://localhost:8080/swagger-ui.html
```

### 24. URL apa yang digunakan untuk membuka OpenAPI JSON?

Jawaban:

```text
http://localhost:8080/v3/api-docs
```

### 25. Apa fungsi @Tag?

Jawaban:

```text
@Tag berfungsi untuk memberi nama grup API.
```

### 26. Apa fungsi @Operation?

Jawaban:

```text
@Operation berfungsi untuk menjelaskan endpoint, berisi summary dan description.
```

### 27. Apa fungsi @ApiResponse?

Jawaban:

```text
@ApiResponse menjelaskan kemungkinan response, berisi responseCode dan description.
```

### 28. Apa perbedaan Swagger UI dan Postman?

Jawaban:

```text
Swagger UI membaca API contract dan membuat dokumentasi, sedangkan Postman lebih berfokus pada melakukan API testing.
```

### 29. Apa yang harus dicek jika Swagger UI 404?

Jawaban:

```text
Apakah dependency sudah ditambahkan atau belum, apakah maven sudah reload atau belum, apakah aplikasi sudah di re-run, apakah URL nya sudah benar, apakah Spring Boot versionnya kompatibel dengan versi springdoc.
```

### 30. Bagian mana yang paling sulit dari Day 3?

Jawaban:

```text
Menambahkan fitur search customer by email.
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. API Response
2. JsonPropertyOrder
3. Penambahan request body baru
```

Apa 2 hal yang masih membingungkan?

```text
1. Penambahan Query Parameter dalam coding
2. Pagination Contract
```

Apa 1 pertanyaan untuk mentor?

```text
Bagaimana cara menambahkan pagination contract
```
