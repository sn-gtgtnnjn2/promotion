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
				
					<div class="midashi">イベント登録</div>
					<form method="post">
						<table>
							<tr>
								<td><label for="eventTitle">イベント名:</label></td>
								<td><input type="text" id="eventTitle" name="eventTitle" value="${eadb.eventTitle }"
									required></td>
							</tr>
							<tr>
								<td><label for="eventDate">イベント日時:</label></td>
								<td><input type="datetime-local" id="eventDate" 
									name="eventDate" value="${eventDate }" required></td>
							</tr>
							<tr>
								<td><label for="scenarioTitle">シナリオ名:</label></td>
								<td><input type="text" id="scenarioTitle"
									name="scenarioTitle" value="${eadb.scenarioTitle }" required></td>
							</tr>
							<tr>
								<td><label for="detail">詳細:</label></td>
								<td><textarea id="detail" name="detail" required>${eadb.detail }</textarea></td>
							</tr>
							<tr>
								<td><label for="memberLimit">募集人数:</label></td>
								<td><input type="number" id="memberLimit"
									name="memberLimit" value="${eadb.memberLimit }" required></td>
							</tr>
							<tr>
								<td><label for="recruitmentStartDate">募集開始日時:</label></td>
								<td><input type="datetime-local" id="recruitmentStartDate" name="recruitmentStartDate" value="${recruitmentStartDate }"></td>
							</tr>
							<tr>
								<td><label for="recruitmentEndDate">募集終了日時:</label></td>
								<td><input type="datetime-local" id="recruitmentEndDate" name="recruitmentEndDate" value="${recruitmentEndDate }"></td>
							</tr>
							<tr>
								<td><label for="memberLimit" value="${eadb.memberLimit }">公開範囲:</label></td>
								<td>
									<p id="openLevel" class="radio">
										<input type="radio" name="openLevel" id="all" value="0" <c:if test="${eadb.openLevel == 0 }">checked="checked"</c:if><c:if test="${empty openLevel}">checked="checked"</c:if>>
										<label for="all">全体</label>
										<input type="radio" name="openLevel" id="follower" value="1" <c:if test="${eadb.openLevel == 1 }">checked="checked"</c:if>>
										<label for="follower" >フォロワーのみ</label>
										<input type="radio" name="openLevel" id="private" value="2" <c:if test="${eadb.openLevel == 2 }">checked="checked"</c:if>>
										<label for="private">自分のみ</label>
										
									</p>
								</td>
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