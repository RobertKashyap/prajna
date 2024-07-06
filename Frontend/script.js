const loginForm = document.getElementById('login-form');
const responseDiv = document.getElementById('response');

loginForm.addEventListener('submit', (event) => {
  event.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  const encodedCredentials = btoa(username + ':' + password);
  const authorizationHeader = `Basic ${encodedCredentials}`;

  fetch('http://localhost:8080/users', {
    method: 'GET', // Adjust method (GET, POST, etc.) based on your API needs
    headers: {
      'Authorization': authorizationHeader,
      'Content-Type': 'application/json' // Adjust content type if needed
    }
  })
  .then(response => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Unauthorized access');
    }
  })
  .then(data => {
    responseDiv.textContent = 'User list: ' + JSON.stringify(data);
    
    // Now call the /authuser endpoint
    return fetch('http://localhost:8080/authuser', {
      method: 'GET',
      headers: {
        'Authorization': authorizationHeader,
        'Content-Type': 'application/json'
      }
    });
  })
  .then(response => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Failed to fetch user details');
    }
  })
  .then(authUserData => {
    responseDiv.textContent += '\nAuthenticated user details: ' + JSON.stringify(authUserData);
  })
  .catch(error => {
    responseDiv.textContent = 'Error: ' + error.message;
  });
});
