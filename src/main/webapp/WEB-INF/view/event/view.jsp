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
					<div class="midashi">イベント確認画面</div>
					<form method="post" action="<%= request.getContextPath() %>/event/registEvent">
						<hidden name="eadb" value=${eventAndDetailList }></hidden>
						<hidden name="eventTitle" value=${eventAndDetailList.scenarioTitle }></hidden>
						<hidden name="scenarioTitle" value=${eventAndDetailList.scenarioTitle }></hidden>
						<hidden name="detail" value=${eventAndDetailList.detail }></hidden>
						<hidden name="memberLimit" value=${eventAndDetailList.memberLimit }></hidden>
						<hidden name="recruitmentStartDate" value=${eventAndDetailList.recruitmentStartDate }></hidden>
						<hidden name="recruitmentEndDate" value=${eventAndDetailList.recruitmentEndDate }></hidden>
						<hidden name="eventDate" value=${eventAndDetailList.eventDate }></hidden>

						<table>
							<tr>
								<td><label for="eventTitle">イベント名:</label></td>
								<td><c:out value="${eventAndDetailList.eventTitle }" /></td>
							</tr>
							<tr>
								<td><label for="eventDate">イベント日時:</label></td>
								<td><c:out value="${eventAndDetailList.eventDate }" /></td>
							</tr>
							<tr>
								<td><label for="organizerName">主催者名:</label></td>
								<td><c:out value="${eventAndDetailList.organizerName }" /></td>
							</tr>
							<tr>
								<td><label for="scenarioTitle">シナリオ名:</label></td>
								<td><c:out value="${eventAndDetailList.scenarioTitle }" /></td>
							</tr>
							<tr>
								<td><label for="detail">詳細:</label></td>
								<td><c:out value="${eventAndDetailList.detail }" /></td>
							</tr>
							<tr>
								<td><label for="memberLimit">募集人数:</label></td>
								<td><c:out value="${eventAndDetailList.memberLimit }" /></td>
							</tr>
							<tr>
								<td><label for="memberLimit">募集開始日時:</label></td>
								<td><c:out value="${eventAndDetailList.recruitmentStartDate }" /></td>
							</tr>
							<tr>
								<td><label for="memberLimit">募集終了日時:</label></td>
								<td><c:out value="${eventAndDetailList.recruitmentEndDate }" /></td>
							</tr>
							<tr>
								<td><label for="openLevel">公開範囲:</label></td>
								<td>
								<c:if test="${eventAndDetailList.openLevel == 0 }">全体</c:if>
								<c:if test="${eventAndDetailList.openLevel == 1 }">フォロワーのみ</c:if>
								<c:if test="${eventAndDetailList.openLevel == 2 }">自分のみ</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: center;">
									<button type="button" onclick="location.href= '<%= request.getContextPath() %>/event/addEvent'">戻る</button> 
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