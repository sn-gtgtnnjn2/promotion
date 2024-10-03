<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="">
</head>
<body>
<h1>はじめてのJSP</h1>
<script src="<%= request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
<!-- Ctrl + Shift + / -->

  <form id="user-management-form" method="POST" action="<%= request.getContextPath() %>/event/eventViewOrg">
<div class="user-management-container">
    <div class="user-select-box">
      <div>承認済みユーザー</div>
      <div class="custom-select-list" id="approved-users">
        <!-- 承認済みユーザーのリストがここに表示されます -->
      </div>
    </div>
    <div class="action-buttons">
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('pending-users', 'approved-users');"><i class="bi bi-chevron-double-left"></i>← 承認</button>
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('approved-users', 'pending-users');">却下 →<i class="bi bi-chevron-double-right"></i></button>
    </div>
    <div class="user-select-box">
      <div class="text-muted">申請中ユーザー</div>
      <div class="custom-select-list" id="pending-users">
        <div class="select-item" data-value="1"><img src="path/to/your/image1.png" alt="January"> January</div>
      </div>
    </div>
    <div class="action-buttons">
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('rejected-users', 'pending-users');"><i class="bi bi-chevron-double-left"></i>← 承認</button>
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('pending-users', 'rejected-users');">却下 →<i class="bi bi-chevron-double-right"></i></button>
    </div>
    <div class="user-select-box">
      <div class="text-muted">申請中ユーザー</div>
      <div class="custom-select-list" id="rejected-users">
        <div class="select-item" data-value="1"><img src="path/to/your/image1.png" alt="January"> January</div>
        <div class="select-item" data-value="2"><img src="path/to/your/image1.png" alt="January"> February</div>
        <div class="select-item" data-value="3"><img src="path/to/your/image1.png" alt="January"> March</div>
        <div class="select-item" data-value="4"><img src="path/to/your/image1.png" alt="January"> April</div>
      </div>
    </div>
    <!-- Hidden fields to store the selected values -->
    <div id="approvedUsers"></div>
    <div id="pendingUsers"></div>
    <div id="rejectedUsers"></div>
</div>
    <button type="submit" class="btn btn-primary" onclick="prepareHiddenFields();">登録</button>
<div>
</div>
  </form>


<style>
		.user-management-container{
            display: flex;
            align-items: center;
            justify-content: space-between;
            width: 600px;
            margin: 50px auto;
        }

  .custom-select-list {
  	width: 10vw;
  	height:200px;
    border: 1px solid #ddd;
    max-height: 200px;
    overflow-y: auto;
  }
  .select-item {
    padding: 10px;
    cursor: pointer;
    display: flex;
    align-items: center;
  }
  .select-item img {
    margin-right: 10px;
  }
  .select-item:hover {
    background-color: #f1f1f1;
  }
  
  .selected{
   background-color: #A8DBA8;
  }
  
  button.btn.btn-primary{
    text-align:center;
  	margin : auto;
  }
</style>

<script>
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
</script>

</body>
</html>