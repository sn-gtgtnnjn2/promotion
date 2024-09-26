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
					<form method="post" enctype="multipart/form-data">
						<table>
							<tr>
								<td><label for="characterName">キャラクター名:</label></td>
								<td><input type="text" id="characterName"
									name="characterName" value="${charaInfo.characterName}" required></td>
							</tr>
							<tr>
								<td><label for="characterNameFurigana">キャラクター名（フリガナ）:</label></td>
								<td><input type="text" id="characterNameFurigana"
									name="characterNameFurigana"
									value="${charaInfo.characterNameFurigana}" required></td>
							</tr>
							<tr>
								<td><label for="imageUpload">画像</label></td>
								<td><input type="file" id="imageUpload" name="imageUpload"
									accept="image/*" required></td>
							</tr>
							<tr>
								<td><label for="memo">メモ欄</label></td>
								<td><textarea id="memo" name="memo" required>${charaInfo.memo}</textarea></td>
							</tr>
							<tr>
								<td><label for="externalLink">外部リンク</label></td>
								<td><input type="url" id="externalLink" name="externalLink"
									value="${charaInfo.externalLink}" required></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;">
									<button type="button"
										onclick="location.href='<%=request.getContextPath()%>/portal'">戻る</button>
									<input type="submit" value="確認画面へ">
								</td>
							</tr>
						</table>
					</form>
				</div>

			</div>
		</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>