# Pretest - Java Fundamental

## Objective

Pretest ini bertujuan mengukur pemahaman awal tentang Java Fundamental sebelum membaca materi dan mengerjakan exercise.

## Instructions

- Jawab dengan bahasa sendiri.
- Tidak perlu takut salah.
- Jangan melihat jawaban teman terlebih dahulu.
- Jika belum tahu, tulis apa yang kamu pahami saat ini.

Estimasi waktu: 20-30 menit.

## Section A - Basic Syntax

### 1. Apa itu variable?

Jawaban:

```text
Variable adalah suatu tempat untuk menyimpan suatu nilai tertentu.
Misalnya, kita ingin menyimpan data tinggi badan orang, maka kita membuat
variable tinggiBadan.
```

### 2. Apa itu data type?

Jawaban:

```text
Tipe data adalah penentuan tipe dari variabel berdasarkan nilai dari variabel tersebut.
Misalnya, apabila variable berbentuk teks atau tulisan, maka tipe datanya adalah String.
```

### 3. Sebutkan contoh tipe data untuk text, number, dan true/false.

Jawaban:

```text
Text: String
Number: Integer, float, double, short, long, bigDecimal
True/False: Boolean
```

### 4. Apa perbedaan variable declaration dan assignment?

Jawaban:

```text
Declaration digunakan untuk menentukan tipe data dari suatu variabel. Mis: String name;
sedangkan assignment digunakan untuk meng-assign value untuk variabel tersebut.
Mis: name = scan.nextLine(); 
```

### 5. Apa itu camelCase?

Jawaban:

```text
Camel Case adalah suatu naming convention atau format penamaan di mana huruf pertama dari
kata pertama adalah huruf kecil, dan huruf pertama dari kata kedua adalah huruf besar.
Misalnya digunakan pada Java.
```

## Section B - Class and Object

### 6. Apa itu class?

Jawaban:

```text
Class adalah suatu kelompok besar atau badan yang menaungi variabel-variabel atau
method tertentu yang menjadi karakteristik dari class tersebut. Class bisa digunakan
sebagai blueprint untuk memanggil atau menggunakan variabel/method di dalamnya
secara berulang.
```

### 7. Apa itu object?

Jawaban:

```text
Object adalah suatu representasi dari Class. Jadi apabila kita ingin menggunakan template
suatu Class, kita bisa membuat object baru sebagai instance dari Class tsb dan menggunakan
object tsb untuk memanggil variabel/method di Class tadi.
```

### 8. Apa perbedaan class dan object?

Jawaban:

```text
Class adalah blueprint nya, sedangkan Object adalah penggunaan dari blueprint tersebut.
Misalnya: saat kita ingin membeli rumah, biasanya ada rumah contoh yang menggambarkan
isian dari rumah tersebut. Maka rumah contoh tsb adalah Class dan rumah yang kita beli adalah Object.
```

## Section C - Field, Method, Constructor

### 9. Apa itu field?

Jawaban:

```text
Field adalah variabel yang ada di dalam suatu class atau object.
```

### 10. Apa itu method?

Jawaban:

```text
Method adalah semacam behavior atau aktivitas yang bisa dilakukan.
Biasanya logic untuk melakukan suatu aktivitas disimpan dalam suatu method sehingga ketika
kita ingin menjalankan logic tsb, kita hanya perlu memanggil methodnya.
```

### 11. Apa itu parameter?

Jawaban:

```text
Parameter adalah data yang dibutuhkan untuk menjalankan suatu method.
Contohnya ketika ada method untuk melakukan kalkulasi Debt Burden Ratio (DBR),
maka kita membutuhkan data penghasilan dan data utang dari orang tsb sehingga
method calculateDbr membutuhkan parameter penghasilanBulanan dan utangBerjalan.
```

### 12. Apa itu return value?

Jawaban:

```text
Return value adalah nilai yang dikembalikan dari suatu method.
Jadi ketika method tsb dijalankan, maka akan ada hasil yang dikembalikan.
Contohnya untuk method calculateDbr maka return nya adalah hasil kalkulasinya (Dbr).
```

### 13. Apa itu constructor?

Jawaban:

```text
Constructor adalah hal-hal yang mengkonstruk atau membangun suatu class.
Jadi isinya adalah variabel-variabel dari suatu class beserta tipe datanya.
Contoh: public class Customer {
    String name,
    int age,
    char gender
}
Maka untuk membentuk suatu object dari class Customer, kita membutuhkan variable name,
age, dan gender.

```

## Section D - Access Modifier

### 14. Apa fungsi private?

Jawaban:

```text
Private adalah access modifier di mana hanya class tsb yang bisa mengakses variabel tsb.
Misalnya, variabel private String name tidak dapat diakses oleh class lain.
Untuk mengakses variabel tsb, kita bisa menggunakan setter getter di mana kita bisa
membuat method setVariabel untuk memasukkan value dari variabel tsb dan membuat getVariabel
yang bisa dipanggil untuk get variabel tsb.
```

### 15. Apa fungsi public?

Jawaban:

```text
Public adalah access modifier di mana variabel tsb dapat dipanggil dan diakses oleh
siapapun dari manapun, cukup dipanggil Class.variabel nya saja.
```

### 16. Kenapa field biasanya dibuat private?

Jawaban:

```text
Karena ada data yang bersifat sensitif yang tidak boleh diakses atau disebar ke sembarang
orang, ataupun ada atribut yang tidak boleh dimodifikasi oleh class lain sehingga
aksesnya dibatasi.
```

## Section E - Getter and Setter

### 17. Apa fungsi getter?

Jawaban:

```text
Getter digunakan untuk mengakses variabel2 yang private.
Jadi apabila kita ingin mengakses variabel yang private, kita bisa memanggil
fungsi .getVariabel nya saja.
```

### 18. Apa fungsi setter?

Jawaban:

```text
Setter digunakan untuk write atau memasukkan value pada variabel yang private.
```

### 19. Kenapa tidak semua field dibuat public?

Jawaban:

```text
Karena tidak semua field boleh dimodifikasi atau diubah-ubah sehingga 
tidak dibuat public untuk memastikan agar tetap konsisten.
```

## Section F - Collection

### 20. Apa itu List?

Jawaban:

```text
List adalah suatu tempat untuk menampung sekumpulan data.
Misalnya List Buah bisa berisi Apel, Mangga, Jeruk.
```

### 21. Apa itu Map?

Jawaban:

```text
Map adalah satu 'kamus' untuk menyimpan sekumpulan pasangan key-value.
Misalnya seperti:
'name': 'Edith'
'height': '158'
di mana key name memiliki value Edith dan key height memiliki value F.
```

### 22. Kapan menggunakan List dan kapan menggunakan Map?

Jawaban:

```text
List digunakan apabila ingin menampung sekumpulan data tunggal saja,
sedangkan Map digunakan untuk menampung sekumpulan data yeng memiliki pasangan key-value. 
Misalnya apabila kita ingin menyimpan data nama saja maka bisa membuat List Nama,
sedangkan apabila ingin menyimpan data customer maka kita membuat Map Customer yang berisi
cust1: {
    'name': 'Edith'
    'height': 158
}

cust2: {
    'name': 'Kayla'
    'height': 157
}

```

## Section G - OOP Basic

### 23. Apa itu encapsulation?

Jawaban:

```text
Encapsulation adalah pembungkusan sekumpulan variabel dan method dalam suatu
class sebagai suatu kesatuan.
```

### 24. Apa itu interface?

Jawaban:

```text
Interface adalah suatu template behavior yang bisa di-implement oleh class lain.
```

### 25. Apa itu abstract class?

Jawaban:

```text
Belum tau.
```

### 26. Apa perbedaan interface dan abstract class?

Jawaban:

```text
Belum tau.
```

## Self Assessment

Beri tanda sesuai pemahaman kamu saat ini.

| Topik | Belum paham | Mulai paham | Cukup paham |
| --- | --- | --- | --- |
| Basic syntax | [ ] | [V] | [ ] |
| Class and object | [ ] | [V] | [ ] |
| Field/method/constructor | [ ] | [V] | [ ] |
| Access modifier | [ ] | [V] | [ ] |
| Getter/setter | [ ] | [V] | [ ] |
| List/Map | [V] | [ ] | [ ] |
| Interface/abstract class | [V] | [ ] | [ ] |
