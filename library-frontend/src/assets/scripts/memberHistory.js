document.addEventListener('DOMContentLoaded', () => {
  const memberId = new URLSearchParams(window.location.search).get('id');
  if (!memberId) {
    console.error('No member ID specified in the URL');
    return;
  }

  // Fetch member's details to update the header with the member's name
  fetch(`http://localhost:8080/api/members/${memberId}`)
  .then(response => response.json())
  .then(member => {
    document.getElementById('memberName').textContent = `Member history: ${member.firstName} ${member.lastName}`;
  })
  .catch(error => {
    console.error('Error fetching member details:', error);
  });

  fetchMemberHistory(memberId);
});

document.addEventListener('DOMContentLoaded', () => {
  const memberId = new URLSearchParams(window.location.search).get('id');
  if (!memberId) {
    console.error('No member ID specified in the URL');
    return;
  }

  fetchMemberHistory(memberId);
});

function fetchMemberHistory(memberId) {
  fetch(`http://localhost:8080/api/registers/member/${memberId}`)
    .then(response => {
      if (!response.ok) throw new Error('Failed to fetch member history');
      return response.json();
    })
    .then(registers => {
      const historyList = document.getElementById('historyList');
      historyList.innerHTML = ''; // Clear existing content

      // Create table and headers
      const table = document.createElement('table');
      table.className = 'results-table'; // Ensure this line is present to apply .results-table styles
      const thead = document.createElement('thead');
      const tbody = document.createElement('tbody');
      table.appendChild(thead);
      table.appendChild(tbody);
      historyList.appendChild(table);

      // Set table headers
      const headerRow = document.createElement('tr');
      const headers = ['Book', 'Checked-out', 'Returned', 'Overdue fine'];
      headers.forEach(headerText => {
        const header = document.createElement('th');
        header.textContent = headerText;
        headerRow.appendChild(header);
      });
      thead.appendChild(headerRow);

      // Process and display each register
      const returnedBooks = registers.filter(register => register.returnDate !== null);

      if (returnedBooks.length === 0) {
        const noHistoryMessage = document.createElement('p');
        noHistoryMessage.textContent = 'No history found for this member.';
        historyList.appendChild(noHistoryMessage);
        return;
      }

      returnedBooks.forEach(register => {
        fetch(`http://localhost:8080/api/books/${register.bookId}`)
          .then(response => {
            if (!response.ok) throw new Error('Failed to fetch book details');
            return response.json();
          })
          .then(book => {
            const row = document.createElement('tr');
            const bookCell = document.createElement('td');
            const checkoutDateCell = document.createElement('td');
            const returnDateCell = document.createElement('td');
            const overdueFineCell = document.createElement('td');

            bookCell.innerHTML = `<a href="book-info.html?id=${register.bookId}">${book.title}, ${book.author}</a>`;
            checkoutDateCell.textContent = new Date(register.checkoutDate).toLocaleDateString();
            returnDateCell.textContent = new Date(register.returnDate).toLocaleDateString();
            overdueFineCell.textContent = register.overdueFine !== null ? `$${parseFloat(register.overdueFine).toFixed(2)} USD` : '';

            row.appendChild(bookCell);
            row.appendChild(checkoutDateCell);
            row.appendChild(returnDateCell);
            row.appendChild(overdueFineCell);

            tbody.appendChild(row);
          })
          .catch(error => console.error('Error fetching book details:', error));
      });
    })
    .catch(error => {
      console.error('Error fetching member history:', error);
    });
}