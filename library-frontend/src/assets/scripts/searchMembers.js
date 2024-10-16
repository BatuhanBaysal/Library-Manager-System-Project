document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (ev) {
        ev.preventDefault();
        const barcodeNumber = document.getElementById('barcodeNumber').value;
        const cardNumber = document.getElementById('cardNumber').value;
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const url = `http://localhost:8080/api/members/search?firstName=${encodeURIComponent(firstName)}&lastName=${encodeURIComponent(lastName)}&barcodeNumber=${encodeURIComponent(barcodeNumber)}&cardNumber=${encodeURIComponent(cardNumber)}`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                displayResults(data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
                alert('Failed to fetch data. Please check console for details.');
            });
    });

    function displayResults(data) {
        let html = '<table class="results-table"><tr><th>Card #</th><th>First Name</th><th>Last Name</th><th>Date of Birth</th><th></th></tr>';
        data.forEach(member => {
            html += `<tr>
                    <td>${member.id}</td>
                    <td>${member.firstName}</td>
                    <td>${member.lastName}</td>
                    <td>${member.dateOfBirth}</td>
                    <td><a class="action-btn" href="member-info.html?id=${member.id}">View Details</a></td>
                </tr>`;
        });
        html += '</table>';

        const resultsContainer = document.getElementById('resultsContainer');
        if (resultsContainer) {
            resultsContainer.innerHTML = html;
        } else {
            console.error('No container found to display the results');
        }
    }
});  