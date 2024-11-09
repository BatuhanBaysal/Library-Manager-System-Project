# WebConfig Sınıfı
`WebConfig` sınıfı, Spring uygulamasında CORS (Cross-Origin Resource Sharing) yapılandırması için kullanılır. Bu sınıf, dış kaynaklardan gelen isteklerin kontrol edilmesi ve gerekli izinlerin verilmesi amacıyla konfigüre edilir.

## Anotasyonlar:
- **@Configuration**: Spring framework'e, bu sınıfın bir konfigürasyon sınıfı olduğunu bildirir. Yani, Spring uygulamasının başlatılmasında bu sınıfın içerisindeki ayarların dikkate alınması sağlanır.

## WebMvcConfigurer İmplementasyonu:
- **WebMvcConfigurer**: Bu arayüzü implement etmek, web uygulaması için özelleştirilmiş yapılandırmalar yapmaya olanak tanır. Burada, CORS ayarları yapılandırılmaktadır.

## `addCorsMappings(CorsRegistry corsRegistry)` Metodu:
Bu metot, CORS ayarlarının yapılandırılması için kullanılır. Aşağıdaki izinler verilmiştir:
- **allowedOrigins("*")**: Tüm kökenlerden gelen isteklere izin verir.
- **allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS")**: İzin verilen HTTP metodları; GET, POST, PATCH, DELETE ve OPTIONS.
- **allowedHeaders("*")**: Tüm başlıkların (headers) kabul edilmesine izin verir.

Bu konfigürasyon, tüm API yollarına ("/**") dış kaynaklardan gelen istekleri kabul eder ve belirtilen HTTP yöntemlerine izin verir.

## Kullanım Senaryosu:
1. **CORS**: Uygulama, farklı domainlerden gelen istekleri kabul eder ve bu isteklerin HTTP başlıklarını kontrol eder. Bu, özellikle frontend ve backend’in farklı sunucularda çalıştığı durumlarda kullanışlıdır.
2. **Swagger UI**: Bu yapılandırma, Swagger UI üzerinden API'lere yapılan CORS isteklerine izin verir. Swagger UI'nin açılması için URL: `http://localhost:8080/swagger-ui/index.html` kullanılabilir.