# Library Manager System 

### Full Stack Development with Java, Spring Boot, PostgreSQL, JavaScript

---

Bu proje, `Tam Yığın(Full Stack)` Kütüphane Yöneticisi Uygulamasıdır. Backend, `Spring Boot` ile `Java`, veritabanı için `PostgreSQL` (pgAdmin kullanılarak oluşturulmuştur) ve veritabanı yönetimi için `DBeaver` kullanılarak oluşturulmuştur. Frontend `HTML`, `CSS` ve `JavaScript` ile geliştirilmiştir. API testi için `Postman` kullanılmıştır.

---

## 1. Proje Hakkında (Overview)

- **Kullanılan Teknolojiler**:
   - Backend: Java, Spring Boot
   - Frontend: JavaScript, CSS, HTML  
   - Database: PostgreSQL
   - API Testing: Postman

- **Kullanılan Programlar**:
   - Backend Development: IntelliJ IDEA
   - Frontend Development: Visual Studio Code
   - Database Management: DBeaver, pgAdmin 

- **Veritabanı Yapısı**:
   - Address Table: Adres ismi, adres numarası, şehir, ülke, zip kodu ve ek bilgi bilgilerini içerir.
   - Books Table: Başlık, yazar ismi, isbn, paylaşan, paylaşma yılı, paylaşma yeri, kopya sayısı ve barkod numarası bilgilerini içerir.
   - Members Table: İsim, soyad, doğum tarihi, email, phone, üyelik başlangıç ve bitiş, aktiflik durumu ve barkod numarası bilgilerini içerir. Address tablosuna ait foreign key vardır.
   - Register Table: Kayıt kontrolü, bitiş tarihi, dönüş tarihi, gecikme cezası bilgilerini içerir. Books ve Members tablolarına ait foreign key vardır.

- **Temel Özellikler**:
   - Kullanıcılar Address, Books, Members, Register tablolarında CRUD işlemleri gerçekleştirebilir.
   - Bir üye silindiğinde, üye ile ilişkili bilgiler de silinir.
   - Exception handling, uygulama boyunca hata bildirimleri sağlamak için uygulanmaktadır.
   - DTO (Veri Aktarım Nesneleri) ve Mapper kullanılarak güvenlik ve modülerlik sağlanır.

---

## 2. Teknik Bilgiler (Technical Details)

- **Backend**

   - **Mimari**: Model-View-Controller (MVC)
   - IntelliJ IDEA ile geliştirilmiş ve PostgreSQL veritabanına bağlanmıştır.
   - **Ayarlar**:
      - Maven yapılandırması: IntelliJ IDEA'de Maven ana yolu "Bundled (Maven 3)" olarak ayarlanır.
      - **Java SDK**: `corretto-17` kullanılarak geliştirilmiştir.
      - **application.properties** dosyası:

      ```application.properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/library-manager
      spring.datasource.username=postgres
      spring.datasource.password=12345678
      spring.jpa.hibernate.ddl-auto=update
      spring.sql.init.mode=always
      spring.jpa.show-sql=true
      spring.datasource.driver-class-name=org.postgresql.Driver
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
      spring.jpa.open-in-view=false
      ```

      - **Veritabanı Oluşturma**: `pgAdmin` ile veritabanı oluşturulur.
      - **Veritabanı Komutları (Manuel oluşturmak için)**:

      ```cmd
      cd C:\Program Files\PostgreSQL\17\bin
      psql -U postgres
      CREATE DATABASE "library-manager";
      ```

      - **Not**: Eğer `psql -U postgres` komutu girildikten sonra çıkan şifre bilinmiyorsa (PostgreSQL Kurulumu sırasında superuser için belirlenen şifre):

      ```cmd
      ALTER USER postgres WITH PASSWORD 'new_password';
      ```

      - **Önemli Not**: PostgreSQL kurulumu sırasında `The Database cluster initialization failed.` hatası alınırsa indirilen tüm dosyaları bilgisayardan silin ve kurulumu yönetici olarak açıp konumu `English United State America` seçin.

---

- **Frontend**
  
   - Javascript ile geliştirilmiştir.
   - Kullanıcı arayüzü tasarımı ve backend API'leriyle etkileşim sağlanmıştır.

---

## 3. Kullanım (Usage)

- **Backend**
   Uygulamayı başlatmak için bu komut kullanılır: `mvn spring-boot:run`

- **Frontend**
   Uygulamayı başlatmak için bu komut kullanılır: `http://localhost:8080/Home`

---

## 4. Öne Çıkan Özellikler (Key Features)

- Restful API desteği.
- JWT ile kimlik doğrulama.
- Role-based access control (RBAC).
- Çevrimiçi ve çevrimdışı modlar.

---

## 5. Demo ve Ekran Görüntüleri (Demo & Screenshots)

Backend kısmı Intellij IDEA'dan, Frontend kısmını Visual Studio Code programından çalıştırılabilir.

---

## 6. Katkıda Bulunanlar ve Kaynaklar (Contributors & Resources)

- **Proje Sahibi**: [Batuhan Baysal](https://www.linkedin.com/in/batuhan-baysal-502656170/)

- **Kaynaklar**:
   - Spring Boot dokümantasyonu.
   - React.js resmi kılavuzu.
   - PostgreSQL dökümantasyonu.
   - Udemy Full Stack Development Project

---

## 7. İletişim ve Destek (Contact & Support)

- **LinkedIn**: [Batuhan Baysal LinkedIn Profilim](https://www.linkedin.com/in/batuhan-baysal-502656170/)

- **GitHub**: [Batuhan Baysal GitHub Profilim](https://github.com/BatuhanBaysal)
