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
					<div class="midashi">イベント登録確認</div>
					<form method="post" action="<%= request.getContextPath() %>/event/registEvent">
						<table>
							<tr>
								<td><label for="eventName">イベント名:</label></td>
								<td><c:out value="${eventName }" /></td>
							</tr>
							<tr>
								<td><label for="eventDate">イベント日時:</label></td>
								<fmt:formatDate value="${eventDate}" pattern="yyyy/MM/dd HH:mm" var="formattedEventDate" />
								<td><c:out value="${formattedEventDate }" /></td>
							</tr>
							<tr>
								<td><label for="organizerName">主催者名:</label></td>
								<td><c:out value="${userName }" /></td>
							</tr>
							<tr>
								<td><label for="scenarioName">シナリオ名:</label></td>
								<td><c:out value="${scenarioName }" /></td>
							</tr>
							<tr>
								<td><label for="details">詳細:</label></td>
								<td><c:out value="${details }" /></td>
							</tr>
							<tr>
								<td><label for="numberOfParticipants">募集人数:</label></td>
								<td><c:out value="${numberOfParticipants }" /></td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;">
									<button onclick="location.href= 'portal'">戻る</button> 
									<input type="submit" value="登録">
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