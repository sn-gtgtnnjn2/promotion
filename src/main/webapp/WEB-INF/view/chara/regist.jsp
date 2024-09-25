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
					<form method="post">
						<table>
							<tr>
								<td><label for="eventTitle">キャラクター名:</label></td>
								<td><input type="text" id="eventTitle" name="eventTitle" value="${eadb.eventTitle }"
									required></td>
							</tr>
							<tr>
								<td><label for="eventTitle">キャラクター名（フリガナ）:</label></td>
								<td><input type="text" id="eventTitle" name="eventTitle" value="${eadb.eventTitle }"
									required></td>
							</tr>
							<tr>
								<td><label for="eventDate">画像</label></td>
								<td><input type="datetime-local" id="eventDate" 
									name="eventDate" value="${eventDate }" required></td>
							</tr>
							<tr>
								<td><label for="scenarioTitle">メモ欄</label></td>
								<td><input type="text" id="scenarioTitle"
									name="scenarioTitle" value="${eadb.scenarioTitle }" required></td>
							</tr>
							<tr>
								<td><label for="detail">外部リンク</label></td>
								<td><textarea id="detail" name="detail" required>${eadb.detail }</textarea></td>
							</tr>
							
							<tr>
								<td colspan="2" style="text-align: center;">
									<button onclick="location.href= '<%= request.getContextPath() %>/portal'">戻る</button> 
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