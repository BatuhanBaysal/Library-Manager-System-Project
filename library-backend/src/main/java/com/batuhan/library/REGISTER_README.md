# Register 

## CheckoutRegister Entity
`CheckoutRegister` sınıfı, bir kitap ödünç alma kaydını temsil eden bir varlıktır. Üyenin, kitabın ve ödünç alma işlemiyle ilgili bilgilerin tutulduğu bir veri modelidir. Bu sınıf, bir üyenin kitap ödünç alma kayıtlarını ve gecikme durumlarını izlemek için kullanılır.

### Alanlar
- **`id`** (`Long`): Ödünç alma kaydının benzersiz kimliği. Veritabanında otomatik olarak üretilir.
- **`member`** (`Member`): Ödünç alan üye bilgisi. `Member` sınıfıyla `@ManyToOne` ilişkisi kurulur. `member_id` veritabanında `CheckoutRegister` tablosunda bir sütun olarak saklanır. 
- **`book`** (`Book`): Ödünç alınan kitap bilgisi. `Book` sınıfıyla `@ManyToOne` ilişkisi kurulur. `book_id` veritabanında `CheckoutRegister` tablosunda bir sütun olarak saklanır.
- **`checkoutDate`** (`LocalDate`): Kitabın ödünç alındığı tarih. Boş bırakılamaz.
- **`dueDate`** (`LocalDate`): Kitabın iade edilmesi gereken son tarih. Boş bırakılamaz.
- **`returnDate`** (`LocalDate`): Kitabın iade edildiği tarih. Gecikme olup olmadığını belirlemek için kullanılabilir.
- **`overdueFine`** (`Double`): Gecikme nedeniyle uygulanacak ceza tutarı. `returnDate`, `dueDate`’i geçtiğinde ceza hesaplanabilir.

### Anotasyonlar
- **`@Entity`**: Bu sınıfın bir JPA varlığı olduğunu belirtir.
- **`@Table`**: Bu sınıf için veritabanında bir tablo oluşturur.
- **`@Id`**: `id` alanının birincil anahtar olduğunu belirtir.
- **`@GeneratedValue`**: `id` alanının otomatik olarak veritabanı tarafından üretileceğini belirtir.
- **`@ManyToOne` ve `@JoinColumn`**: `member` ve `book` alanları için diğer tablolarla ilişki kurar.

Bu yapı, kitap ödünç alma işlemlerini izlemek için gerekli tüm bilgileri barındırır ve ilişkilendirilmiş `Member` ve `Book` varlıklarıyla veritabanı ilişkilerini oluşturur.


## RegisterDTO Data Transfer Object (DTO)
`RegisterDTO` sınıfı, ödünç alma işlemiyle ilgili verileri taşımak için kullanılan bir DTO'dur. Bu sınıf, genellikle API taleplerinde ve yanıtlarında veri iletimi için kullanılır. `CheckoutRegister` varlığını temsil etmez, ancak ilgili bilgileri taşır ve gerektiğinde kullanılabilir.

### Alanlar
- **`id`** (`Long`): Kayıt kimliği. Bu alan, her ödünç alma kaydının benzersiz kimliğini temsil eder.
- **`memberId`** (`Long`): Üye kimliği. Bu, ödünç alan üyenin kimliğini taşır. `Member` varlığı ile ilişkili olan üye ID’sini içerir.
- **`bookId`** (`Long`): Kitap kimliği. Bu, ödünç alınan kitabın kimliğini temsil eder. `Book` varlığı ile ilişkili olan kitap ID’sini içerir.
- **`checkoutDate`** (`String`): Kitabın ödünç alındığı tarih. Bu tarih, `String` olarak saklanır ve genellikle `yyyy-MM-dd` formatında olur.
- **`dueDate`** (`String`): Kitabın iade edilmesi gereken tarih. Bu da `String` olarak saklanır ve yine genellikle `yyyy-MM-dd` formatında olur.
- **`returnDate`** (`String`): Kitabın iade edildiği tarih. Bu alan, iade işlemi gerçekleştiğinde doldurulur ve `String` formatında saklanır.
- **`overdueFine`** (`Double`): Gecikme nedeniyle uygulanacak ceza tutarı. Kitap geç teslim edilirse, ceza miktarını temsil eder.

### Kullanım Alanları
Bu DTO, genellikle veritabanı sorguları, API talepleri, ve yanıtlar arasında veri taşımak için kullanılır. `String` formatındaki tarih alanları, tarihleri manipüle etmeden kullanıcıya iletmek için kullanılır. Gerçek veritabanı etkileşimleri ve işlemlerinde, bu DTO'daki tarih alanları daha uygun türlere (örneğin, `LocalDate`) dönüştürülebilir.

### Anotasyonlar
- **`@Data`**: Lombok tarafından sağlanan bu anotasyon, getter, setter, `toString`, `equals` ve `hashCode` gibi metodları otomatik olarak oluşturur.

Bu DTO sınıfı, API istemcileriyle etkileşimde ve uygulama içindeki farklı katmanlarda veri iletiminde oldukça faydalıdır.


## RegisterMapper Sınıfı
`RegisterMapper`, `CheckoutRegister` varlıkları ile `RegisterDTO` arasında veri dönüşümü yapar.

### Metodlar

#### `mapToRegisterDTO(CheckoutRegister checkoutRegister)`
- `CheckoutRegister` varlığını `RegisterDTO`'ya dönüştürür.
- Veritabanındaki `Member` ve `Book` bilgilerini `DTO`'ya ekler.
- Tarihleri `String` formatında döndürür.

#### `mapToCheckoutRegistryEntity(RegisterDTO registerDTO)`
- `RegisterDTO` verisini `CheckoutRegister` varlığına dönüştürür.
- `Member` ve `Book` bilgilerini veritabanından alır.
- Tarihleri `LocalDate` formatına çevirir.

### Kullanım Alanları
- API'den alınan verileri varlıklara dönüştürüp veritabanı ile uyumlu hale getirir.
- Veritabanı işlemleri için veri aktarımını sağlar.


## CheckoutRegisterRepository Sınıfı
`CheckoutRegisterRepository`, `CheckoutRegister` varlığıyla ilişkili veritabanı işlemlerini sağlar. `JpaRepository`'yi extend ederek, temel CRUD işlemleri için hazır metodlar sunar.

### Metodlar

#### `findByMemberId(Long memberId)`
- `Member`'a ait tüm `CheckoutRegister` kayıtlarını döndürür.
- Verilen `memberId`'ye göre kayıtları filtreler.

#### `findByBookId(Long bookId)`
- `Book`'a ait tüm `CheckoutRegister` kayıtlarını döndürür.
- Verilen `bookId`'ye göre kayıtları filtreler.

### Kullanım Alanları
- Üye ve kitap bazlı olarak ödünç alma kayıtlarını sorgulamak için kullanılır.


## RegisterService Arayüzü
`RegisterService`, `CheckoutRegister` varlıklarıyla ilgili iş mantığını yöneten bir servis arayüzüdür. `RegisterDTO` veri transfer nesnesini kullanarak, ödünç alma işlemleriyle ilgili servis metodlarını tanımlar.

### Metodlar

#### `createRegister(RegisterDTO registerDTO)`
- Yeni bir `CheckoutRegister` kaydı oluşturur.
- Verilen `RegisterDTO` nesnesine göre kayıt oluşturur.

#### `getAllRegisters()`
- Tüm `CheckoutRegister` kayıtlarını döndürür.

#### `getRegisterById(Long id)`
- Verilen `id`'ye sahip `CheckoutRegister` kaydını döndürür.

#### `updateRegister(RegisterDTO registerDTO)`
- Var olan bir `CheckoutRegister` kaydını günceller.
- Verilen `RegisterDTO` nesnesine göre mevcut kaydı günceller.

#### `deleteRegister(Long id)`
- Verilen `id`'ye sahip `CheckoutRegister` kaydını siler.

#### `getRegisterByMemberId(Long memberId)`
- Verilen `memberId`'ye ait tüm `CheckoutRegister` kayıtlarını döndürür.

#### `getRegisterByBookId(Long bookId)`
- Verilen `bookId`'ye ait tüm `CheckoutRegister` kayıtlarını döndürür.

### Kullanım Alanları
- `CheckoutRegister` ile ilgili işlemleri yönetmek için servis metodları sağlanır.


## RegisterServiceImpl Sınıfı Açıklaması
`RegisterServiceImpl`, `RegisterService` arayüzünü implement eden ve ödünç alma işlemleriyle ilgili iş mantığını gerçekleştiren bir servis sınıfıdır.

### Özellikler ve İşlevler
- **loanPeriodInDays**: Bu özellik, her ödünç alma işlemi için geçerli olan ödünç süresini (gün cinsinden) belirtir.
- **overdueFineRate**: Bu özellik, gecikme faizi oranını temsil eder.

### Temel Metodlar

#### 1. `createRegister(RegisterDTO registerDTO)`
- Yeni bir `CheckoutRegister` kaydı oluşturur.
- Ödünç alma tarihine bağlı olarak gecikme tarihi hesaplanır.
- Kayıt başarıyla veritabanına kaydedilir ve `RegisterDTO` olarak döndürülür.

#### 2. `getAllRegisters()`
- Veritabanındaki tüm `CheckoutRegister` kayıtlarını getirir.
- Kayıtları `RegisterDTO`'ya dönüştürüp döndürür.

#### 3. `getRegisterById(Long id)`
- Verilen ID'ye sahip `CheckoutRegister` kaydını alır.
- Kayıt bulunamazsa, `ResourceNotFoundException` fırlatılır.

#### 4. `updateRegister(RegisterDTO registerDTO)`
- Mevcut bir `CheckoutRegister` kaydını günceller.
- Eğer geri dönüş tarihi belirtilmişse, gecikme faizi hesaplanır.

#### 5. `deleteRegister(Long id)`
- Verilen ID'ye sahip `CheckoutRegister` kaydını siler.
- Kayıt bulunamazsa, `ResourceNotFoundException` fırlatılır.

#### 6. `getRegisterByMemberId(Long memberId)`
- Verilen üyeye ait tüm ödünç alma kayıtlarını getirir.

#### 7. `getRegisterByBookId(Long bookId)`
- Verilen kitaba ait tüm ödünç alma kayıtlarını getirir.

### Yardımcı Metodlar

#### - `calculateOverDueFine(CheckoutRegister checkoutRegister)`
- Eğer `returnDate` geçerlidir ve teslim tarihi gecikmişse, gecikme faizi hesaplanır.

#### - `updateCheckoutRegisterFromDTO(CheckoutRegister checkoutRegisterToUpdate, RegisterDTO registerDTO)`
- `RegisterDTO`'dan alınan bilgilerle var olan `CheckoutRegister` kaydını günceller.

#### - `calculateDueDate(LocalDate checkoutDate)`
- Ödünç alma tarihine göre teslim tarihi hesaplar.

### Kullanım Alanı
Bu sınıf, ödünç alma işlemlerini yönetir, kayıtları oluşturur, günceller ve siler. Ayrıca gecikme faizi hesaplamaları gibi ek işlevler sağlar.


## RegisterController Açıklaması
`RegisterController`, RESTful API uç noktalarını sağlayarak, `RegisterService` ile etkileşime geçerek ödünç kayıtlarını yöneten bir denetleyicidir. Bu denetleyici, çeşitli CRUD işlemleri sunar.

### Temel Metodlar

#### 1. `createRegister(@RequestBody RegisterDTO registerDTO)`
- **Yöntem**: `POST`
- **URL**: `/api/registers/createRegister`
- **Açıklama**: Yeni bir ödünç kaydını oluşturur. `RegisterDTO` parametre olarak alır ve başarıyla kaydedilen kaydı döndürür.

#### 2. `getAllRegisters()`
- **Yöntem**: `GET`
- **URL**: `/api/registers/listAll`
- **Açıklama**: Veritabanındaki tüm ödünç kayıtlarını listeler.

#### 3. `getRegisterById(@PathVariable Long id)`
- **Yöntem**: `GET`
- **URL**: `/api/registers/{id}`
- **Açıklama**: Verilen ID'ye sahip ödünç kaydını alır. ID ile belirtilen kaydı döndürür.

#### 4. `updateRegister(@PathVariable Long id, @RequestBody RegisterDTO registerDTO)`
- **Yöntem**: `PATCH`
- **URL**: `/api/registers/updateRegister/{id}`
- **Açıklama**: Verilen ID'ye sahip ödünç kaydını günceller. Güncellenmiş kaydı döndürür.

#### 5. `deleteRegister(@PathVariable Long id)`
- **Yöntem**: `DELETE`
- **URL**: `/api/registers/deleteRegister/{id}`
- **Açıklama**: Verilen ID'ye sahip ödünç kaydını siler.

#### 6. `getRegistersByMember(@PathVariable Long memberId)`
- **Yöntem**: `GET`
- **URL**: `/api/registers/member/{memberId}`
- **Açıklama**: Verilen üyeye ait tüm ödünç kayıtlarını alır ve liste halinde döndürür.

#### 7. `getRegistersByBook(@PathVariable Long bookId)`
- **Yöntem**: `GET`
- **URL**: `/api/registers/book/{bookId}`
- **Açıklama**: Verilen kitaba ait tüm ödünç kayıtlarını alır ve liste halinde döndürür.

### Genel Açıklama
- Bu denetleyici sınıfı, ödünç alma kayıtlarını yönetmek için çeşitli API uç noktaları sağlar.
- CRUD işlemleri için HTTP istekleriyle etkileşime girer (CREATE, READ, UPDATE, DELETE).
- Uç noktalar, kullanıcıların üyeler veya kitaplar ile ilgili ödünç kayıtlarını kolayca oluşturmasını, görüntülemesini, güncellemesini ve silmesini sağlar.

### Kullanım Senaryoları
- **Kayıt Ekleme**: Yeni bir ödünç kaydı eklemek için `POST /api/registers/createRegister` kullanılabilir.
- **Kayıt Görüntüleme**: Tüm ödünç kayıtlarını görmek için `GET /api/registers/listAll` veya belirli bir ID'ye ait kaydı görmek için `GET /api/registers/{id}` kullanılabilir.
- **Kayıt Güncelleme**: Bir kaydı güncellemek için `PATCH /api/registers/updateRegister/{id}` kullanılabilir.
- **Kayıt Silme**: Bir kaydı silmek için `DELETE /api/registers/deleteRegister/{id}` kullanılabilir.

### Logger
- Her bir işlemde, işlemle ilgili bilgi veren loglar (`logger.info`) kullanılarak işlem detayları kaydedilir.