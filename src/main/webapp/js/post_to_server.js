/**
 * 
 */

  function postToServer() {
        const data = { userId: document.getElementById('userId').value };

        fetch('submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                if (!childWindow || childWindow.closed) {
                    openChildWindow();
                }
                childWindow.onload = function() {
                    childWindow.document.getElementById('error_message').textContent = data.error_message;
                };
            } else {
                
            }
        });
    }