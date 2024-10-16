document.addEventListener('DOMContentLoaded', () => {
    const memberId = new URLSearchParams(window.location.search).get('id');
    if (!memberId) {
      console.error('No member ID specified in the URL');
      return;
    }
    
    document.querySelector('h1').textContent = `Edit Member #${memberId}`;
    
    fetchMemberData(memberId);
});


function fetchMemberData(memberId) {
    fetch(`http://localhost:8080/api/members/${memberId}`)
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch member data');
            return response.json();
        })
        .then(member => {
            console.log("member info:", member);
            // Assuming member has fields: firstName, lastName, dob, etc.
            document.getElementById('firstName').value = member.firstName;
            document.getElementById('lastName').value = member.lastName;
            document.getElementById('dob').value = member.dateOfBirth; // format YYYY-MM-DD if needed
            document.getElementById('streetName').value = member.address.addressName;
            document.getElementById('streetNo').value = member.address.addressNumber;
            document.getElementById('zipCode').value = member.address.zipCode;
            document.getElementById('placeName').value = member.address.placeName;
            document.getElementById('country').value = member.address.country;
            document.getElementById('addInfo').value = member.address.additionalInfo;
            document.getElementById('email').value = member.email;
            document.getElementById('phone').value = member.phone;
            document.getElementById('barcode').value = member.barcodeNumber;
        })
        .catch(error => {
            console.error('Error fetching member data:', error);
        });
}

document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();
    const memberId = new URLSearchParams(window.location.search).get('id');
    const formData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        dateOfBirth: document.getElementById('dob').value,
        address: {
            streetName: document.getElementById('streetName').value,
            streetNumber: document.getElementById('streetNo').value,
            zipCode: document.getElementById('zipCode').value,
            placeName: document.getElementById('placeName').value,
            country: document.getElementById('country').value,
            additionalInfo: document.getElementById('addInfo').value
        },
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        barcode: document.getElementById('barcode').value
    };
    
    updateMemberData(memberId, formData);
});


function updateMemberData(memberId, data) {
    fetch(`http://localhost:8080/api/members/updateMember/${memberId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) throw new Error('Failed to update member data');
        alert('Member data updated successfully');
        // Redirect or refresh the page if needed
        // window.location.href = 'member-info.html?id=' + memberId;
    })
    .catch(error => {
        console.error('Error updating member data:', error);
        alert('Failed to update member data');
    });
}