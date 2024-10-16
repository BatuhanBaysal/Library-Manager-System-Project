document.addEventListener('DOMContentLoaded', () => {
    const bookId = new URLSearchParams(window.location.search).get('id');
    if (!bookId) {
      console.error('No book ID specified in the URL');
      return;
    }
    
    document.querySelector('h1').textContent = `Edit Book #${bookId}`;
    
    fetchBookData(bookId);
});


function fetchBookData(bookId) {
    fetch(`http://localhost:8080/api/books/${bookId}`)
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch book data');
            return response.json();
        })
        .then(book => {
            console.log("book info:", book);
            document.getElementById('title').value = book.title;
            document.getElementById('author').value = book.author;
            document.getElementById('isbn').value = book.isbn;
            document.getElementById('publisher').value = book.publisher;
            document.getElementById('yearPublished').value = book.yearOfPublication;
            document.getElementById('placePublished').value = book.placeOfPublication;
            document.getElementById('noOfAvailableCopies').value = book.noOfAvailableCopies;
            document.getElementById('barcodeNumber').value = book.barcodeNumber;
        })
        .catch(error => {
            console.error('Error fetching book data:', error);
        });
}

document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    const bookId = new URLSearchParams(window.location.search).get('id');
    const formData = {
        title: document.getElementById('title').value,
        author: document.getElementById('author').value,
        isbn: document.getElementById('isbn').value,
        publisher: document.getElementById('publisher').value,
        yearOfPublication: document.getElementById('yearPublished').value,
        placeOfPublication: document.getElementById('placePublished').value,
        noOfAvailableCopies: document.getElementById('noOfAvailableCopies').value,
        barcodeNumber: document.getElementById('barcodeNumber').value
    };
    
    updateBookData(bookId, formData);
});


function updateBookData(bookId, data) {
    fetch(`http://localhost:8080/api/books/updateBook/${bookId}`, {
        method: 'PATCH', // Assuming your API supports PATCH for partial updates
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) throw new Error('Failed to update book data');
        alert('Book data updated successfully');
        // Redirect or perform another action as needed, like refreshing the page
    })
    .catch(error => {
        console.error('Error updating book data:', error);
        alert('Failed to update book data');
    });
}