# Address 

## Address Entity
`Address` nesnesi, temel adres bilgilerini yönetmek için kullanılan bir JPA Entity sınıfıdır. Bu sınıf, çeşitli adres bilgilerini içeren alanlara ve gerekli doğrulama notasyonlarına sahiptir.

### JPA Varlığı Açıklamaları
- **@Entity**: Bu sınıfın bir JPA varlığı olduğunu belirtir, yani veritabanında bir tabloya karşılık gelir.
- **@Table(name = "address")**: Veritabanında bu varlık için hangi tablo isminin kullanılacağını belirtir.
- **@Data**: Lombok kütüphanesi ile sınıfa otomatik olarak @AllArgsConstructor, @NoArgsConstructor, @Getter, @Setter, @ToString ve @EqualsAndHashCode metodlarını ekler.
- **@Id**: Bu alanın (id) tablodaki birincil anahtar olduğunu belirtir.
- **@GeneratedValue(strategy = GenerationType.IDENTITY)**: id alanının otomatik olarak artan bir kimlik değeri olacağını belirtir.
- **@Column(nullable = false)**: İlgili alanın (örneğin addressName) boş olmaması gerektiğini ifade eder. Eğer boş kalmasın diye bir sınırlamaya gerek yoksa @Column ifadesi kullanılmayabilir. O alanın sütun olduğu yine bilinir.

### Özellikler
- **ID**: Otomatik olarak üretilen benzersiz kimlik.
- **Address Name**: Adresin adı (Zorunlu).
- **Address Number**: Adres numarası (Zorunlu).
- **Place Name**: Mekan adı (Zorunlu).
- **Country**: Ülke adı (Zorunlu).
- **Zip Code**: Posta kodu (İsteğe bağlı).
- **Additional Info**: Ek bilgiler (İsteğe bağlı).

### Kullanılan Teknolojiler
- **Jakarta Persistence API (JPA)**: Veritabanı işlemlerini kolaylaştırmak için kullanılır.
- **Jakarta Validation**: Alanların doğrulama gerekliliklerini sağlar.
- **Lombok**: `@Data` ile getter, setter ve yapılandırıcıları otomatik olarak oluşturur.


## Address DTO
`AddressDTO`, veritabanı işlemlerinden bağımsız olarak `Address` verisini taşımak için kullanılan bir Data Transfer Object (DTO) sınıfıdır. `AddressDTO`, yalnızca adres verilerini API katmanında daha güvenli ve esnek bir şekilde taşımayı amaçlar.

### Özellikler
- **ID**: Benzersiz kimlik numarası.
- **Address Name**: Adresin adı.
- **Address Number**: Adres numarası.
- **Place Name**: Mekan adı.
- **Country**: Ülke adı.
- **Zip Code**: Posta kodu (İsteğe bağlı).
- **Additional Info**: Ek bilgiler (İsteğe bağlı).

### Kullanılan Teknolojiler
- **Lombok**: `@Data` ile getter, setter ve diğer yardımcı metotları otomatik olarak oluşturur.


## Address Mapper
`AddressMapper`, `Address` ve `AddressDTO` nesneleri arasında veri dönüşümünü kolaylaştırmak için kullanılan bir sınıftır. `AddressMapper`, veritabanı ile API katmanı arasında veri taşırken ihtiyaç duyulan dönüşümleri yapar, bu sayede veri uyumluluğunu sağlar.

### Metotlar
- **mapToAddressDTO(Address address)**: `Address` nesnesini `AddressDTO` nesnesine dönüştürür. Bu, veritabanı varlıklarını API katmanında daha güvenli ve esnek bir şekilde kullanmayı sağlar.
- **mapToAddressEntity(AddressDTO addressDTO)**: `AddressDTO` nesnesini `Address` nesnesine dönüştürür. Bu, API’den gelen veriyi veritabanında saklanabilir hale getirir.

### Kullanım Amacı
`AddressMapper` sınıfı, veri taşımacılığı ve dönüşüm işlemlerinde kod tekrarını önler ve daha temiz bir yapı sağlar. DTO ve entity sınıflarının ayrıştırılması, yazılımda katmanlı mimari prensibine uyum sağlar.


## Address Repository
`AddressRepository`, `Address` varlığını veritabanı işlemleriyle entegre etmek için kullanılan bir arayüzdür. `AddressRepository`, Spring Data JPA tarafından sağlanan hazır metotları kullanarak temel CRUD (Create, Read, Update, Delete) işlemlerini kolaylıkla gerçekleştirmeyi sağlar.

### @Repository Açıklaması
- **@Repository**: Bu arayüzün bir veri erişim katmanı (DAO) olduğunu belirtir ve Spring tarafından bu arayüzün bir Spring Bean olarak yönetilmesini sağlar. Ek olarak, çalışma zamanında veri erişim katmanında oluşabilecek hataları yönetmek için kullanılabilir.

### Özellikler
- **JpaRepository**: `AddressRepository`, `JpaRepository<Address, Long>` arayüzünü genişleterek `Address` varlığı için hazır veri erişim yöntemlerine erişim sağlar. `Long` türü, `Address` nesnesinin kimlik alanı türünü belirtir.

### Kullanım Amacı
`AddressRepository` sayesinde `Address` nesneleri için veritabanı işlemleri daha kolay hale gelir ve veri erişim katmanında kod tekrarı önlenmiş olur.

### Kullanılan Teknolojiler
- **Spring Data JPA**: Veritabanı işlemlerini hızlı ve etkili bir şekilde gerçekleştirir.


## Address Service
`AddressService`, adreslerle ilgili temel iş mantığını içeren bir arayüzdür. `AddressService`, CRUD işlemleriyle `AddressDTO` nesnelerinin oluşturulması, okunması, güncellenmesi ve silinmesi için yöntem tanımlamaları içerir. Bu katman, veri erişim katmanı ile API arasında aracı rolü üstlenir ve iş mantığının daha düzenli bir şekilde yapılandırılmasını sağlar.

### Metotlar
- **createAddress(AddressDTO addressDTO)**: Yeni bir adres oluşturur ve oluşturulan adresi döner.
- **getAllAddresses()**: Tüm adreslerin listesini döner.
- **getAddressById(Long id)**: Verilen ID ile eşleşen adresi döner.
- **updateAddress(AddressDTO addressDTO)**: Mevcut bir adresi günceller ve güncellenmiş adresi döner.
- **deleteAddress(Long id)**: Verilen ID'ye sahip adresi siler.

### Kullanım Amacı
`AddressService` arayüzü, iş mantığının uygulanması için gerekli olan sözleşmeyi belirler ve projede iş mantığının net bir şekilde ayrılmasını sağlar.

### @Service Açıklaması
- **@Service**: Bu sınıfın bir servis sınıfı olduğunu belirtir ve Spring tarafından bir Spring Bean olarak yönetilmesini sağlar. Servis katmanındaki iş mantığını uygulamak için kullanılır.

### @AllArgsConstructor Açıklaması
- **@AllArgsConstructor**: Lombok tarafından tüm final veya @NonNull alanlar için bir yapıcı (constructor) oluşturur, böylece `AddressRepository` bağımlılığı otomatik olarak enjekte edilebilir.

### @Override Açıklaması
- **@Override**: Java’da bir yöntemin üst sınıf veya arabirimde tanımlanmış bir yöntemi geçersiz kıldığını belirtmek için kullanılan bir anotasyondur. Kodda işlevi:
    - Belirtilen yöntemin üst sınıf veya arabirimde zaten var olan bir yöntemi yeniden tanımladığını belirtir. Bu sayede yanlış yazım veya yöntem imzası gibi hatalar derleme sırasında yakalanır.
    - Kodun okunabilirliğini artırır, geliştiricilere hangi yöntemlerin üst sınıf/arabirimde geçersiz kılındığını net şekilde gösterir.


## Address Service Impl
`AddressServiceImpl`, `AddressService` arayüzünün gerçekleştirimini (implementation) sağlar ve `Address` nesneleri için CRUD işlemlerini içeren iş mantığını uygular. Bu sınıf, veritabanı işlemlerini yürütmek için `AddressRepository` ve veri dönüşümlerini sağlamak için `AddressMapper` kullanır.

### Metotlar
- **createAddress(AddressDTO addressDTO)**: Yeni bir adres oluşturur, `AddressDTO` verisini `Address` nesnesine dönüştürür ve veritabanına kaydeder.
- **getAllAddresses()**: Veritabanındaki tüm adresleri getirir, DTO nesnelerine dönüştürerek döner.
- **getAddressById(Long id)**: Verilen ID'ye göre adresi getirir ve DTO nesnesi olarak döner.
- **updateAddress(AddressDTO addressDTO)**: Mevcut bir adresi günceller ve güncellenmiş adresi DTO olarak döner. `updateAddressEntityFromDTO` metodu, yalnızca dolu alanları günceller.
- **deleteAddress(Long id)**: Verilen ID’ye göre adresi siler.

### Yardımcı Metotlar
- **updateAddressEntityFromDTO(Address addressToUpdate, AddressDTO addressDTO)**: Güncelleme işlemi sırasında yalnızca dolu olan DTO alanlarını `Address` nesnesine kopyalar.

### Kullanılan Teknolojiler
- **Spring Boot**: İş mantığının yönetimi ve bağımlılık enjeksiyonu için.
- **Lombok**: `@AllArgsConstructor` ile bağımlılıkları oluşturur.
- **Java Streams**: Adres listesini DTO nesnelerine dönüştürmek için kullanılır.


## Address Controller
`AddressController`, API istemcilerinin `Address` verileriyle etkileşime geçmesini sağlayan RESTful bir denetleyicidir. Bu sınıf, adres oluşturma, listeleme, güncelleme ve silme gibi temel CRUD işlemlerini sunar. Her bir uç nokta, `AddressService` sınıfını kullanarak iş mantığına erişir ve HTTP yanıtları döner.

### @RestController Açıklaması
- **@RestController**: Bu sınıfın bir Spring REST denetleyicisi olduğunu belirtir, yani HTTP isteklerini işleyip yanıtlar. Ayrıca `@Controller` ve `@ResponseBody` anotasyonlarını bir araya getirir.

### @AllArgsConstructor Açıklaması
- **@AllArgsConstructor**: Lombok tarafından tüm final veya @NonNull alanlar için bir yapıcı (constructor) oluşturur, böylece `AddressService` bağımlılığı otomatik olarak enjekte edilebilir.

### @RequestMapping Açıklaması
- **@RequestMapping("api/address")**: Bu denetleyicideki tüm URL yollarını "api/address" ile başlatır. Böylece sınıfın her bir isteği bu kök URL'ye göre eşlenir.

### @PostMapping Açıklaması
- **@PostMapping("createAddress")**: `addAddress` yönteminin HTTP POST isteklerini "api/address/createAddress" yoluyla işlemesini sağlar.

### @GetMapping Açıklamaları
- **@GetMapping("listAll")**: `getAllAddresses` yönteminin HTTP GET isteklerini "api/address/listAll" yoluyla işlemesini sağlar.
- **@GetMapping("{id}")**: `getAddressById` yönteminin "api/address/{id}" yolundan dinamik id parametresini alarak HTTP GET isteklerini işlemesini sağlar.

### @PatchMapping Açıklaması
- **@PatchMapping("updateAddress/{id}")**: `updateAddress` yönteminin "api/address/updateAddress/{id}" yolundan dinamik id parametresini alarak HTTP PATCH isteklerini işlemesini sağlar.

### @DeleteMapping Açıklaması
- **@DeleteMapping("deleteAddress/{id}")**: `deleteAddress` yönteminin "api/address/deleteAddress/{id}" yolundan id parametresini alarak HTTP DELETE isteklerini işlemesini sağlar.

### @PathVariable Açıklaması
- **@PathVariable**: URL yolundaki `{id}` parametresini yöntem parametresi olan `id` ile eşleştirir.

### @RequestBody Açıklaması
- **@RequestBody**: HTTP isteği gövdesindeki JSON verisini `AddressDTO` nesnesine dönüştürür.

### Uç Noktalar (Endpoints)
- **POST /api/address/createAddress**: Yeni bir adres oluşturur. `AddressDTO` nesnesini JSON formatında alır ve `AddressDTO` nesnesi olarak döner.
- **GET /api/address/listAll**: Tüm adresleri listeleyerek bir `AddressDTO` listesi döner.
- **GET /api/address/{id}**: Verilen ID’ye göre bir adres getirir ve `AddressDTO` nesnesi olarak döner.
- **PATCH /api/address/updateAddress/{id}**: Belirtilen ID’ye sahip adresi günceller. Güncellenmiş adresi `AddressDTO` olarak döner.
- **DELETE /api/address/deleteAddress/{id}**: Verilen ID’ye göre adresi siler ve başarılı bir silme mesajı döner.

### Kullanılan Teknolojiler
- **Spring Boot**: Denetleyici (Controller) katmanının yönetimi ve HTTP isteklerinin işlenmesi için.
- **Lombok**: `@AllArgsConstructor` ile bağımlılıkları otomatik olarak enjekte eder.
- **ResponseEntity**: HTTP yanıt durumu ve gövdesini birlikte döner.

**Örnek İstek Gövdesi:**
- **Postman'de POST işlemi örneği**
```json
{
    "addressName": "Main Street",
    "addressNumber": "1903",
    "placeName": "Besiktas",
    "country": "Turkey",
    "zipCode": "34000",
    "additionalInfo": "Near the Inonu Stadium"
}
```