document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        // Get today's date in YYYY-MM-DD format
        const today = new Date().toISOString().split('T')[0];
        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            dateOfBirth: document.getElementById('dateOfBirth').value,
            address: {
                addressName: document.getElementById('address').value,
                addressNumber: document.getElementById('addressNumber').value,
                zipCode: document.getElementById('zipCode').value,
                placeName: document.getElementById('city').value,
                country: document.getElementById('country').value,
                additionalInfo: document.getElementById('addInfo').value
            },
            email: document.getElementById('email').value,
            phone: document.getElementById('phone').value,
            barcodeNumber: document.getElementById('barcode').value,
            membershipStarted: today,
            isActive: true
        };

        addNewMember(formData, form);
    });
});

function addNewMember(data, form) {
    console.log('Attempting to add new member with data: ', data);

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
            form.reset();
        })
        .catch(error => {
            console.error('Error adding new member:', error);
            alert('Failed to add new member. Please try again.');
        });
}