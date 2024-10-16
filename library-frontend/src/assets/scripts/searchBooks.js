document.addEventListener('DOMContentLoaded', function () {
  const form = document.querySelector('form');
  form.addEventListener('submit', function (e) {
    e.preventDefault(); // Prevent default form submission

    // Retrieve input values from the form
    const barcodeNumber = document.getElementById('barcodeNumber').value;
    const isbn = document.getElementById('isbn').value;
    const author = document.getElementById('author').value;
    const title = document.getElementById('title').value;

    // Construct the API request URL with query parameters
    const url = `http://localhost:8080/api/books/search?barcodeNumber=${encodeURIComponent(barcodeNumber)}&isbn=${encodeURIComponent(isbn)}&author=${encodeURIComponent(author)}&title=${encodeURIComponent(title)}`;

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
        alert('No book found with given criteria.');
      });
  });
});

function displayResults(data) {
  // Start with the table headers, including a new "Details" column
  // Ensure the table has the 'results-table' class applied
  let html = '<table class="results-table"><tr><th>Title</th><th>Author</th><th>Available Copies</th><th>Details</th></tr>';
  data.forEach(book => {
    // For each book, add a row with the book information
    // And add a "View Details" link in the new column, pointing to book-info.html with a query parameter for ISBN
    html += `<tr>
              <td>${book.title}</td>
              <td>${book.author}</td>
              <td>${book.noOfAvailableCopies}</td>
              <td><a href="book-info.html?id=${book.id}" class="action-btn">View Details</a></td>
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