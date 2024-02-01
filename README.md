# Dicoding-Android-Fundamental-Akhir
Dicoding Android Fundamental Akhir adalah aplikasi yang harus disubmit dalam menyelesaikan kelas [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14/)
Kelas ditujukan bagi developer yang ingin belajar fundamental pembuatan aplikasi Android, seperti networking dan database, dengan mengacu pada standar kompetensi internasional milik Google Developers Authorized Training Partner. 

## SCREENSHOT!
<img src="https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Akhir/assets/107051384/c87f0a60-5625-4b2b-9d40-2cb8f405a64f" alt="Screenshot_20231228_234319" width="250">   
<img src="https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Akhir/assets/107051384/5f4bba3e-8988-4227-a4b1-0088247897c6" alt="Screenshot_20231228_234319" width="250">   
<img src="https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Akhir/assets/107051384/d46c0a81-468f-473d-84ff-2f3cad189c6d" alt="Screenshot_20231228_234319" width="250"><br>
<img src="https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Akhir/assets/107051384/4b479797-dc39-47c4-8b6f-550cbf33177f" alt="Screenshot_20231228_234319" width="250">   
<img src="https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Akhir/assets/107051384/da70a74b-fc76-4e79-88c0-f1690206dcee" alt="Screenshot_20231228_234319" width="250">   
<img src="https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Akhir/assets/107051384/eb9dfe5d-01bd-4e94-86ab-b50bffb1b6c2" alt="Screenshot_20231228_234319" width="250">

## PETUNJUK INSTALASI
1. Kloning Repositori:
   - Buka terminal atau command prompt di komputer lokal Anda.
   - Gunakan perintah **git clone** diikuti dengan URL yang telah Anda salin.
     ```
     git clone https://github.com/robbyyehezkiel/Dicoding-Android-Fundamental-Awal.git
     ```
3. Buka Proyek di Android Studio:<br>
   Buka Android Studio dan buka proyek yang telah Anda kloning.
4. Bangun Proyek:<br>
   Setelah proyek dimuat, bangun proyek menggunakan opsi **build** di Android Studio.
5. Hubungkan Perangkat Anda:<br>
   Hubungkan perangkat Android Anda ke komputer menggunakan kabel USB dan pastikan debugging USB diaktifkan pada perangkat Anda.
7. Jalankan Aplikasi:
   - Pilih perangkat yang terhubung dari daftar perangkat yang tersedia di Android Studio.
   - Klik tombol **Run** untuk menginstal dan meluncurkan aplikasi di perangkat Anda.
8. Atau, Gunakan Emulator:<br>
   Jika Anda tidak memiliki perangkat fisik, Anda dapat menggunakan Emulator Android yang disediakan oleh Android Studio. Cukup buat perangkat virtual menggunakan AVD Manager dan jalankan aplikasi di emulator.

## KRITERIA SUBMISSION APLIKASI

1. **Favorite User dengan Database**<br>
   Syarat:
   - Aplikasi harus bisa menambah dan menghapus user dari daftar favorite.
   - Aplikasi harus mempunyai halaman yang menampilkan daftar favorite.
   - Menampilkan halaman detail dari daftar favorite.
    
2. **Pengaturan Tema**<br>
   Syarat:
   - Membuat menu untuk mengganti tema (light theme atau dark theme) dengan menggunakan penyimpanan key-value.
   - Pastikan tema tetap terimplementasi walaupun aplikasi ditutup dan dibuka kembali.
       - Caranya yaitu dengan meng-observe data dan mengimplementasikan tema pada halaman pertama.
   - Pastikan setiap komponen dan indikator tetap terlihat jelas ketika berubah tema (Jangan ketika tema gelap, teks dan indikator juga berwarna hitam)

## PENILAIAN
Submission Anda akan dinilai oleh reviewer dengan skala 1-5 berdasarkan dari parameter yang ada.<br>

| Penilaian | Deskripsi |
| --- | --- |
| ![rating-default-1](https://github.com/robbyyehezkiel/Dicoding-Android-Beginner/assets/107051384/a44691b2-f5ae-4d65-8cf1-35e883080b58) | Semua ketentuan terpenuhi, namun terdapat indikasi plagiat yaitu dengan menggunakan project orang lain dan hanya merubah kontennya saja. |
| ![rating-default-2](https://github.com/robbyyehezkiel/Dicoding-Android-Beginner/assets/107051384/2eb100d9-6af2-4612-9a3e-335b2afa400f) | Semua ketentuan terpenuhi, namun terdapat kekurangan pada tampilan aplikasi. |
| ![rating-default-3](https://github.com/robbyyehezkiel/Dicoding-Android-Beginner/assets/107051384/5b91432e-4b00-44cf-abb4-f1440904c94a) | Semua ketentuan wajib terpenuhi, namun tidak terdapat improvisasi atau persyaratan opsional yang dipenuhi. |
| ![rating-default-4](https://github.com/robbyyehezkiel/Dicoding-Android-Beginner/assets/107051384/66b77800-871a-4a5a-8a0f-ffeb86fdffa5) | Semua ketentuan terpenuhi dan menerapkan tiga saran di atas. |
| ![rating-default-5](https://github.com/robbyyehezkiel/Dicoding-Android-Beginner/assets/107051384/acbff36b-016d-4962-8aaa-20f36ec55b8b) | Semua ketentuan terpenuhi dan menerapkan semua saran di atas. |

Anda dapat menerapkan beberapa saran di bawah ini untuk mendapatkan nilai tinggi, berikut sarannya:

1. Menerapkan tampilan aplikasi yang sesuai standar:
   - Tampilan aplikasi memiliki width, height, margin, dan padding yang sesuai.
   - Pemilihan warna yang sesuai tema aplikasi.
   - Tidak ada komponen yang saling bertumpuk.
   - Penggunaan komponen yang sesuai dengan fungsinya.<br>
     Contoh : Komponen ImageView yang dijadikan sebagai button navigasi.
   - Menggunakan SearchView pada fitur pencarian.
   - Aplikasi bisa memberikan pesan eror jika data tidak berhasil ditampilkan.
 
2. Menuliskan kode dengan bersih.
   - Bersihkan comment dan kode yang tidak digunakan.
   - Indentasi yang sesuai.
   - Menghapus import yang tidak digunakan.

3. Terdapat improvisasi fitur seperti (pilih satu)
   - Splash screen yang sesuai dengan tema aplikasi.
   - Share
   - dsb.
4. Menyimpan informasi yang penting (API Key, Base URL) di dalam BuildConfig pada berkas build.gradle.
5. Mengimplementasikan Room untuk menyimpan data ke dalam daftar favorite. 
6. Menerapkan Android Architecture Component (minimal ViewModel dan LiveData) dengan benar.
   - Tidak membuat satu ViewModel untuk beberapa view sekaligus, tetapi dipisah.
7. Menggunakan DataStore untuk penyimpanan pengaturan.
8. Menambahkan testing (unit test data ui test) minimal satu test case.
> **Catatan:**
> Jika submission Anda ditolak maka tidak ada penilaian. Kriteria penilaian bintang di atas hanya berlaku jika submission Anda lulus.
