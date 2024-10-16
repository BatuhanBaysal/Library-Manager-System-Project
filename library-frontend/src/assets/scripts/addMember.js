document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        // Get today's date in YYYY-MM-DD format
        const today = new Date().toISOString().split('T')[0];
        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            dateOfBirth: document.getElementById('dob').value,
            address: {
                addressName: document.getElementById('streetName').value,
                addressNumber: document.getElementById('streetNo').value,
                zipCode: document.getElementById('zipCode').value,
                placeName: document.getElementById('placeName').value,
                country: document.getElementById('country').value,
                additionalInfo: document.getElementById('addInfo').value
            },
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            barcodeNumber: document.getElementById('barcode').value,
            membershipStarted: today,
            isActive: true
        };

        addNewMember(formData);
    });
});


function addNewMember(data) {
    fetch('http://localhost:8080/api/members/addMember', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add new member');
            }
            return response.json();
        })
        .then(data => {
            console.log('Member added successfully:', data);
            alert('New member added successfully!');
            // Optionally, redirect to the home page or clear the form
            window.location.href = `member-info.html?id=${data.id}`;
            // form.reset();
        })
        .catch(error => {
            console.error('Error adding new member:', error);
            alert('Failed to add new member. Please try again.');
        });
}