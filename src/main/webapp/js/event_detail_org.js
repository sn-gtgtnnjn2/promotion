/**
 * 
 */

function submitSignUpForm() {
    document.getElementById('signUpForm').submit();
}
  
function moveGroup(fromId, toId) {
    moveSelect(fromId, toId);
    updateHiddenParams('hiddenValues', 'values[]', 'selectGroup');
}

function showSelectValues() {
    let values = [];
    const collection  = document.getElementById('hiddenValues').children;
    for (let i = 0; i < collection.length; i++) {
        values.push(collection[i].value);
    }
    alert(values);
}

function moveSelect(fromId, toId) {
    // 選択値の確認.
    //const val = $(`[id=${fromId}]`).val();
    //console.log(`${val} move. ${fromId} to ${toId}`);

    $(`[id=${fromId}] option:selected`).each(function() {
        $(`[id=${toId}]`).append($(this).clone());
        console.log($(this).val(), $(this).text());
        $(this).remove();
    });
}

/**
 * セレクトボックスにある全て値を hidden パラメータに設定する.
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

    // セレクトボックスの内容を取得.
    const groups = $(selectBoxId).children();
    // 配列型で hidden を追加する.
    for (let i = 0; i < groups.length; i++) {
        $(hiddenId).append('<input type="hidden" name="' + hiddenName + '" value="' + groups.eq(i).val() + '">');
    }
}

        function moveSelected(fromSelect, toSelect) {
            const selectedOptions = Array.from(fromSelect.selectedOptions);
            selectedOptions.forEach(option => {
                toSelect.appendChild(option);
            });
        }

        document.getElementById('approve-button').addEventListener('click', () => {
            moveSelected(document.getElementById('pending-users'), document.getElementById('approved-users'));
        });

        document.getElementById('reject-button').addEventListener('click', () => {
            moveSelected(document.getElementById('pending-users'), document.getElementById('rejected-users'));
        });

        document.getElementById('approve-button-2').addEventListener('click', () => {
            moveSelected(document.getElementById('rejected-users'), document.getElementById('approved-users'));
        });

        document.getElementById('reject-button-2').addEventListener('click', () => {
            moveSelected(document.getElementById('approved-users'), document.getElementById('rejected-users'));
        });
        
