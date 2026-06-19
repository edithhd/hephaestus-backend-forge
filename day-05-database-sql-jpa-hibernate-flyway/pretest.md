# Pretest - Database, SQL, JPA, Hibernate, Flyway & Query Relationship

## Objective

Pretest ini digunakan untuk mengukur pemahaman awal peserta tentang database, SQL, JPA, Hibernate, Flyway, dan relationship antar table.

## Instructions

- Jawab dengan singkat dan jelas.
- Tidak perlu membuka dokumentasi.
- Tidak dinilai hanya dari benar atau salah, tetapi dari cara berpikir.
- Estimasi waktu: 20-30 menit.

## Section A - Database Basic

1. Apa itu database?

Jawaban:

```text
Database (Basis data) adalah wadah atau tempat untuk menyimpan data dalam jangka panjang.
```

2. Apa perbedaan menyimpan data di Map dan menyimpan data di database?

Jawaban:

```text
Data di Map bersifat temporer, di mana datanya akan ke-reset setelah kita re-run programnya. Sedangkan data di database tersimpan secara permanen atau jangka panjang kecuali dihapus (tidak direkomendasikan), sehingga datanya akan tetap tersimpan meskipun programnya di-rerun
```

3. Apa itu table?

Jawaban:

```text
Tabel adalah struktur untuk menyimpan data yang terdiri dari baris dan kolom.
```

4. Apa itu row?

Jawaban:

```text
Row atau baris adalah komponen tabel yang menunjukkan jumlah data pada tabel.
```

5. Apa itu column?

Jawaban:

```text
Column atau kolom adalah komponen tabel yang merepresentasikan field pada tabel.
```

6. Apa itu primary key?

Jawaban:

```text
Primary key adalah kunci untuk mengidentifikasi objek suatu entity, atau sebagai unique identifier.
```

7. Apa itu foreign key?

Jawaban:

```text
Foreign key adalah field yang ada di lebih dari 1 field sebagai penghubung kedua field tsb.
```

8. Kenapa aplikasi backend membutuhkan database driver?

Jawaban:

```text
Untuk menyimpan data dalam jangka panjang, misalnya data akun customer.
```

## Section B - SQL Basic

9. Apa fungsi SELECT?

Jawaban:

```text
SELECT berfungsi untuk memilih data yang ingin diakses atau ditampilkan.
```

10. Apa fungsi INSERT?

Jawaban:

```text
INSERT berfungsi untuk memasukkan atau menambahkan data pada tabel.
```

11. Apa fungsi UPDATE?

Jawaban:

```text
UPDATE berfungsi untuk mengubah data pada tabel.
```

12. Apa fungsi DELETE?

Jawaban:

```text
DELETE berfungsi untuk menghapus data pada tabel.
```

13. Apa fungsi WHERE?

Jawaban:

```text
WHERE berfungsi untuk menambahkan kondisi dalam pemilihan data, misalnya SELECT * FROM MsCustomer WHERE CustomerName LIKE 'Andy%' maka hanya mengambil data Customer yang memiliki Nama Andy...
```

14. Apa perbedaan LIKE dan ILIKE di PostgreSQL?

Jawaban:

```text
Belum tau Pak
```

15. Apa fungsi ORDER BY?

Jawaban:

```text
ORDER BY berfungsi untuk mengurutkan data berdasarkan nilai dalam salah satu field.
```

16. Apa fungsi LIMIT?

Jawaban:

```text
Belum tau Pak
```

17. Apa itu JOIN?

Jawaban:

```text
JOIN berfungsi untuk menghubungkan dua tabel agar bisa menarik data yang dibutuhkan dari tabel lain.
```

18. Apa perbedaan INNER JOIN dan LEFT JOIN?

Jawaban:

```text
INNER JOIN hanya mengembalikan data yang memiliki data yang sama di kedua tabel, sedangkan LEFT JOIN mengembalikan semua data di tabel pertama serta data di tabel kedua yang match dengan data di tabel pertama.
```

## Section C - JPA & Hibernate

19. Apa itu JPA?

Jawaban:

```text
Belum tau Pak
```

20. Apa itu Hibernate?

Jawaban:

```text
Belum tau Pak
```

21. Apa perbedaan JPA dan Hibernate?

Jawaban:

```text
Belum tau Pak
```

22. Apa itu Entity?

Jawaban:

```text
Entity adalah representasi dari tabel di database.
```

23. Apa fungsi @Entity?

Jawaban:

```text
Sebagai anotasi untuk menandai class di bawahnya sebagai entity di JPA
```

24. Apa fungsi @Table?

Jawaban:

```text
Sebagai anotasi untuk memetakan class ke suatu tabel.
```

25. Apa fungsi @Id?

Jawaban:

```text
Sebagai anotasi untuk menandai primary key.
```

26. Apa fungsi @GeneratedValue?

Jawaban:

```text
Sebagai anotasi untuk generate value dari ID secara otomatis.
```

27. Apa itu Repository?

Jawaban:

```text
Repository adalah lapisan yang mengelola komunikasi antara aplikasi dengan database.
```

28. Apa fungsi JpaRepository?

Jawaban:

```text
Belum tau Pak
```

29. Pada Spring Boot 3, kenapa import JPA menggunakan jakarta.persistence, bukan javax.persistence?

Jawaban:

```text
Belum tau Pak
```

## Section D - Query

30. Apa itu derived query method di Spring Data JPA?

Jawaban:

```text
Belum tau Pak
```

31. Apa contoh query method untuk mencari customer berdasarkan email?

Jawaban:

```text
SELECT * FROM MsCustomer WHERE email 
```

32. Apa fungsi @Query?

Jawaban:

```text
Sebagai anotasi untuk menuliskan query secara manual. 
```

33. Apa perbedaan JPQL dan native query?

Jawaban:

```text
Belum tau Pak
```

34. Kapan menggunakan native query?

Jawaban:

```text
Belum tau Pak
```

## Section E - Flyway

35. Apa itu database migration?

Jawaban:

```text
Database migration adalah perpindahan data dari satu database ke database yang lain.
```

36. Apa itu Flyway?

Jawaban:

```text
Belum tau Pak
```

37. Kenapa perubahan schema database perlu versioning?

Jawaban:

```text
Sehingga terlihat jelas perubahan yang dilakukan pada schema database dan bisa di-trace, serta memungkinkan version-version lama untuk di-revisit apabila ada masalah pada database.
```

38. Apa maksud file V1__create_customers_table.sql?

Jawaban:

```text
Bahwa file tsb bertujuan untuk membuat tabel Customers dan merupakan versi pertama dari struktur tabel tsb.
```

39. Apa risiko jika struktur database diubah manual tanpa migration?

Jawaban:

```text
Maka versi-versi sebelumnya tidak terdokumentasi dan akan hilang atau tertimpa sehingga tidak bisa di-revisit apabila ada kesalahan.
```

## Section F - Relationship & Lazy Loading

40. Apa itu relationship antar table?

Jawaban:

```text
Relasi antar tabel untuk menggambarkan bagaimana satu tabel terkoneksi dengan tabel lain.
```

41. Apa itu one-to-many?

Jawaban:

```text
One-to-many adalah salah satu jenis relasi yang menggambarkan bahwa 1 data pada tabel bisa terhubung pada beberapa data di tabel yang lain.
```

42. Apa itu many-to-one?

Jawaban:

```text
Many-to-one adalah salah satu jenis relasi yang menggambarkan bahwa beberapa data pada tabel bisa terhubung atau dimiliki oleh satu data di tabel yang lain.
```

43. Apa fungsi @ManyToOne?

Jawaban:

```text
Sebagai anotasi untuk menjelaskan bahwa tabel2 tsb memiliki relasi many to one.
```

44. Apa fungsi @OneToMany?

Jawaban:

```text
Sebagai anotasi untuk menjelaskan bahwa tabel2 tsb memiliki relasi one to many.
```

45. Apa itu lazy loading?

Jawaban:

```text
Lazy loading adalah ketika data di-load hanya ketika data diminta.
```

46. Apa itu eager loading?

Jawaban:

```text
Eager loading adalah ketika data di-load segera bahkan sebelum data dipanggil secara eksplisit.
```

47. Apa risiko lazy loading jika tidak dipahami?

Jawaban:

```text
Belum tau Pak
```

48. Apa itu N+1 query problem?

Jawaban:

```text
Belum tau Pak
```

49. Apa itu join fetch?

Jawaban:

```text
Belum tau Pak
```

## Section G - Finance Case

50. Dalam sistem pinjaman, kenapa customer dan loan application sebaiknya dipisah ke table berbeda?

Jawaban:

```text
Karena merupakan dua data berbeda, di mana 1 customer bisa memiliki beberapa loan application dan memiliki fokus yang berbeda (customer untuk menyimpan data customer, dan loan application untuk data2 berkaitan dengan pangajuan) sehingga sebaiknya dipisah.
```

51. Dalam sistem cicilan, kenapa repayment schedule perlu table sendiri?

Jawaban:

```text
Karena repayment schedule bersifat kaku sehingga sebaiknya ada di tabel terpisah supaya lebih aman dan konsisten.
```

52. Apa contoh query yang berguna untuk melihat loan berdasarkan status?

Jawaban:

```text
SELECT * FROM TrLoanApplication WHERE Status = "SUBMITTED"
```

53. Apa contoh query yang berguna untuk melihat total pembayaran customer?

Jawaban:

```text
SELECT SUM(Payment) AS "Total Pembayaran"
FROM MsCustomer mc JOIN TrPayment tp ON mc.CustomerId = tp.CustomerId
GROUP BY LoanId
```

## Self Assessment

| Area | Score 1-5 |
| --- | --- |
| Database basic |3|
| SQL basic |3|
| JPA |0|
| Hibernate |0|
| Repository |0|
| Flyway |0|
| Relationship |3|
| Join query |3|
| Lazy loading |0|
| Finance data modeling |2|

## Notes

```text
JPA, Hibernate, Repository, Flyway, Lazy loading, Finance data modeling
```

