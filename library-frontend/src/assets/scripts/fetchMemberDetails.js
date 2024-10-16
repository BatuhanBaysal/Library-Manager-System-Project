let memberId = null; // Declare memberId globally

document.addEventListener('DOMContentLoaded', function () {
    memberId = getQueryParam('id');
    if (!memberId) {
        console.error('No member ID specified in the URL');
        return;
    }

    const url = `http://localhost:8080/api/members/${memberId}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(member => {
            displayMemberDetails(member);
            fetchCheckedOutBooks(member.id); // Moved outside of displayMemberDetails
        })
        .catch(error => {
            console.error('Error fetching member details:', error);
        });

    const memberHistoryLink = document.querySelector('a[href="member-history.html"]');
    if (memberHistoryLink) {
        memberHistoryLink.href = `member-history.html?id=${memberId}`;
    }

    // Update the Edit Member link with the member ID
    const editMemberLink = document.querySelector('a[href="edit-member.html"]');
    if (editMemberLink) {
        editMemberLink.href = `edit-member.html?id=${memberId}`;
    }
});


function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}


function displayMemberDetails(member) {
    document.getElementById('memberFullName').textContent = `${member.firstName} ${member.lastName}`;
    document.getElementById('cardNumber').textContent = member.barcodeNumber || 'N/A';

    let address = member.address ? `${member.address.addressNumber} ${member.address.addressName}, ${member.address.zipCode} ${member.address.placeName}, ${member.address.country}` : 'N/A';
    document.getElementById('address').textContent = address;

    document.getElementById('phone').textContent = member.phone || 'N/A';
    document.getElementById('email').textContent = member.email || 'N/A';

    // Assuming the dateOfBirth is in ISO format (YYYY-MM-DD) and you want to display it in a more readable format
    let dob = new Date(member.dateOfBirth).toLocaleDateString("en-US"); // Adjust the locale as needed
    document.getElementById('dateOfBirth').textContent = dob; // Displaying the Date of Birth

    // Format and display the Membership Started date
    let membershipStarted = member.membershipStarted ? new Date(member.membershipStarted).toLocaleDateString("en-US") : 'N/A';
    document.getElementById('membershipStarted').textContent = membershipStarted;

    // Format and display the Membership Ended date if available
    let membershipEnded = member.membershipEnded ? new Date(member.membershipEnded).toLocaleDateString("en-US") : 'N/A';
    document.getElementById('membershipEnded').textContent = membershipEnded;

    document.getElementById('membershipStatus').textContent = member.isActive ? "Active" : "Terminated";

    // Dynamically set the "Terminate Membership" or "Activate Membership" link
    const membershipActionLink = document.getElementById('membershipActionLink');
    if (membershipActionLink) {
        if (member.isActive) {
            membershipActionLink.textContent = 'Terminate Membership';
            membershipActionLink.href = 'javascript:terminateMembership()'; // Assuming you have a function for termination
        } else {
            membershipActionLink.textContent = 'Activate Membership';
            membershipActionLink.href = 'javascript:activateMembership()'; // Assuming you have a function for activation
        }
    }

    // Control the visibility of the checkout link based on member's active status
    const checkoutLink = document.getElementById('checkoutLink');
    if (checkoutLink) {
        if (member.isActive) {
            checkoutLink.style.display = 'inline'; // Show link if member is active
            checkoutLink.href = `book-checkout.html?memberBarcode=${member.barcodeNumber}`; // Set the correct link
        } else {
            checkoutLink.style.display = 'none'; // Hide link if member is not active
        }
    }
}


function fetchCheckedOutBooks(memberId) {
    fetch(`http://localhost:8080/api/registers/member/${memberId}`)
        .then(response => {
            if (!response.ok) {
                // Handle specific "no content" scenario gracefully
                if (response.status === 404 || response.status === 204) {
                    return [];
                }
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(registers => {
            if (registers.length === 0) {
                // Directly display the message for no checked-out books
                displayCheckedOutBooks([]);
                return;
            }

            // Fetch details for each checked-out book and include dueDate and registerId
            const bookDetailsPromises = registers.filter(register => register.returnDate === null)
                .map(register =>
                    fetch(`http://localhost:8080/api/books/${register.bookId}`)
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Failed to fetch book details');
                            }
                            return response.json();
                        })
                        .then(bookDetails => ({
                            ...bookDetails, // Include all book details
                            dueDate: register.dueDate, // Add dueDate from the register
                            registerId: register.id // Add registerId from the register
                        }))
                        .catch(error => {
                            console.error('Error fetching book details:', error);
                            return null; // Handle failed book details fetches
                        })
                );

            Promise.all(bookDetailsPromises)
                .then(booksDetails => {
                    // Filter out any failed fetches
                    const validBooksDetails = booksDetails.filter(details => details !== null);
                    displayCheckedOutBooks(validBooksDetails);
                });
        })
        .catch(error => {
            console.error('Error fetching checked out books:', error);
            displayCheckedOutBooks([]); // Fallback for any error during the process
        });
}


function displayCheckedOutBooks(books) {
    console.log("Books to display: ", books);
    const booksTable = document.getElementById('checkedOutBooks');
    const booksHeading = document.getElementById('checkedOutBooksHeading'); // Get the heading element

    // Clear previous content
    booksTable.innerHTML = '';

    // Check if there are no checked-out books
    if (books.length === 0 || !books) {
        booksHeading.style.display = 'none'; // Hide the heading if there are no books
        return; // Exit the function early
    } else {
        booksHeading.style.display = 'block'; // Show the heading if there are books
    }

    // If there are books, proceed with displaying them as before
    let tbody = document.createElement('tbody');
    booksTable.appendChild(tbody);

    books.forEach((book, index) => {
        let row = tbody.insertRow();
        let detailCell = row.insertCell(0);
        let actionCell = row.insertCell(1);

        detailCell.innerHTML = `${index + 1}. ${book.title}, ${book.author} (Due date: ${book.dueDate})`;

        // Create a space (like a tab) before the link
        let space = document.createTextNode('\u00A0\u00A0\u00A0\u00A0'); // Uses four non-breaking spaces for approximation of a tab
        actionCell.appendChild(space);

        // Create the "Return" hyperlink
        let returnLink = document.createElement('a');
        returnLink.href = 'javascript:void(0)';
        returnLink.className = 'action-btn';
        returnLink.textContent = 'Return this book';
        returnLink.onclick = function () {
            returnBook(book.registerId, book.title, book.author);
        };
        actionCell.appendChild(returnLink);
    });
}


function returnBook(registerId, bookTitle, bookAuthor) {
    const payload = {
        returnDate: new Date().toISOString()
    };
    console.log("returnBook payload: ", payload);

    fetch(`http://localhost:8080/api/registers/updateRegister/${registerId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to return book.');
            }
            return response.json();
        })
        .then(async () => { // Correctly use async here to await the fetch call
            const response = await fetch(`http://localhost:8080/api/registers/${registerId}`);
            if (!response.ok) {
                throw new Error('Failed to fetch updated register details.');
            }
            return response.json(); // Await the JSON parsing of the response
        })
        .then(registerDetails => {
            // Increase the book's available copies now
            increaseBookCopies(registerDetails.bookId); // Correctly call without await, as we don't need to wait here
            // However, if you want to wait for this to finish before showing the alert, you should await this call and mark the current function as async
            return registerDetails;
        })
        .then(registerDetails => {
            let alertMessage = `Book "${bookTitle}, ${bookAuthor}" successfully returned.`;
            if (registerDetails.overdueFine != null) {
                // Format the overdue fine to two decimal places
                let formattedFine = parseFloat(registerDetails.overdueFine).toFixed(2);
                // Add two line breaks and the overdue fine information
                alertMessage += `\n\nOverdue fine: ${formattedFine} USD.`;
            }
            // Display the alert message. Note: Line breaks (\n) in an alert box are respected.
            alert(alertMessage);
            fetchCheckedOutBooks(memberId); // Refresh the list of checked out books
        })
        .catch(error => {
            console.error('Error returning book:', error);
            alert('Failed to return book. Please try again.');
        });
}


function increaseBookCopies(bookId) {
    // Fetch book details to get the current number of available copies
    return fetch(`http://localhost:8080/api/books/${bookId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch book details.');
            }
            return response.json();
        })
        .then(book => {
            // Increment the number of available copies by one
            const updatedCopies = book.noOfAvailableCopies + 1;
            // Update the book's available copies in the database
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
        })
        .catch(error => {
            console.error('Error updating book copies:', error);
        });
}

// Function to terminate a member's membership
function terminateMembership() {
    updateMembershipStatus(false); // false to terminate membership
}

// Function to activate a member's membership
function activateMembership() {
    updateMembershipStatus(true); // true to activate membership
}

// Function to update the membership status
function updateMembershipStatus(isActive) {
    // Assuming memberId is the global variable holding the current member's ID
    const url = `http://localhost:8080/api/members/updateMember/${memberId}`;

    // Prepare the payload
    const today = new Date().toISOString().split('T')[0];
    const payload = isActive ? { membershipEnded: "" } : { membershipEnded: today };
    console.log('updateMembershipStatus payload:', payload);

    fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update membership status.');
            }
            return response.json();
        })
        .then(updatedMember => {
            console.log('Membership status updated successfully.', updatedMember);
            // Optionally, refresh the page or UI to reflect the new membership status
            // location.reload(); // Uncomment to refresh the page automatically
            // Or you can manually update the UI to reflect the change
            updateUIBasedOnMembershipStatus(updatedMember.isActive, updatedMember.membershipEnded);
        })
        .catch(error => {
            console.error('Error updating membership status:', error);
        });
}

// Utility function to update UI based on membership status
/*function updateUIBasedOnMembershipStatus(isActive) {
    const statusElement = document.getElementById('membershipStatus');
    const actionLink = document.getElementById('membershipActionLink');
    const checkoutLink = document.getElementById('checkoutLink');

    if (isActive) {
        statusElement.textContent = "Active";
        actionLink.textContent = 'Terminate Membership';
        actionLink.onclick = function () { updateMembershipStatus(false); }; // Update onclick to terminate

        // Show the checkout link if the member is active
        if (checkoutLink) {
            checkoutLink.style.display = 'inline'; // Adjust as needed for your site's styling
            checkoutLink.href = `book-checkout.html?memberBarcode=${memberId}`; // Ensure this is the correct link for your app
        }
    } else {
        statusElement.textContent = "Terminated";
        actionLink.textContent = 'Activate Membership';
        actionLink.onclick = function () { updateMembershipStatus(true); }; // Update onclick to activate

        // Hide the checkout link if the member is not active
        if (checkoutLink) {
            checkoutLink.style.display = 'none';
        }
    }
} */

// Adjust the function signature to accept the membershipEnded date
function updateUIBasedOnMembershipStatus(isActive, membershipEnded = '') {
    const statusElement = document.getElementById('membershipStatus');
    const endedElement = document.getElementById('membershipEnded'); // Ensure you have this element in HTML
    const actionLink = document.getElementById('membershipActionLink');
    const checkoutLink = document.getElementById('checkoutLink');

    // Update status text
    statusElement.textContent = isActive ? "Active" : "Terminated";
    
    // Update ended date text; Format it if not empty
    endedElement.textContent = isActive ? 'N/A' : (membershipEnded ? new Date(membershipEnded).toLocaleDateString("en-US") : 'N/A');

    // Update the text and onclick handler of the action link
    actionLink.textContent = isActive ? 'Terminate Membership' : 'Activate Membership';
    actionLink.onclick = function () { updateMembershipStatus(!isActive); };

    // Show or hide the checkout link based on the active status
    checkoutLink.style.display = isActive ? 'inline' : 'none';
    if (isActive) {
        checkoutLink.href = `book-checkout.html?memberBarcode=${memberId}`;
    }
}