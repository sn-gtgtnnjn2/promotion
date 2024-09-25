<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />

<body>
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="<%= request.getContextPath() %>/img/logo.png" alt="" />
		</h1>
	</header>
	<main>
	    <style>
        .hidden {
            display: none;
        }
    </style>
    <script>
    function showMore() {
        var hiddenRows = document.querySelectorAll('.hidden');
        hiddenRows.forEach(function(row) {
            row.classList.remove('hidden');
        });
        document.getElementById('viewMoreBtn').style.display = 'none';
        document.getElementById('closeBtn').style.display = 'block';
    }

    function closeRows() {
        var allRows = document.querySelectorAll('tr');
        allRows.forEach(function(row, index) {
            if (index > 1) { // 最初の2行以外を隠す
                row.classList.add('hidden');
            }
        });
        document.getElementById('viewMoreBtn').style.display = 'block';
        document.getElementById('closeBtn').style.display = 'none';
    }
    </script>
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="errorMessege">${errorList }</div>

					<div class="midashi">キャラクター管理</div>
					<a href="<%= request.getContextPath() %>/chara/addCharacter"> <img src="新規登録ボタン画像.png"
						alt="キャラクター新規登録">
					</a>
				</div>
				<div class="box">
				<div class="midashi">キャラクター一覧</div>
<table border="1">
            <tr>
                <td>
                    <a href="<%= request.getContextPath() %>/chara/viewCharaDetail">
                        <img src="キャラクター画像1.png" alt="キャラクター1">
                    </a>
                    <div>キャラクター1</div>
                    <div>年齢: 20</div>
                    <div>職業: 冒険者</div>
                </td>
                <td>
                    <a href="キャラクター詳細ページ2.html">
                        <img src="キャラクター画像2.png" alt="キャラクター2">
                    </a>
                    <div>キャラクター2</div>
                    <div>年齢: 25</div>
                    <div>職業: 魔法使い</div>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="キャラクター詳細ページ3.html">
                        <img src="キャラクター画像3.png" alt="キャラクター3">
                    </a>
                    <div>キャラクター3</div>
                    <div>年齢: 30</div>
                    <div>職業: 戦士</div>
                </td>
                <td>
                    <a href="キャラクター詳細ページ4.html">
                        <img src="キャラクター画像4.png" alt="キャラクター4">
                    </a>
                    <div>キャラクター4</div>
                    <div>年齢: 22</div>
                    <div>職業: 盗賊</div>
                </td>
            </tr>
            <tr class="hidden">
                <td>
                    <a href="キャラクター詳細ページ5.html">
                        <img src="キャラクター画像5.png" alt="キャラクター5">
                    </a>
                    <div>キャラクター5</div>
                    <div>年齢: 28</div>
                    <div>職業: 騎士</div>
                </td>
                <td>
                    <a href="キャラクター詳細ページ6.html">
                        <img src="キャラクター画像6.png" alt="キャラクター6">
                    </a>
                    <div>キャラクター6</div>
                    <div>年齢: 24</div>
                    <div>職業: 僧侶</div>
                </td>
            </tr>
            <tr class="hidden">
                <td>
                    <a href="キャラクター詳細ページ7.html">
                        <img src="キャラクター画像7.png" alt="キャラクター7">
                    </a>
                    <div>キャラクター7</div>
                    <div>年齢: 26</div>
                    <div>職業: 弓使い</div>
                </td>
                <td>
                    <a href="キャラクター詳細ページ8.html">
                        <img src="キャラクター画像8.png" alt="キャラクター8">
                    </a>
                    <div>キャラクター8</div>
                    <div>年齢: 29</div>
                    <div>職業: 錬金術師</div>
                </td>
            </tr>
            <!-- 追加の行も同様に記述 -->
        </table>
        <button id="viewMoreBtn" onclick="showMore()">view more</button>
        <button id="closeBtn" class="hidden" onclick="closeRows()">close</button>
    </div>

				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>