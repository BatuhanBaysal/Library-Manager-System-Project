let bookId = null; // Declare bookId globally

document.addEventListener('DOMContentLoaded', function () {
    bookId = getQueryParam('id');
    if (!bookId) {
        console.error('No book ID specified in the URL');
        return;
    }

    const url = `http://localhost:8080/api/books/${bookId}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(book => {
            displayBookDetails(book);
            updateLinkVisibility(book.noOfAvailableCopies); // Update visibility of links based on available copies
            // Set checkout link
            const checkoutLink = document.getElementById('checkoutLink');
            if (checkoutLink) {
                checkoutLink.href = `book-checkout.html?bookBarcode=${book.barcodeNumber}`;
            }
        })
        .catch(error => {
            console.error('Error fetching book details:', error);
        });

    const removeCopyLink = document.getElementById('removeCopyLink');
    if (removeCopyLink) {
        removeCopyLink.addEventListener('click', function () {
            confirmAndRemoveCopy();
        });
    }

    const addCopyLink = document.getElementById('addCopyLink');
    if (addCopyLink) {
        addCopyLink.addEventListener('click', function () {
            addBookCopy();
        });
    }

    // Setup the "Current Holders" link click event listener
    const currentHoldersLink = document.getElementById('currentHoldersLink');
    if (currentHoldersLink) {
        currentHoldersLink.addEventListener('click', function (e) {
            e.preventDefault(); // Prevent default action of the link
            fetchCurrentHolders(bookId); // Delay fetch to ensure page is reloaded
        });
    }

    // Setup the "Checkout History" link click event listener
    const checkoutHistoryLink = document.getElementById('checkoutHistoryLink');
    if (checkoutHistoryLink) {
        checkoutHistoryLink.addEventListener('click', function (e) {
            e.preventDefault(); // Prevent default action of the link
            fetchCheckoutHistory(bookId); // Delay fetch to ensure page is reloaded
        });
    }

    // Update the Edit Book link's href attribute with the book's ID
    updateEditBookLink(bookId);
});

function confirmAndRemoveCopy() {
    if (confirm('Are you sure you want to remove a copy?')) {
        decreaseBookCopies();
    }
}


function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}


function displayBookDetails(book) {
    console.log("Book: ", book);
    // Assuming the structure of your book object matches the given details
    const detailElements = document.querySelectorAll('table td:nth-child(2)');
    detailElements[0].textContent = book.isbn || 'N/A';
    detailElements[1].textContent = book.publisher || 'N/A';
    detailElements[2].textContent = book.yearOfPublication || 'N/A';
    detailElements[3].textContent = book.placeOfPublication || 'N/A';
    detailElements[4].textContent = book.noOfAvailableCopies.toString() || 'N/A';

    document.querySelector('h1').textContent = `Book Info: ${book.title}, ${book.author}`;
}


function updateLinkVisibility(availableCopies) {
    const checkoutLink = document.getElementById('checkoutLink');
    const removeCopyLink = document.getElementById('removeCopyLink');

    if (availableCopies <= 0) {
        if (checkoutLink) checkoutLink.style.display = 'none';
        if (removeCopyLink) removeCopyLink.style.display = 'none';
    } else {
        if (checkoutLink) checkoutLink.style.display = '';
        if (removeCopyLink) removeCopyLink.style.display = '';
    }
}

// The functions decreaseBookCopies and addBookCopy remain unchanged from your original script.
function decreaseBookCopies() {
    // Fetch the current book details to get the current number of available copies
    const getBookDetailsUrl = `http://localhost:8080/api/books/${bookId}`; // Ensure bookId is correctly obtained

    fetch(getBookDetailsUrl)
        .then(response => response.json())
        .then(book => {
            // Calculate the new number of available copies by decrementing by one
            const updatedCopies = book.noOfAvailableCopies - 1;

            // Prepare the URL for the PATCH request to update the book
            const updateBookUrl = `http://localhost:8080/api/books/updateBook/${bookId}`;

            // Send the PATCH request with the updated number of available copies
            return fetch(updateBookUrl, {
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
        })
        .then(updatedBook => {
            alert('A copy has been successfully removed.');
            // Refresh the page to reflect the change
            location.reload();
        })
        .catch(error => {
            console.error('Error removing a copy:', error);
            alert('Failed to remove a copy. Please try again.');
        });
}


function addBookCopy() {
    // Fetch the current book details to get the current number of available copies
    const getBookDetailsUrl = `http://localhost:8080/api/books/${bookId}`; // Ensure bookId is correctly obtained

    fetch(getBookDetailsUrl)
        .then(response => response.json())
        .then(book => {
            // Calculate the new number of available copies by incrementing by one
            const updatedCopies = book.noOfAvailableCopies + 1;

            // Prepare the URL for the PATCH request to update the book
            const updateBookUrl = `http://localhost:8080/api/books/updateBook/${bookId}`;

            // Send the PATCH request with the updated number of available copies
            return fetch(updateBookUrl, {
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
        })
        .then(updatedBook => {
            alert('A copy has been successfully added.');
            // Refresh the page to reflect the change
            location.reload();
        })
        .catch(error => {
            console.error('Error adding a copy:', error);
            alert('Failed to add a copy. Please try again.');
        });
}

// Function to fetch current holders of the book
function fetchCurrentHolders(bookId) {
    console.log("Fetching current holders for bookId:", bookId);
    fetch(`http://localhost:8080/api/registers/book/${bookId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch current holders.');
            }
            return response.json();
        })
        .then(registers => {
            console.log("registers: ", registers);
            // Filter registers to include only those where returnDate is null
            const currentHolders = registers.filter(register => register.returnDate === null);

            // Use the member IDs from the filtered registers to fetch member details
            const memberDetailsPromises = currentHolders.map(register =>
                fetch(`http://localhost:8080/api/members/${register.memberId}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Failed to fetch member details');
                        }
                        return response.json();
                    })
                    .then(member => ({
                        ...member,
                        checkoutDate: register.checkoutDate,
                        dueDate: register.dueDate
                    }))
            );

            return Promise.all(memberDetailsPromises);
        })
        .then(membersWithDetails => {
            // Display the fetched members with details
            displayCurrentHolders(membersWithDetails);
        })
        .catch(error => {
            console.error('Error fetching current holders:', error);
        });
}

// Function to display current holders information
function displayCurrentHolders(members) {
    // Initialize with either the table headers or a message for no current holders
    let html = '<p>No current holders.</p>';
    console.log("members: ", members);
    console.log("members length: ", members.length);
    if (members.length > 0) {
        html = '<h2 class="h2-no-line">Current Holders</h2><table class="results-table"><thead><tr><th>First Name</th><th>Last Name</th><th>Card #</th><th>Checkout Date</th><th>Due Date</th></tr></thead><tbody>';
    }
    members.forEach(member => {
        // Format dates
        const checkoutDate = member.checkoutDate ? new Date(member.checkoutDate).toLocaleDateString() : 'N/A';
        const dueDate = member.dueDate ? new Date(member.dueDate).toLocaleDateString() : 'N/A';

        // For each member, add a row to the table
        html += `<tr>
                    <td>${member.firstName}</td>
                    <td>${member.lastName}</td>
                    <td>${member.id}</td>
                    <td>${checkoutDate}</td>
                    <td>${dueDate}</td>
                 </tr>`;
    });
    if (members.length > 0) {
        html += '</tbody></table>';
    }

    // Assuming there is a container in your HTML to display this information
    const currentHoldersContainer = document.getElementById('currentHoldersContainer');
    if (currentHoldersContainer) {
        currentHoldersContainer.innerHTML = html;
    } else {
        console.error('Container for displaying current holders is missing.');
    }
}

// Function to fetch checkout history of the book
function fetchCheckoutHistory(bookId) {
    fetch(`http://localhost:8080/api/registers/book/${bookId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch checkout history.');
            }
            return response.json();
        })
        .then(registers => {
            // Filter out registers where returnDate is not null to only show returned books
            const returnedRegisters = registers.filter(register => register.returnDate != null);

            // Use the member IDs from the returned registers to fetch member details
            const memberDetailsPromises = returnedRegisters.map(register =>
                fetch(`http://localhost:8080/api/members/${register.memberId}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Failed to fetch member details');
                        }
                        return response.json();
                    })
                    .then(member => ({
                        ...member,
                        checkoutDate: register.checkoutDate,
                        dueDate: register.dueDate,
                        returnDate: register.returnDate, // Include returnDate in the details
                        overdueFine: register.overdueFine // Assuming overdueFine is part of the register object
                    }))
            );

            return Promise.all(memberDetailsPromises);
        })
        .then(membersWithDetails => {
            // Display the fetched members with details, including returnDate
            displayCheckoutHistory(membersWithDetails);
        })
        .catch(error => {
            console.error('Error fetching checkout history:', error);
        });
}

// Function to display checkout history information as a table
function displayCheckoutHistory(members) {
    let html = '<p>No checkout history.</p>';
    // Start with the table headers
    if (members.length > 0) {
        html = '<h2 class="h2-no-line">Checkout History</h2><table class="results-table"><thead><tr><th>First Name</th><th>Last Name</th><th>Card #</th><th>Checkout Date</th><th>Due Date</th><th>Return Date</th><th>Overdue Fine</th></tr></thead><tbody>';
    }
    members.forEach(member => {
        // Format dates and overdue fine
        const checkoutDate = member.checkoutDate ? new Date(member.checkoutDate).toLocaleDateString() : 'N/A';
        const dueDate = member.dueDate ? new Date(member.dueDate).toLocaleDateString() : 'N/A';
        const returnDate = member.returnDate ? new Date(member.returnDate).toLocaleDateString() : 'N/A';
        const overdueFineDisplay = member.overdueFine ? `$${member.overdueFine} USD` : '';

        // For each member, add a row with the checkout history information
        html += `<tr>
                    <td>${member.firstName}</td>
                    <td>${member.lastName}</td>
                    <td>${member.id}</td>
                    <td>${checkoutDate}</td>
                    <td>${dueDate}</td>
                    <td>${returnDate}</td>
                    <td>${overdueFineDisplay}</td>
                 </tr>`;
    });
    html += '</tbody></table>';

    // Assuming there is a container in your HTML to display this information
    const checkoutHistoryContainer = document.getElementById('checkoutHistoryContainer'); // Ensure you have this container in your HTML
    if (checkoutHistoryContainer) {
        checkoutHistoryContainer.innerHTML = html;
    } else {
        console.error('Container for displaying checkout history is missing.');
    }
}


function updateEditBookLink(bookId) {
    const editBookLink = document.getElementById('editBookLink');
    if (editBookLink) {
        editBookLink.href = `edit-book.html?id=${bookId}`;
    }
}