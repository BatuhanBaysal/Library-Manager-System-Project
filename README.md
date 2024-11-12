## Library Manager Application (Full Stack Development with Spring Boot)
- Bu proje, Tam Yığın(Full Stack) Kütüphane Yöneticisi Uygulamasıdır. Arka uç, `Spring Boot` ile `Java`, veritabanı için `PostgreSQL` (pgAdmin kullanılarak oluşturulmuştur) ve veritabanı yönetimi için `DBeaver` kullanılarak oluşturulmuştur. Ön uç `HTML`, `CSS` ve `JavaScript` ile geliştirilmiştir. API testi için `Postman` kullanılmıştır.

### Genel Bakış

#### Kullanılan Teknolojiler:
- **Backend**: Java, Spring Boot
- **Frontend**: HTML, CSS, JavaScript
- **Database**: PostgreSQL
- **API Testing**: Postman

#### Kullanılan Programlar:
- **Backend Development**: IntelliJ IDEA
- **Frontend Development**: Visual Studio Code
- **Database Management**: pgAdmin, DBeaver

#### Veritabanı Yapısı:
- **Address Table**: adres ismi, numarası, şehir, ülke, zip kodu ve ek bilgi ayrıntıları içerir.
- **Books Table**: yazar ismi, isbn, başlık gibi ayrıntıları içerir.
- **Members Table**: isim, soyad, doğum tarihi, email, phone gibi ayrıntıları içerir.
- **Register Table**: kayıt ile ilgili ayrıntıları içerir.
    
#### Temel Özellikler:
- Kullanıcılar **Address**, **Books**, **Members**, **Register** tablolarında CRUD işlemleri gerçekleştirebilir.
- Bir üye silindiğinde, üye ile ilişkili bilgiler de silinir.
- `Exception handling`, uygulama boyunca hata bildirimleri sağlamak için uygulanmaktadır.
- Karmaşıklığı azaltmak ve güvenliği sağlamak için **DTO** (Veri Aktarım Nesneleri) ve **Mapper** kullanılır.

### Backend (Arka uç)
Backend, `Spring Boot` kullanılarak `IntelliJ IDEA` ile geliştirilmiş olup, veritabanı `pgAdmin` ile oluşturulmuş ve `PostgreSQL` ile erişim sağlanmıştır.

#### IntelliJ IDEA Ayarları
Backend geliştirmesi için `IntelliJ IDEA` kullanılırken aşağıdaki ayarların yapılması gerekmektedir:

1. **Maven Ayarları**:
   - IntelliJ IDEA'de `Settings` (Ayarlar) -> `Build, Execution, Deployment` (İnşa, Çalıştırma, Dağıtım) -> `Build Tools` (İnşa Araçları) -> `Maven` (Maven) bölümüne gidin.
   - "Maven home path" (Maven ana yolunu) `Bundled (Maven 3)` olarak seçin.

2. **SDK Seçimi**:
   - Java SDK olarak `corretto-17` seçilmelidir.
   - Projede `Java 17` kullanılmıştır.

#### `application.properties` ayarlamaları: 
   - spring.datasource.url=jdbc:postgresql://localhost:5432/library-app --> Dbeaver programında oluşturulan PostgreSQL veritabanındaki `Edit Connection` kısmındaki url.
   - spring.datasource.username=postgres --> PostgreSQL superuser.
   - spring.datasource.password=1234 --> PostgreSQL `Edit Connection` kısmındaki belirlenen şifre.
   - spring.jpa.hibernate.ddl-auto=update
   - spring.sql.init.mode=always
   - spring.jpa.show-sql=true
   - spring.datasource.driver-class-name=org.postgresql.Driver
   - spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   - spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access=false
   - spring.jpa.open-in-view=false

#### Bu kısım `pgAdmin` programı kullanılarak Database oluşturulur. Eğer komut ile yapacaksksanız Employee Management Database'ini manuel olarak oluşturmak için girilecek komutlar (`cmd`):
   - cd C:\Program Files\PostgreSQL\17\bin
   - psql -U postgres
   - CREATE DATABASE "employee-management";

#### Eğer postgres komutuna giderken şifre bilinmiyorsa(Dbeaver Kurulumu sırasında superuser için belirlenen şifre) (`cmd`):
   - ALTER USER postgres WITH PASSWORD 'new_password';

### Frontend
Bu kısım `JavaScript`, `HTML`, `CSS` ile geliştirilmiştir.
