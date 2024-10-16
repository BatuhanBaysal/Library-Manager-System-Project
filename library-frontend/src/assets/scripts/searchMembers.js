document.addEventListener('DOMContentLoaded', function() {
  const form = document.querySelector('form');
  form.addEventListener('submit', function(e) {
    e.preventDefault(); // Prevent default form submission

    // Retrieve input values from the form
    const barcodeNumber = document.getElementById('barcodeNumber').value;
    const cardNumber = document.getElementById('cardNumber').value;
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;

    // Construct the API request URL with query parameters
    const url = `http://localhost:8080/api/members/search?firstName=${encodeURIComponent(firstName)}&lastName=${encodeURIComponent(lastName)}&barcodeNumber=${encodeURIComponent(barcodeNumber)}&cardNumber=${encodeURIComponent(cardNumber)}`;

    // Make the API request using fetch
    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        console.log(data); // Log the data for verification
        displayResults(data); // Process and display the data
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
                     <!-- Added a link to member-info.html with the member's ID as a query parameter -->
                 </tr>`;
    });
    html += '</table>';

    // Assuming there is a div with id="resultsContainer" in your HTML where the results will be displayed
    const resultsContainer = document.getElementById('resultsContainer');
    if (resultsContainer) {
        resultsContainer.innerHTML = html;
    } else {
        console.error('No container found to display the results');
    }
  }
});