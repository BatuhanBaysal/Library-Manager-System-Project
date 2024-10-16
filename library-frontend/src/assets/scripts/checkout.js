document.addEventListener('DOMContentLoaded', function () {

    // these two functions are to pre-fill the input

    // Function to extract query parameters from the URL
    function getQueryParam(param) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }

    // Attempt to pre-fill the memberBarcode input if the parameter is present
    const memberBarcodeParam = getQueryParam('memberBarcode');
    if (memberBarcodeParam) {
        document.getElementById('memberBarcode').value = memberBarcodeParam;
    }
    // Attempt to pre-fill the bookBarcode input if the parameter is present
    const bookBarcodeParam = getQueryParam('bookBarcode');
    if (bookBarcodeParam) {
        document.getElementById('bookBarcode').value = bookBarcodeParam;
    }

    const form = document.querySelector('form');

    form.addEventListener('submit', function (e) {
        e.preventDefault(); // Prevent default form submission

        const memberBarcode = document.getElementById('memberBarcode').value;
        const bookBarcode = document.getElementById('bookBarcode').value;

        Promise.all([
            fetchMemberIdByBarcode(memberBarcode),
            fetchBookIdByBarcode(bookBarcode)
        ])
            .then(([memberId, bookId]) => {
                if (memberId && bookId) {
                    createCheckoutRegister(memberId, bookId);
                } else {
                    alert('Invalid member or book barcode.');
                }
            })
            .catch(error => {
                console.error('Error during checkout process:', error);
                alert(error.message);
            });
    });
});


function fetchMemberIdByBarcode(barcode) {
    return fetch(`http://localhost:8080/api/members/search?barcodeNumber=${barcode}`)
        .then(response => response.json())
        .then(data => {
            console.log('Member data:', data);
            if (data.length > 0) return data[0].id;
        });
}


function fetchBookIdByBarcode(barcode) {
    return fetch(`http://localhost:8080/api/books/search?barcodeNumber=${barcode}`)
        .then(response => response.json())
        .then(data => {
            console.log('Book data:', data);
            // checking for book availability
            if (data.length > 0) {
                const book = data[0];
                return book.id; // Return the book ID if there are available copies
            }
        });
}


function createCheckoutRegister(memberId, bookId) {
    // First, check the availability of the book
    fetch(`http://localhost:8080/api/books/${bookId}`) // Assuming this endpoint fetches book details
        .then(response => response.json())
        .then(book => {
            if (!book || book.noOfAvailableCopies <= 0) {
                // If no copies are available, throw an error before attempting to create a checkout register
                throw new Error('Checkout failed: no copies available or book not found.');
            }
            // Proceed to create a checkout register since the book is available
            const payload = {
                memberId: memberId,
                bookId: bookId,
                checkoutDate: new Date().toISOString().split('T')[0]
            };
            return fetch('http://localhost:8080/api/registers/createRegister', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload)
            });
        })
        .then(response => {
            if (response && !response.ok) {
                throw new Error('Failed to create checkout register.');
            }
            return response.json();
        })
        .then(data => {
            // Successfully created the checkout register, now decrease the book copies
            return decreaseBookCopies(bookId);
        })
        .then(() => {
            alert('Book checked out successfully and copies updated.');
            window.history.back(); // Navigate back to the previous page
        })
        .catch(error => {
            console.error('Error in checkout process:', error);
            alert(error.message); // Display error message to the user
        });
}


function decreaseBookCopies(bookId) {
    // Fetch book details again to ensure we have the latest copy count
    return fetch(`http://localhost:8080/api/books/${bookId}`)
        .then(response => response.json())
        .then(book => {
            const updatedCopies = book.noOfAvailableCopies - 1;
            // Update the book's available copies
            return fetch(`http://localhost:8080/api/books/updateBook/${bookId}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ noOfAvailableCopies: updatedCopies })
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update book copies.');
            }
            return response.json();
        });
}