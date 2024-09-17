<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />

<body>
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="img/logo.png" alt="" />
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
								<td><label for="eventName">イベント名:</label></td>
								<td><input type="text" id="eventName" name="eventName" value="${eventName }"
									required></td>
							</tr>
							<tr>
								<td><label for="eventDate">イベント日時:</label></td>
								<td><input type="datetime-local" id="eventDate" 
									name="eventDate" value="${eventDate }" required></td>
							</tr>
							<tr>
								<td><label for="organizerName">主催者名:</label></td>
								<td><input type="text" id="organizerName"
									name="organizerName" value="${userName }" required></td>
							</tr>
							<tr>
								<td><label for="scenarioName">シナリオ名:</label></td>
								<td><input type="text" id="scenarioName"
									name="scenarioName" value="${scenarioName }" required></td>
							</tr>
							<tr>
								<td><label for="details">詳細:</label></td>
								<td><textarea id="details" name="details" value="${details }" required></textarea></td>
							</tr>
							<tr>
								<td><label for="numberOfParticipants">募集人数:</label></td>
								<td><input type="number" id="numberOfParticipants"
									name="numberOfParticipants" value="${numberOfParticipants }" required></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;">
									<button onclick="location.href= 'portal'">戻る</button> 
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