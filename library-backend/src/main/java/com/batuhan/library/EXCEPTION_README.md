# Exception

## ErrorDetails Sınıfı Açıklaması
`ErrorDetails`, uygulama tarafından oluşan hataları daha anlamlı hale getirebilmek için kullanılan bir sınıftır. Hata bilgilerini tutar ve genellikle global hata yönetimi sistemlerinde kullanılır. 

### Alanlar
- **timestamp**: Hata oluşma zamanını temsil eder. `LocalDateTime` tipinde bir değerdir.
- **message**: Hata mesajını içerir. Bu mesaj genellikle kullanıcıya veya geliştiriciye hata hakkında bilgi verir.
- **path**: Hatanın meydana geldiği API uç noktasını veya URL yolunu belirtir. Bu, hatanın hangi kaynağa ait olduğunu anlamak için kullanılır.
- **errorCode**: Hata için özel bir hata kodu sağlar. Hata türünü daha hızlı sınıflandırmak için kullanılabilir.

### Kullanım Senaryoları
- **Global Hata Yönetimi**: Bu sınıf, `@ControllerAdvice` veya benzeri yapılar ile global hata yakalama mekanizmalarında hata bilgilerini kullanıcıya döndürmek için kullanılabilir.
- **Hata Raporlama**: Hatalar meydana geldiğinde, ilgili hata detayları kullanıcıya veya geliştiriciye düzgün bir formatla iletilir.


## ResourceNotFoundException Sınıfı
`ResourceNotFoundException`, belirli bir kaynağın bulunamadığı durumlar için özel bir istisnadır. Genellikle veritabanı sorguları sırasında kaynağın mevcut olmaması durumunda fırlatılır.

### Özellikler
- **resourceName**: Kaynağın adı (örneğin, "Book", "Member").
- **fieldName**: Kaynağın sorgulanan alanı (örneğin, "ID").
- **fieldValue**: Aranan alanın değeri (örneğin, `1`).

### Anlamı
Bu istisna, belirli bir kaynak ve alan değeri ile kaynak bulunamadığında tetiklenir. Kullanıcıya **HTTP 404 (Not Found)** hatası döndürülür.

### Yapıcı Metod
- **ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue)**: İlgili kaynağı, alanı ve değeri alarak istisna mesajını oluşturur.


## GlobalExceptionHandler Sınıfı
`GlobalExceptionHandler`, tüm uygulama genelinde oluşabilecek hataları yakalamak ve uygun bir yanıt döndürmek için kullanılan merkezi bir istisna işleyicisidir. Bu sınıf, uygulamadaki farklı istisnalara göre özelleştirilmiş hata mesajları sağlar.

### Kullanılan Anotasyonlar:
- **@ControllerAdvice**: Bu anotasyon, sınıfın tüm kontrolörler için global hata işleme işlevi görmesini sağlar.
- **@ExceptionHandler**: Belirtilen istisna türünü yakalayarak, işleme için uygun bir metodun çağrılmasını sağlar.

### Metodlar:
#### 1. `handleGlobalException(Exception exception, WebRequest webRequest)`
- **Amaç**: Genel tüm istisnaları yakalar ve uygulama hatalarını (örneğin `NullPointerException`, `IOException`) işler.
- **Yanıt**: 
    - **HTTP Durum Kodu**: `500 INTERNAL SERVER ERROR`
    - **Yanıt**: `ErrorDetails` nesnesi içerir (hata mesajı, istek yolu, hata kodu).

#### 2. `handleResourceNotFoundException(Exception exception, WebRequest webRequest)`
- **Amaç**: `ResourceNotFoundException` türündeki özel istisnaları işler.
- **Yanıt**: 
    - **HTTP Durum Kodu**: `404 NOT FOUND`
    - **Yanıt**: `ErrorDetails` nesnesi içerir (kaynak bulunamadığına dair mesaj, istek yolu, hata kodu).

### `ErrorDetails` Nesnesi
Her iki metodun da döndürdüğü `ErrorDetails` nesnesi, aşağıdaki bilgileri içerir:
- **timestamp**: Hata oluşma zamanı.
- **message**: Hata mesajı.
- **path**: Hata meydana gelen istek yolu.
- **errorCode**: Hata tipi (örneğin, "INTERNAL SERVER ERROR", "RESOURCE NOT FOUND").

### Kullanım Senaryosu:
1. **Global Exception**:
   - `Exception` türünde bir hata oluştuğunda, `handleGlobalException` metodu devreye girer ve **500 Internal Server Error** döner.
2. **Resource Not Found Exception**:
   - `ResourceNotFoundException` fırlatıldığında, `handleResourceNotFoundException` metodu devreye girer ve **404 Not Found** döner.