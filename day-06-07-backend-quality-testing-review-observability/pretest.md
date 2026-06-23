# Pretest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Total pertanyaan: 15.
- Estimasi waktu: 15-20 menit.

1. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code adalah kode yang bekerja dan menghasilkan output yang diharapkan.
Trusted code adalah kode yang tidak hanya bekerja sesuai ekspektasi, tetapi juga aman, konsisten, maindan bisa di-maintain.
```

2. Kenapa testing disebut sebagai risk reduction?

Jawaban:

```text
Karena testing bertujuan untuk mengecek program dan menemukan kesalahan sebelum di-deploy sehingga mengurangi risiko terjadi kesalahan pada production yang akan menghasilkan kerugian.
```

3. Apa itu Given-When-Then?

Jawaban:

```text
Given–When–Then adalah format penulisan skenario yang digunakan untuk menjelaskan perilaku sistem. Memiliki format:
- Given (Kondisi awal) -> Menjelaskan kondisi atau situasi awal sebelum aksi dilakukan
- When (Aksi) -> Menjelaskan tindakan atau event yang dilakukan
- Then (Hasil) -> Menjelaskan hasil yang diharapkan
```

4. Apa perbedaan unit test dan integration test?

Jawaban:

```text
Unit test menguji perilaku dan performa unit aplikasi secara terpisah sedangan integration test menguji integrasi dari komponen-komponen tsb secara menyeluruh.
```

5. Kenapa service layer biasanya cocok untuk unit test?

Jawaban:

```text
Karena service layer berada di antara logic bisnis dan database, API, dll. sehingga testing jadi lebih terfokus dan spesifik.
```

6. Apa fungsi JUnit 5?

Jawaban:

```text
Belum tau Pak
```

7. Apa fungsi Mockito?

Jawaban:

```text
Belum tau Pak
```

8. Kenapa dependency seperti repository sering dimock saat unit test?

Jawaban:

```text
Supaya tidak perlu meng-install dependency berulang lagi.
```

9. Apa contoh test case penting untuk `LoanApplicationService`?

Jawaban:

```text
Given customer memiliki credit score tinggi & income mencukupi -> When mengajukan pinjaman -> Then loan disetujui & status = APPROVED

```

10. Apa tujuan peer code review?

Jawaban:

```text
Peer code review bertujuan untuk memastikan kualitas code sebelum digunakan dengan melibatkan rekan satu tim (peer).
```

11. Area apa saja yang perlu dicek saat code review backend?

Jawaban:

```text
API contract, readability, business logic, error handling, input validation, security, clean architecture, logging, dll.
```

12. Apa itu structured logging?

Jawaban:

```text
Structured logging adalah pencatatan log dalam format yang terstruktur, misalnya format JSON (key-value), agar mudah dibaca manusia dan diproses oleh sistem.
```

13. Apa fungsi `correlation_id`?

Jawaban:

```text
Belum tau Pak
```

14. Kapan menggunakan log level `info`, `warn`, dan `error`?

Jawaban:

```text
- Info -> Digunakan untuk informasi normal yang menunjukkan sistem berjalan dengan benar
- Warn -> Digunakan untuk kondisi tidak normal, tapi sistem masih bisa jalan
- Error -> Digunakan untuk kegagalan yang benar-benar mengganggu proses
```

15. Sebutkan data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Password, data keuangan, data sensitif seperti PII (Personally Identifiable Information), token & session, dsb.
```

## Self Assessment

| Area | Score 1-5 |
|---|---|
| Testing mindset |2|
| Given-When-Then |2|
| JUnit 5 |1|
| Mockito |1|
| Service layer testing |2|
| Peer code review |2|
| Structured logging |1|
| Correlation ID |1|
| PII safety |1|
