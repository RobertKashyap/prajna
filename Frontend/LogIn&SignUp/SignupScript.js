document.getElementById('signup-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('error-message');

    try {
        const response = await fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, email, password })
        });

        if (response.ok) {
            alert('User registered successfully!');
        } else {

            const errorData = await response.json();
            console.log('Error:', errorData);
            errorMessage.textContent = `Error: ${errorData}`;
        }
    } catch (error) {
        console.error('Error:', error);
        errorMessage.textContent = `Error: ${error.message}`;
    }
});
