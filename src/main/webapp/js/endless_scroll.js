document.addEventListener('DOMContentLoaded', function() {
    let page = 1;
    const eventList = document.getElementById('event-list');
    const loadMoreButton = document.getElementById('load-more');

    function loadMoreEvents() {
        fetch(`/events?page=${page}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(event => {
                    const li = document.createElement('li');
                    li.className = 'event-item';
                    li.innerHTML = `
                        <span class="event-date">${event.date}</span>
                        <img src="data:image/jpeg;base64,${event.organizerImageString}" alt="${event.organizerName}" class="event-organizer-icon">
                        <span class="event-status">募集中</span>
                        <span class="event-title">${event.title}</span>
                        <span class="event-organizer">${event.organizerName}</span>
                    `;
                    eventList.appendChild(li);
                });
                page++;
                if (data.length < 10) {
                    loadMoreButton.style.display = 'none';
                }
            });
    }

    window.addEventListener('scroll', function() {
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 500) {
            loadMoreEvents();
        }
    });

    loadMoreButton.addEventListener('click', loadMoreEvents);
});
