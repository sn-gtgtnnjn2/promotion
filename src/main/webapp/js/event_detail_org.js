/**
 * 
 */

function submitSignUpForm() {
	document.getElementById('signUpForm').submit();
}

function moveGroup(fromId, toId) {
	const fromList = document.getElementById(fromId);
	const toList = document.getElementById(toId);
	const selectedItems = fromList.querySelectorAll('.select-item.selected');

	selectedItems.forEach(item => {
		item.classList.remove('selected');
		toList.appendChild(item);
	});

	updateHiddenParams('hiddenValues', 'values[]', 'selectGroup');
}

document.querySelectorAll('.select-item').forEach(item => {
	item.addEventListener('click', function() {
		this.classList.toggle('selected');
	});
});

function updateHiddenParams(hiddenId, hiddenName, selectBoxId) {
	hiddenId = '#' + hiddenId;
	selectBoxId = '#' + selectBoxId;
	// 既に hidden が設定済なら削除する.
	if ($(hiddenId).children().length > 0) {
		$(hiddenId).empty();
	}

	// カスタムセレクトリストの内容を取得.
	const items = $(selectBoxId).find('.select-item');
	// 配列型で hidden を追加する.
	for (let i = 0; i < items.length; i++) {
		$(hiddenId).append('<input type="hidden" name="' + hiddenName + '" value="' + items.eq(i).data('value') + '">');
	}
}


/**
 * カスタムセレクトリストにある全ての値を hidden パラメータに設定する.
 * @param {*} hiddenId 
 * @param {*} hiddenName クエリーの名前. 複数選択がある場合は選択１つでも配列になるよう末尾に[]を付ける. 選択なしの場合は undefinedになる.
 * @param {*} selectBoxId 
 */
function updateHiddenParams(hiddenId, hiddenName, selectBoxId) {
	hiddenId = '#' + hiddenId;
	selectBoxId = '#' + selectBoxId;
	// 既に hidden が設定済なら削除する.
	if ($(hiddenId).children().length > 0) {
		$(hiddenId).empty();
	}

	// カスタムセレクトリストの内容を取得.
	const items = $(selectBoxId).find('.select-item');

	// 配列型で hidden を追加する.
	for (let i = 0; i < items.length; i++) {
		$(hiddenId).append('<input type="hidden" name="' + hiddenName + '" value="' + items.eq(i).data('value') + '">');
	}
}

/**
 * 各セレクトボックスの値を hidden フィールドに設定する.
 */
function prepareHiddenFields() {
	updateHiddenParams('approvedUsers', 'approvedUsers[]', 'approved-users');
	updateHiddenParams('pendingUsers', 'pendingUsers[]', 'pending-users');
	updateHiddenParams('rejectedUsers', 'rejectedUsers[]', 'rejected-users');
}

document.getElementById('eventDate').addEventListener('blur', function() {
    let dateInput = this.value;
    const dateError = document.getElementById('date-error');
    this.classList.remove('error');
    dateError.style.display = 'none';

    if (dateInput.length === 4) {
        const currentYear = new Date().getFullYear();
        dateInput = currentYear + dateInput;
    }
    if (dateInput.length === 8) {
        const year = dateInput.substring(0, 4);
        const month = dateInput.substring(4, 6);
        const day = dateInput.substring(6, 8);
        const date = new Date(year, month - 1, day);
        if (date && date.getFullYear() == year && date.getMonth() + 1 == month && date.getDate() == day) {
            const dayOfWeek = ['日', '月', '火', '水', '木', '金', '土'][date.getDay()];
            this.value = `${year}/${month}/${day}(${dayOfWeek})`;
        } else {
            this.classList.add('error');
            dateError.style.display = 'block';
        }
    } else {
        this.classList.add('error');
        dateError.style.display = 'block';
    }
});

document.getElementById('eventDate').addEventListener('focus', function() {
    const formattedDate = this.value;
    const dateError = document.getElementById('date-error');
    this.classList.remove('error');
    dateError.style.display = 'none';

    const match = formattedDate.match(/^(\d{4})\/(\d{2})\/(\d{2})\(\S\)$/);
    if (match) {
        const year = match[1];
        const month = match[2];
        const day = match[3];
        this.value = `${year}${month}${day}`;
    }
});

document.getElementById('eventTime').addEventListener('blur', function() {
	let timeInput = this.value;
	const timeError = document.getElementById('time-error');
	this.classList.remove('error');
	timeError.style.display = 'none';

	if (timeInput.length === 2) {
		if (timeInput === '24') {
			timeInput = '0000';
			const dateInput = document.getElementById('eventDate').value;
			const match = dateInput.match(/^(\d{4})\/(\d{2})\/(\d{2})\(\S\)$/);
			if (dateInput.length === 8 || match) {
				const year = match[1];
				const month = match[2];
				const day = match[3];
				const date = new Date(year, month - 1, day);
				date.setDate(date.getDate() + 1);
				const newYear = date.getFullYear();
				const newMonth = ('0' + (date.getMonth() + 1)).slice(-2);
				const newDay = ('0' + date.getDate()).slice(-2);
				const dayOfWeek = ['日', '月', '火', '水', '木', '金', '土'][date.getDay()];
				document.getElementById('eventDate').value = `${newYear}/${newMonth}/${newDay}(${dayOfWeek})`;
			}
		} else {
			timeInput = timeInput + '00';
		}
	}
	if (timeInput.length === 3) {
		const formattedTimeInput = ("0" + timeInput);
		const hours = formattedTimeInput.substring(0, 2);
		const minutes = formattedTimeInput.substring(2, 4);
		if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) {
			this.value = `${hours}:${minutes}`;
		} else {
			this.classList.add('error');
			timeError.style.display = 'block';
		}
	}else if (timeInput.length === 4) {
		const hours = timeInput.substring(0, 2);
		const minutes = timeInput.substring(2, 4);
		if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) {
			this.value = `${hours}:${minutes}`;
		} else {
			this.classList.add('error');
			timeError.style.display = 'block';
		}
	} else {
		this.classList.add('error');
		timeError.style.display = 'block';
	}
});

document.getElementById('eventTime').addEventListener('focus', function() {
	const formattedTime = this.value;
	const dateError = document.getElementById('date-error');
	this.classList.remove('error');
	dateError.style.display = 'none';

	const match = formattedTime.match(/^(\d{2})\:(\d{2})$/);
	if (match) {
		const hour = match[1];
		const time = match[2];
		this.value = `${hour}${time}`;
	}
});