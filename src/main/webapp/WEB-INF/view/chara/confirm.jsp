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
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="errorMessege">
					${errorList }
					</div>

					<div class="midashi">キャラクター登録</div>
					<table>
						<tr>
							<td>キャラクター名:</td>
							<td>${charaInfo.characterName}</td>
						</tr>
						<tr>
							<td>キャラクター名（フリガナ）:</td>
							<td>${charaInfo.characterNameFurigana}</td>
						</tr>
						<tr>
							<td>メモ欄:</td>
							<td>${charaInfo.memo}</td>
						</tr>
						<tr>
							<td>外部リンク:</td>
							<td>${charaInfo.externalLink}</td>
						</tr>
						<tr>
							<td>画像プレビュー:</td>
							<td><div class="preview-img"><img src="<%= request.getContextPath() %>/${charaInfo.tmpImageFilePath }" alt="の画像" /></div></td>
						</tr>
					</table>
					<form method="post" action="<%= request.getContextPath() %>/chara/completeRegistration">
						<button type="button" onclick="history.back()">戻る</button>
						<input type="submit" value="登録">
					</form>
				</div>

			</div>
		</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>