document.addEventListener('DOMContentLoaded', function() {
  let currentYear = new Date().getFullYear();
  let currentMonth = new Date().getMonth();

  function generateCalendar(year, month) {
    const calendar = document.getElementById('calendar');
    const monthYear = document.getElementById('month-year');
    calendar.innerHTML = `
      <div class="calendar-header">
        <span id="prev" class="arrow">◀</span>
        <span id="month-year"></span>
        <span id="next" class="arrow">▶</span>
      </div>
    `;

    const table = document.createElement('table');
    const headerRow = document.createElement('tr');
    const daysOfWeek = ['日', '月', '火', '水', '木', '金', '土'];

    daysOfWeek.forEach(day => {
      const th = document.createElement('th');
      th.textContent = day;
      headerRow.appendChild(th);
    });

    table.appendChild(headerRow);

    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();
    let date = 1;

    for (let i = 0; i < 6; i++) {
      const row = document.createElement('tr');

      for (let j = 0; j < 7; j++) {
        const cell = document.createElement('td');

        if (i === 0 && j < firstDay) {
          cell.textContent = '';
        } else if (date > lastDate) {
          cell.textContent = '';
        } else {
          cell.textContent = date;
          const selectedDate = new Date(year, month, date); // 正しい日付を取得
          cell.addEventListener('click', () => {
            alert(`選ばれた日付: ${selectedDate.getFullYear()}-${selectedDate.getMonth() + 1}-${selectedDate.getDate()}`);
          });
          if (date === new Date().getDate() && month === new Date().getMonth() && year === new Date().getFullYear()) {
            cell.classList.add('today');
          }
          date++;
        }

        row.appendChild(cell);
      }

      table.appendChild(row);
    }

    calendar.appendChild(table);
    document.getElementById('month-year').textContent = `${year}年 ${month + 1}月`; // ここでmonth-yearを更新

    document.getElementById('prev').addEventListener('click', () => {
      currentMonth--;
      if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
      }
      generateCalendar(currentYear, currentMonth);
    });

    document.getElementById('next').addEventListener('click', () => {
      currentMonth++;
      if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
      }
      generateCalendar(currentYear, currentMonth);
    });
  }

  generateCalendar(currentYear, currentMonth);
});
