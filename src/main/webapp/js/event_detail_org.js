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