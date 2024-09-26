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
    <c:forEach var="charaInfo" items="${charaInfoList}" varStatus="status">
        <c:if test="${status.index % 2 == 0}">
            <tr class="${status.index >= 8 ? 'hidden' : ''}">
        </c:if>
            <td>
                <a href="">
                	<div class="preview-img">
                    <img src="<%= request.getContextPath() %>/${charaInfo.imageFilePath}" alt="${charaInfo.characterName}">
                    </div>
                </a>
                <div>名前：<c:out value= "${charaInfo.characterName}" />(<c:out value="${charaInfo.characterNameFurigana}" />)</div>
                <div>外部リンク: ${charaInfo.externalLink}</div>
            </td>
        <c:if test="${status.index % 2 == 1}">
            </tr>
        </c:if>
            <!-- もしキャラクターの数が奇数の場合、最後の行を閉じる -->
    <c:if test="${characters.size() % 2 != 0}">
        </tr>
    </c:if>
    </c:forEach>
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