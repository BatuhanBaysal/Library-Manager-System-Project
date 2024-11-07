# Maven POM Konfigürasyonu

## Genel Proje Bilgisi:
- **groupId**: Projeye ait grup ID'si, genellikle organizasyon ya da proje ismiyle eşleşir. Burada `com.batuhan` olarak belirlenmiştir.
- **artifactId**: Projenin benzersiz ID'si. Bu, proje çıktı dosyasının adını belirler. Burada `library` olarak belirlenmiştir.
- **version**: Projenin sürüm numarasını belirtir. Burada `0.0.1-SNAPSHOT` olarak belirlenmiştir.
- **name**: Projenin adı. Burada `library` olarak belirlenmiştir.
- **description**: Projenin kısa açıklaması. Burada "Library App" olarak belirtilmiştir.

## Properties:
- **java.version**: Kullanılacak Java sürümünü belirtir. Burada Java 17 belirlenmiştir.

## Bağımlılıklar (Dependencies):
1. **spring-boot-starter-data-jpa**: JPA (Java Persistence API) kullanarak veritabanı işlemleri yapmayı sağlar.
2. **spring-boot-starter-web**: Web uygulamaları geliştirmek için gerekli Spring Boot başlangıç bağımlılığıdır. RESTful API'ler geliştirmek için kullanılır.
3. **postgresql**: PostgreSQL veritabanı ile bağlantı sağlamak için kullanılan JDBC sürücüsüdür. `runtime` scope'u ile yalnızca çalışma zamanında gereklidir.
4. **lombok**: Kod tekrarını azaltan ve getter/setter metodlarını otomatik olarak oluşturan bir kütüphanedir. `optional` olarak işaretlenmiştir, yani zorunlu değildir.
5. **spring-boot-starter-test**: Uygulama testi için gerekli bağımlılıkları içerir (JUnit, Hamcrest, Mockito vb.). Bu bağımlılık `test` scope'u ile yalnızca testler için gereklidir.
6. **springdoc-openapi-starter-webmvc-ui**: OpenAPI spesifikasyonu ve Swagger UI entegrasyonunu sağlar. Bu, API dokümantasyonu oluşturur.

## Build Konfigürasyonu:
- **spring-boot-maven-plugin**: Spring Boot uygulamalarının Maven ile paketlenmesi için kullanılan plugin. Ayrıca Lombok kütüphanesinin paketlemeye dahil edilmemesi gerektiği belirtilmiştir.

### Özet:
Bu Maven POM dosyası, Spring Boot uygulamanızın bağımlılıklarını yönetmek ve yapılandırmak için gerekli ayarları içerir. PostgreSQL veritabanı, JPA, Swagger/OpenAPI entegrasyonu ve testler için gerekli bağımlılıklar sağlanmıştır. Ayrıca, Java 17 sürümü ve Spring Boot'un 3.3.4 sürümü kullanılarak uygulama oluşturulmuştur.

## NOT: Eğer PostgreSQL yüklenirken "The database cluster initialisation failed" hatası verirse PostgreSQL dosyasını programlardan kaldırılmalı, sonrasında klasörün içindekiler silinmeli, ardından PostgreSQL indirme dosyasını yönetici olarak açıp yükleme aşamasında lokasyonu "English, United States" seçilmelidir. İndirme seçeneklerinde Database'in aldında kurulu postgreSQL sürümü seçilmelidir.

cmd'yi yönetici olarak çalıştır
>> cd C:\Program Files\PostgreSQL\17\bin (C:\Program Files\PostgreSQL\17\data klasörün içi boş ise hata verir. Bu hatanın alınması durumunda üstteki NOT uygulanmalıdır.)
>> psql -U postgres
>> PostgreSQL kurulurken belirlenen şifre.
>> CREATE DATABASE "library-app"; -->Database oluşturur.