# `application.properties` Konfigürasyonu

## Veritabanı Konfigürasyonu:
- **spring.application.name**: Uygulamanın adını belirtir. Bu örnekte, uygulamanın adı `library` olarak belirlenmiştir.
- **spring.datasource.url**: Veritabanı bağlantı URL'sini belirtir. Bu ayar, PostgreSQL veritabanına bağlanmak için kullanılan bağlantı string'ini içerir.
    - `jdbc:postgresql://localhost:5432/library-app`: PostgreSQL veritabanının URL’si, `localhost`'ta, `5432` portunda ve `library-app` adlı veritabanına bağlanır.
- **spring.datasource.username**: Veritabanına bağlanırken kullanılacak kullanıcı adını belirtir. Bu örnekte, kullanıcı adı `postgres` olarak belirlenmiştir.
- **spring.datasource.password**: Veritabanına bağlanırken kullanılacak şifreyi belirtir. Bu örnekte, şifre `1234` olarak belirlenmiştir.
- **spring.jpa.hibernate.ddl-auto**: Hibernate'in veritabanı şeması oluşturma/modifikasyon davranışını kontrol eder.
    - `update`: Hibernate, mevcut şemayı günceller. Bu, veritabanı şemasının otomatik olarak değiştirilmesini sağlar.
- **spring.sql.init.mode**: SQL başlatma modunu belirler. `always` değeri, uygulama başlatıldığında her zaman SQL betiklerinin çalıştırılmasını sağlar.
- **spring.jpa.show-sql**: Veritabanı sorgularını log olarak gösterir. `true` değeriyle tüm SQL sorguları konsolda görülebilir.
- **spring.datasource.driver-class-name**: Veritabanı bağlantısı için kullanılacak JDBC sürücüsünün sınıf adını belirtir. Bu örnekte, PostgreSQL için `org.postgresql.Driver` kullanılır.
- **spring.jpa.properties.hibernate.dialect**: Hibernate'in, hangi veritabanı türüne özgü SQL'leri üreteceğini belirtir. Burada PostgreSQL için `org.hibernate.dialect.PostgreSQLDialect` kullanılmıştır.
- **spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access**: `false` değeriyle, Hibernate'e JDBC metadata erişimi kapatılır. Bu, performansı iyileştirebilir.
- **spring.jpa.open-in-view**: `false` değeriyle, Spring'in veritabanı bağlantısını view'lar için açık tutmasını engeller. Bu, lazy-loading hatalarının önlenmesine yardımcı olabilir.

## Özel Parametreler:
- **library.loanPeriodInDays**: Kitap ödünç alma süresi, burada 30 gün olarak belirlenmiştir.
- **library.overdueFineRate**: Geç teslim ceza oranı, burada her gecikilen gün için 0.1 birim olarak belirlenmiştir.

## Genel Bakış:
Bu konfigürasyon, Spring Boot uygulamanızın PostgreSQL veritabanına bağlanmasını, Hibernate ile veritabanı işlemleri yapmasını ve bazı özel uygulama ayarlarını yönetmesini sağlar. Veritabanı bağlantısı ve Hibernate'in çalışma şekli, özellikle geliştirme ve test süreçlerinde önemli roller üstlenir.