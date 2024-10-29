<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<title>キャラクター情報</title>
    <style>
        .container {
            display: flex;
        }
        .box {
            width: 50%;
            padding: 10px;
        }
        .editable input, .editable textarea {
            width: 100%;
        }
        
        img.charaImage{
            width: 100px;
        }
        
        div.main-button-area{
        	text-align: left;
        }
    </style>
<body>
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="${request.getContextPath()}/img/logo.png" alt="" />
		</h1>
	</header>
	<main>
		<div class="container">
			<div class="block">
				<div class="box">
					<h2>キャラクター詳細情報</h2>
					<div>
						<img class="charaImage" src="${charaInfo.imagePath}"
							alt="キャラクター画像">
						<div class="main-button-area">
							<button class="" onclick="history.back()">戻る</button>
						</div>
					</div>
				</div>
			</div>
				
			<div class="block">	
				<div class="box">
					<h2>詳細情報</h2>
					<div>
						<c:if test="${userId == charaInfo.createrId}">
							<input type="text" value="${ charaInfo.name }"
								placeholder="キャラクター名称">
							<input type="text" value="${charaInfo.nameKana}"
								placeholder="キャラクターのフリガナ">
						</c:if>
						<c:if test="${userId != charaInfo.createrId}">
							<p>キャラクター名称: ${charaInfo.name}</p>
							<p>キャラクターのフリガナ: ${charaInfo.nameKana}</p>
						</c:if>
					</div>
					
					<div>
						<c:if test="${userId == charaInfo.createrId}">
						<form action="updateCharaServlet">
							<textarea id="memo" name="memo" required>${charaInfo.memo}</textarea>
							<br>
							<input type="text" value="${charaInfo.externalLink}"
								placeholder="外部リンクアドレス">
							<div class="main-button-area">
							<c:if test="${userId == charaInfo.createrId}">
								<button type="button">登録</button>
							</c:if>
							</div>
						</form>
						</c:if>
						<c:if test="${userId != charaInfo.createrId}">
							<p>詳細メモ: ${charaInfo.memo}</p>
							<p>
								外部リンク: <a href="${charaInfo.externalLink}">${charaInfo.externalLink}</a>
							</p>
						</c:if>
					</div>
					<h2>作成者</h2>
					<div>
						<img src="" alt="作成者の画像" style="width: 100%;">
					</div>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>