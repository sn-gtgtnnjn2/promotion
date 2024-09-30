document.addEventListener('DOMContentLoaded', function() {
    const rowsPerPage = 11;
    const table = document.getElementById('eventTable').getElementsByTagName('tbody')[0];
    const rows = table.getElementsByTagName('tr');
    const pagination = document.getElementById('pagination');
    const pageCount = Math.ceil(rows.length / rowsPerPage);

    function displayPage(page) {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        for (let i = 1; i < rows.length; i++) {
			rows[0].style.display = '';
            rows[i].style.display = (i >= start && i < end) ? '' : 'none';
        }

        const buttons = document.querySelectorAll('.page-button');
        if (buttons.length > 0) {
            buttons.forEach(button => button.classList.remove('active'));
            if (buttons[page - 1]) {
                buttons[page - 1].classList.add('active');
            }
        }
    }

    function createPagination() {
        for (let i = 1; i <= pageCount; i++) {
            const pageButton = document.createElement('div');
            pageButton.className = 'page-button';
            pageButton.innerText = i;
            pageButton.addEventListener('click', function() {
                displayPage(i);
            });
            pagination.appendChild(pageButton);
        }
    }

    displayPage(1);
    createPagination();
    const buttons = document.querySelectorAll('.page-button');
    buttons[0].classList.add('active');
});