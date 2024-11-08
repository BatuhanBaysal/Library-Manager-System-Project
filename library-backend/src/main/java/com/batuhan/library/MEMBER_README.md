# Member

## Member Entity
`Member` sınıfı, kütüphane üyelerini temsil eden bir JPA entity'sidir ve `members` tablosuna karşılık gelir.

### Alanlar
- **id**: Üyenin birincil anahtarı (Long).
- **firstName**: Üyenin adı (String).
- **lastName**: Üyenin soyadı (String).
- **dateOfBirth**: Doğum tarihi (LocalDate).
- **address**: Üyenin adres bilgisi, `Address` entity'si ile ilişkilidir.
- **email**: Üyenin e-posta adresi (String).
- **phone**: Üyenin telefon numarası (String).
- **membershipStarted**: Üyelik başlangıç tarihi (LocalDate).
- **membershipEnded**: Üyelik bitiş tarihi (LocalDate, opsiyonel).
- **isActive**: Üyelik durumu (Boolean, varsayılan `true`).
- **barcodeNumber**: Üyenin benzersiz barkod numarası (String).

### Amaç
Bu sınıf, kütüphane üyelerinin bilgilerini veritabanında saklamak için kullanılır.


## MemberDTO
`MemberDTO` sınıfı, üye bilgilerini istemci ile sunucu arasında veri transferi için kullanılan veri taşıyıcı nesnedir.

### Alanlar
- **id**: Üyenin benzersiz kimliği (Long).
- **firstName**: Üyenin adı (String).
- **lastName**: Üyenin soyadı (String).
- **dateOfBirth**: Üyenin doğum tarihi (String).
- **address**: `AddressDTO` ile adres bilgisi.
- **email**: Üyenin e-posta adresi (String).
- **phone**: Üyenin telefon numarası (String).
- **membershipStarted**: Üyelik başlangıç tarihi (String).
- **membershipEnded**: Üyelik bitiş tarihi (String, opsiyonel).
- **isActive**: Üyelik aktif durumu (Boolean).
- **barcodeNumber**: Üyenin benzersiz barkod numarası (String).

### Amaç
`MemberDTO`, `Member` varlık nesnesini doğrudan kullanmak yerine istemciye veya istemciden gelen veriler için kullanılır.


## MemberMapper
`MemberMapper`, `Member` varlık nesnesi ile `MemberDTO` veri taşıyıcı nesnesi arasındaki dönüşümleri sağlar.

### Yöntemler
1. **mapToMemberDTO(Member member)**:
   - `Member` nesnesini `MemberDTO` nesnesine dönüştürür.
   - `LocalDate` verilerini `String` formatına çevirir.
   - `AddressMapper` kullanarak adres bilgisini dönüştürür.

2. **mapToMemberEntity(MemberDTO memberDTO)**:
   - `MemberDTO` nesnesini `Member` nesnesine dönüştürür.
   - `String` formatındaki tarihleri `LocalDate` tipine çevirir.
   - `AddressMapper` ile adresi `Member` varlık nesnesine dönüştürür.

### Kullanımı
- Veriyi DTO formatına ya da varlık nesnesine çevirmek için bu yöntemler kullanılır.


## MemberRepository
`MemberRepository`, `Member` varlık sınıfını yönetmek için kullanılan bir JPA deposudur.

### Özellikler
- **JpaRepository<Member, Long>**:
  - `Member` varlıkları üzerinde CRUD işlemlerini sağlar.
  - `Long`, `Member` varlığının birincil anahtarının veri tipidir.

### Kullanımı
- Üyeler (`Member`) üzerinde veritabanı işlemleri gerçekleştirmek için `MemberRepository` kullanılır.


## MemberService
`MemberService`, üye yönetimiyle ilgili iş mantığını tanımlar.

### Metodlar
- **addMember(MemberDTO memberDTO)**: Yeni bir üye ekler.
- **getAllMembers()**: Tüm üyeleri getirir.
- **getMemberById(Long id)**: Belirtilen ID'ye göre üye getirir.
- **updateMember(MemberDTO memberDTO)**: Üye bilgilerini günceller.
- **deleteMember(Long id)**: Belirtilen ID'ye göre üyeyi siler.
- **findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber)**: Belirli kriterlere göre üyeleri arar.


## MemberServiceImpl
`MemberServiceImpl` sınıfı, üye yönetimi iş mantığını sağlar. CRUD işlemleri ve belirli kriterlere göre üye arama işlevlerini içerir.

### Metodlar
- **`addMember(MemberDTO memberDTO)`**: Yeni bir üye ekler. Üyenin adresi varsa önce adresi kaydeder, ardından üye bilgilerini veritabanına ekler.
- **`getAllMembers()`**: Tüm üyeleri `MemberDTO` listesi olarak döner.
- **`getMemberById(Long id)`**: Belirtilen ID’ye sahip üyeyi getirir. Üye bulunamazsa `ResourceNotFoundException` fırlatır.
- **`updateMember(MemberDTO memberDTO)`**: Üye bilgilerini günceller. Güncelleme işlemi sırasında üyenin adresi varsa adres güncellenir ya da yeni bir adres eklenir. 
- **`deleteMember(Long id)`**: Belirtilen ID’ye sahip üyeyi siler. Üye yoksa `ResourceNotFoundException` fırlatır.
- **`findMembersByCriteria(Long id, String firstName, String lastName, String barcodeNumber)`**: Belirtilen kriterlere göre üyeleri arar ve sonuçları `MemberDTO` listesi olarak döner.

### Yardımcı Metod
- **`updateMemberEntityFromDTO(Member memberToUpdate, MemberDTO memberDTO)`**: Bir `Member` nesnesini, `MemberDTO`'daki verilere göre günceller. Adres bilgisi varsa, adres güncelleme işlemini `AddressServiceImpl` üzerinden gerçekleştirir.

### Notlar
- **Logger Kullanımı**: Sınıf, işlem adımlarını kaydetmek için `Logger` kullanır.
- **EntityManager**: Kriter tabanlı sorgulamalar için `EntityManager` ile `CriteriaBuilder` kullanılır.
- **Transactional**: `@Transactional` anotasyonu ile ekleme ve güncelleme işlemlerinin atomikliği sağlanır.


## MemberController
`MemberController` sınıfı, `MemberService` sınıfına HTTP istekleriyle erişim sağlar. Üye ekleme, listeleme, arama, güncelleme ve silme işlemlerini gerçekleştiren REST API uç noktaları içerir.

### Uç Noktalar
- **`addMember(MemberDTO memberDTO)`**
  - **URL**: `/api/members/addMember`
  - **HTTP Yöntemi**: POST
  - **Açıklama**: Yeni bir üye ekler. `MemberDTO` nesnesini isteğin gövdesinde kabul eder.
  - **Yanıt**: Eklenen üyenin `MemberDTO` nesnesi ile yanıt döner, HTTP 201 (CREATED) durumu ile birlikte.

- **`getAllMembers()`**
  - **URL**: `/api/members/listAll`
  - **HTTP Yöntemi**: GET
  - **Açıklama**: Tüm üyeleri listeler.
  - **Yanıt**: Üyelerin `MemberDTO` listesini döner, HTTP 200 (OK) durumu ile birlikte.

- **`getMemberById(Long id)`**
  - **URL**: `/api/members/{id}`
  - **HTTP Yöntemi**: GET
  - **Açıklama**: Belirtilen ID’ye sahip üyeyi getirir.
  - **Yanıt**: Üyenin `MemberDTO` nesnesini döner, HTTP 200 (OK) durumu ile birlikte.

- **`updateMemberById(Long id, MemberDTO memberDTO)`**
  - **URL**: `/api/members/updateMember/{id}`
  - **HTTP Yöntemi**: PATCH
  - **Açıklama**: Belirtilen ID’ye sahip üyeyi günceller. `MemberDTO` nesnesini isteğin gövdesinde kabul eder.
  - **Yanıt**: Güncellenen üyenin `MemberDTO` nesnesini döner, HTTP 200 (OK) durumu ile birlikte.

- **`deleteMemberById(Long id)`**
  - **URL**: `/api/members/deleteMember/{id}`
  - **HTTP Yöntemi**: DELETE
  - **Açıklama**: Belirtilen ID’ye sahip üyeyi siler.
  - **Yanıt**: Silme işlemi başarılıysa, başarılı bir silme mesajı ile HTTP 200 (OK) durumu döner.

- **`searchMembers(Long cardNumber, String firstName, String lastName, String barcodeNumber)`**
  - **URL**: `/api/members/search`
  - **HTTP Yöntemi**: GET
  - **Açıklama**: Kriterlere göre üyeleri arar. Arama parametreleri opsiyoneldir.
  - **Yanıt**: Eşleşen üyelerin `MemberDTO` listesini döner, HTTP 200 (OK) durumu ile birlikte.

### Notlar
- **Logger Kullanımı**: Her işlem adımını kaydetmek için `Logger` kullanılır.
- **ResponseEntity**: HTTP durum kodları ve yanıt verileri, `ResponseEntity` ile döndürülür.
- **`@AllArgsConstructor` Kullanımı**: Lombok’un `@AllArgsConstructor` anotasyonu ile sınıfın constructor’ı otomatik olarak oluşturulur.