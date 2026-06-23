# Posttest - Backend Quality: Testing, Peer Code Review & Observability

## Objective

Posttest ini digunakan untuk mengukur pemahaman peserta setelah mempelajari testing mindset, unit testing, peer code review, structured logging, correlation ID, dan PII safety.

## Instructions

- Jawab dengan singkat dan jelas.
- Total pertanyaan: 10.

1. Kenapa testing disebut risk reduction?

Jawaban:

```text
Karena testing bertujuan untuk mengecek program dan menemukan kesalahan sebelum di-deploy sehingga mengurangi risiko terjadi kesalahan pada production yang akan menghasilkan kerugian.
```

2. Apa perbedaan working code dan trusted code?

Jawaban:

```text
Working code adalah kode yang bekerja dan menghasilkan output yang diharapkan.
Trusted code adalah kode yang tidak hanya bekerja sesuai ekspektasi, tetapi juga aman, konsisten, maindan bisa di-maintain.
```

3. Jelaskan pola Given-When-Then.

Jawaban:

```text
Given–When–Then adalah format penulisan skenario yang digunakan untuk menjelaskan perilaku sistem. Memiliki format:
- Given (Kondisi awal) -> Menjelaskan kondisi atau situasi awal sebelum aksi dilakukan
- When (Aksi) -> Menjelaskan tindakan atau event yang dilakukan
- Then (Hasil) -> Menjelaskan hasil yang diharapkan
```

4. Kenapa service layer cocok untuk unit test?

Jawaban:

```text
Karena service layer berada di antara logic bisnis dan database, API, dll. sehingga testing jadi lebih terfokus dan spesifik.
```

5. Apa peran JUnit 5 dan Mockito dalam unit test?

Jawaban:

```text
JUnit 5 digunakan untuk menulis dan menjalankan unit test, sedangkan Mockito digunakan untuk mocking dependency, sehingga kita bisa mensimulasikan perilaku objek lain (misalnya repository) tanpa bergantung pada implementasi asli seperti database.
```

6. Sebutkan 3 test case penting untuk `LoanApplicationService`.

Jawaban:

```text
- Create Loan Application Successfully
- Approve Loan Application Successfully
- Reject Loan Application Successfully
```

7. Apa tujuan peer code review?

Jawaban:

```text
Peer code review bertujuan untuk memastikan kualitas code sebelum digunakan dengan melibatkan rekan satu tim (peer).
```

8. Apa itu structured logging dan kenapa penting?

Jawaban:

```text
Structured logging adalah pencatatan log dalam format yang terstruktur, misalnya format JSON (key-value), dan bersifat penting agar mudah dibaca manusia dan diproses oleh sistem.
```

9. Apa fungsi `correlation_id` pada log dan error response?

Jawaban:

```text
Correlation_id berfungsi sebagai unique identifier untuk melacak satu request end-to-end di log dan error response, sehingga memudahkan debugging dan tracing antar sistem atau service.

```

10. Sebutkan minimal 5 data yang tidak boleh ditulis mentah di log.

Jawaban:

```text
Password, token, NIK, alamat, tanggal lahir
```

## Reflection

Apa 3 hal utama yang kamu pahami hari ini?

```text
1. Correlation ID
2. Mockito 
3. JUnit5
```

Apa 2 hal yang masih membingungkan?

```text
1. Membuat codingan test case
2. Membuat test case untuk negative case
```

Apa 1 hal yang akan kamu cek saat melakukan code review?

```text
Mengecek apakah kode tersebut sudah aman (secure) dan tidak ada error saat dijalankan.
```
