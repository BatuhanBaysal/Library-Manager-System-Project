# Book

## Book Entity
`Book` sınıfı, bir kitap varlığını temsil eder ve veritabanındaki `books` tablosuna karşılık gelir. Bu sınıf, kitap ile ilgili temel bilgileri içeren alanları içerir.

### Anotasyonlar
- **@Entity**: Bu sınıfın bir JPA varlığı olduğunu belirtir, yani veritabanında bir tabloya karşılık gelir.
- **@Table(name = "books")**: Bu varlık için veritabanında hangi tablo adının kullanılacağını belirtir. Burada tablo adı `books` olarak tanımlanmıştır.
- **@Data**: Lombok tarafından sağlanır ve `@AllArgsConstructor`, `@NoArgsConstructor`, `@Getter`, `@Setter`, `@ToString`, ve `@EqualsAndHashCode` gibi anotasyonların işlevlerini topluca sağlar.

### Alanlar
- **@Id**: `id` alanının birincil anahtar olduğunu belirtir.
- **@GeneratedValue(strategy = GenerationType.IDENTITY)**: `id` alanının otomatik olarak artan bir kimlik değeri olacağını belirtir.
- **@Column(nullable = false)**: İlgili alanın veritabanında `null` olamayacağını belirtir.

### Alanlar ve Açıklamaları
- **id** (`Long`): Kitabın benzersiz kimlik numarası.
- **title** (`String`): Kitabın başlığı.
- **author** (`String`): Kitabın yazarı.
- **isbn** (`String`): Kitabın ISBN numarası.
- **publisher** (`String`): Kitabı yayımlayan kuruluş veya kişi.
- **yearOfPublication** (`Integer`): Kitabın yayımlandığı yıl.
- **placeOfPublication** (`String`): Kitabın yayımlandığı yer.
- **noOfAvailableCopies** (`Integer`): Mevcut kopya sayısı.
- **barcodeNumber** (`String`): Kitabın barkod numarası.


## BookDTO Sınıfı
`BookDTO`, kitap bilgilerini taşıyan bir veri transfer nesnesidir ve istemci ile sunucu arasındaki veri alışverişinde kullanılır. `Book` varlık sınıfının alanlarını içermekle birlikte, veritabanı işlemlerine bağımlı olmadan verileri taşımak için tasarlanmıştır.

### Anotasyonlar
- **@Data**: Lombok tarafından sağlanır ve `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, ve `@RequiredArgsConstructor` gibi anotasyonların işlevlerini topluca sağlar. Bu sayede sınıfın getter ve setter metotları otomatik olarak oluşturulur.

### Alanlar ve Açıklamaları
- **id** (`Long`): Kitabın benzersiz kimlik numarası.
- **title** (`String`): Kitabın başlığı.
- **author** (`String`): Kitabın yazarı.
- **isbn** (`String`): Kitabın ISBN numarası.
- **publisher** (`String`): Kitabı yayımlayan kuruluş veya kişi.
- **yearOfPublication** (`Integer`): Kitabın yayımlandığı yıl.
- **placeOfPublication** (`String`): Kitabın yayımlandığı yer.
- **noOfAvailableCopies** (`Integer`): Mevcut kopya sayısı.
- **barcodeNumber** (`String`): Kitabın barkod numarası.

### Kullanım Amacı
`BookDTO`, istemci ile sunucu arasındaki veri aktarımında, kitap verilerini taşımak için kullanılır. Bu sayede `Book` varlık sınıfına bağımlılık olmadan yalnızca ihtiyaç duyulan veriler taşınabilir.


## BookMapper Sınıfı
**BookMapper** sınıfı, `Book` (varlık) ve `BookDTO` (veri transfer nesnesi) arasında dönüştürme işlemlerini sağlar. Bu dönüşüm, uygulamanın veri katmanı ve iş katmanı arasındaki veri transferini kolaylaştırarak daha temiz ve bakımı kolay bir yapı oluşturur.

### BookMapper Sınıfının Metotları
- **`mapToBookDTO(Book book)`**: `Book` varlık nesnesini `BookDTO` nesnesine dönüştürür. Veritabanından alınan `Book` nesnesinin istemciye `BookDTO` olarak gönderilmesini sağlar.
- **`mapToBookEntity(BookDTO bookDTO)`**: `BookDTO` nesnesini `Book` varlık nesnesine dönüştürür. İstemciden alınan `BookDTO` nesnesinin `Book` varlık nesnesi olarak veritabanına kaydedilmesine olanak tanır.

### Kullanım Amacı
`BookMapper` sınıfı, `Book` ve `BookDTO` arasında merkezi bir dönüştürme mekanizması sunar. Bu, uygulamanın veri katmanı ve iş katmanı arasındaki geçişlerde tutarlı bir veri aktarımı sağlar ve aynı zamanda kod tekrarını azaltarak veri transferini daha düzenli hale getirir. Bu yapı sayesinde, iş mantığı ile veri katmanının ayrılması daha etkin bir şekilde sağlanır.


## BookRepository Arayüzü
**BookRepository** arayüzü, `Book` varlığı için veri erişim işlemlerini sağlar. Spring Data JPA kullanılarak oluşturulan bu arayüz, CRUD işlemlerinin yanı sıra özelleştirilmiş sorgular ile daha gelişmiş veri sorgulama yetenekleri sunar.

### Kullanılan Anotasyonlar ve Arayüzler
- **`@Repository`**: Bu arayüzün bir veri erişim katmanı olduğunu belirtir. Spring, bu arayüzü bir **Spring Bean** olarak yönetir ve çalışma zamanında veri erişim hatalarını işlemek için kullanır.
- **`JpaRepository<Book, Long>`**: `BookRepository` arayüzü, `JpaRepository` arayüzünü genişleterek temel CRUD işlemlerini sağlar. Bu arayüzde, `Book` varlığı için `Long` türünde bir kimlik (ID) kullanılır.

### Özelleştirilmiş Sorgu Metotları
- **`List<Book> findByTitleContainingIgnoreCase(String title)`**: `title` değeri içeren (büyük/küçük harf duyarsız) tüm kitapları döner.
- **`List<Book> findByTitleAndAuthor(String title, String author)`**: Belirtilen `title` ve `author` değerlerine sahip kitapları döner.

### Kullanım Amacı
`BookRepository`, **veritabanı ile etkileşimi soyutlayarak** veri katmanı ve iş katmanı arasında bir köprü görevi görür. Özelleştirilmiş sorgular, belirli koşullara göre verilerin daha esnek bir şekilde sorgulanmasını sağlar. Bu yapı, veri erişim işlemlerini daha okunabilir ve modüler hale getirir.


## BookService Arayüzü
**BookService** arayüzü, `BookDTO` nesneleri ile uygulamanın iş mantığı katmanında gerçekleştirilecek işlemleri tanımlar. Bu arayüz, kitap ekleme, güncelleme, silme ve çeşitli kriterlere göre arama gibi işlevleri içerir.

### Metotlar
- **`BookDTO addBook(BookDTO bookDTO)`**: Yeni bir kitap ekler ve eklenen `BookDTO` nesnesini döner.
- **`List<BookDTO> getAllBooks()`**: Tüm kitapları dönen bir `BookDTO` listesi sağlar.
- **`BookDTO getBookById(Long bookId)`**: Verilen ID’ye göre bir kitap döner.
- **`BookDTO updateBook(BookDTO bookDTO)`**: Verilen `BookDTO` nesnesi ile kitabı günceller ve güncellenmiş `BookDTO` nesnesini döner.
- **`void deleteBook(Long bookId)`**: Verilen ID’ye göre kitabı siler.
- **`List<BookDTO> findBooksByTitle(String title)`**: Verilen başlığa göre kitapları dönen bir `BookDTO` listesi sağlar.
- **`List<BookDTO> findBooksByTitleAndAuthor(String title, String author)`**: Belirtilen başlık ve yazara göre kitapları döner.
- **`List<BookDTO> findBooksByCriteria(String title, String author, String isbn, String barcodeNumber)`**: Başlık, yazar, ISBN veya barkod numarasına göre kitapları döner.

### Kullanım Amacı
`BookService`, **uygulamanın iş mantığını veri erişim katmanından ayırmak** amacıyla kullanılır. Bu arayüz, `BookRepository` arayüzü üzerinden veri tabanına erişirken uygulamanın iş katmanında gerçekleştirilmesi gereken işlemleri kapsar. Ayrıca, bu yapı sayesinde kod daha okunabilir, test edilebilir ve bakımı kolay hale gelir.


### BookServiceImpl Sınıfı
`BookServiceImpl` sınıfı, `BookService` arayüzünü implement ederek kitap ekleme, güncelleme, silme ve arama gibi işlemleri gerçekleştirir. Bu sınıf, `BookRepository` ve `EntityManager` ile veri tabanı işlemlerini yaparken `BookMapper` aracılığıyla `Book` varlıkları ile `BookDTO` nesneleri arasında dönüşüm sağlar. Ayrıca, `Logger` ile çeşitli aşamalarda loglama işlemi yaparak işlem takibi sağlar.

## BookServiceImpl Sınıfındaki Metotlar
- **addBook(BookDTO bookDTO)**: Yeni bir kitap ekler. `BookDTO`'yu `Book` varlık nesnesine dönüştürerek veri tabanına kaydeder ve döner.
- **getAllBooks()**: Tüm kitapları `BookDTO` listesi olarak döner.
- **getBookById(Long bookId)**: Verilen ID'ye göre kitabı getirir, eğer kitap yoksa `ResourceNotFoundException` fırlatır.
- **updateBook(BookDTO bookDTO)**: Var olan bir kitabı günceller. Kitap bulunmazsa `ResourceNotFoundException` fırlatır.
- **deleteBook(Long bookId)**: Verilen ID'ye göre kitabı siler, kitap yoksa `ResourceNotFoundException` fırlatır.
- **findBooksByTitle(String title)**: Başlığa göre kitapları arar.
- **findBooksByTitleAndAuthor(String title, String author)**: Başlık ve yazara göre kitapları arar.
- **findBooksByCriteria(String title, String author, String isbn, String barcodeNumber)**: Çeşitli kriterlere göre kitap arar ve `CriteriaBuilder` ile dinamik sorgu oluşturur.

### Yardımcı Metotlar
- **updateBookEntityFromDTO(Book book, BookDTO bookDTO)**: Güncelleme sırasında `BookDTO` içindeki mevcut verilerle `Book` varlık nesnesini günceller.

### Kullanım Amacı
Bu yapı, uygulamanın veri erişim katmanı ile iş mantığını ayırarak daha modüler ve okunabilir hale getirir. Özellikle `CriteriaBuilder` kullanılarak yapılan dinamik arama işlemleri, esnek sorgulama imkanı sunar. `BookServiceImpl`, merkezi bir iş mantığı katmanı sunarak veritabanı ve veri aktarımı süreçlerinde tutarlılığı ve kod tekrarının önlenmesini sağlar.


## BookController Sınıfı
`BookController` sınıfı, kitaplar ile ilgili HTTP isteklerini yöneten RESTful bir kontrolördür. `BookService` aracılığıyla kitap ekleme, güncelleme, silme ve arama gibi işlemleri gerçekleştiren metodlar içerir. `Logger` kullanarak önemli aşamalarda loglama işlemi yapar.

### BookController Sınıfındaki Metotlar
- **addBook(@RequestBody BookDTO bookDTO)**: 
  - URL: `http://localhost:8080/api/books/addBook`
  - HTTP Method: POST
  - Açıklama: Yeni bir kitabı ekler ve kaydedilen `BookDTO` nesnesini döner.
  
- **getAllBooks()**:
  - URL: `http://localhost:8080/api/books/listAll`
  - HTTP Method: GET
  - Açıklama: Tüm kitapları listeler ve `BookDTO` listesi olarak döner.

- **getBookById(@PathVariable("id") Long bookId)**:
  - URL: `http://localhost:8080/api/books/{id}`
  - HTTP Method: GET
  - Açıklama: Verilen ID'ye göre kitabı getirir.

- **updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO)**:
  - URL: `http://localhost:8080/api/books/updateBook/{id}`
  - HTTP Method: PATCH
  - Açıklama: Var olan bir kitabı günceller ve güncellenmiş `BookDTO` nesnesini döner.

- **deleteBook(@PathVariable Long id)**:
  - URL: `http://localhost:8080/api/books/deleteBook/{id}`
  - HTTP Method: DELETE
  - Açıklama: Verilen ID'ye göre kitabı siler.

- **searchBooksByTitle(@RequestParam String title)**:
  - URL: `http://localhost:8080/api/books/search-title?title=Lord of the Rings`
  - HTTP Method: GET
  - Açıklama: Başlığa göre kitapları arar ve sonuçları döner.

- **searchBooksByTitleAndAuthor(@RequestParam String title, @RequestParam String author)**:
  - URL: `http://localhost:8080/api/books/search-title-author?title=Lord of the Rings&author=J.R.R. Tolkien`
  - HTTP Method: GET
  - Açıklama: Başlık ve yazara göre kitapları arar.

- **searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) String isbn, @RequestParam(required = false) String barcodeNumber)**:
  - URL: `http://localhost:8080/api/books/search?title=Lord&author=Tolki&isbn=1234&barcodeNumber=1234`
  - HTTP Method: GET
  - Açıklama: Belirtilen kriterlere göre kitapları arar ve sonuçları döner.

### Kullanım Amacı
Bu sınıf, `BookService`'in sunduğu işlevleri HTTP istekleri aracılığıyla erişilebilir hale getirir. RESTful bir API sunarak istemcilerin kitapları eklemesine, güncellemesine, silmesine ve aramasına olanak tanır. Ayrıca, `Logger` ile uygulama içinde yaşanan önemli olayları kaydederek hata ayıklamayı kolaylaştırır.