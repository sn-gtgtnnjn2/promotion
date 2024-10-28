<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/event_list.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/event.css" />
<script>var ctx = "<%=request.getContextPath()%>"</script>
<script src="<%=request.getContextPath()%>/js/event_detail_confirm.js"></script>
<body>
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="<%=request.getContextPath()%>/img/logo.png" alt="" />
		</h1>
	</header>
	<main>
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="midashi">イベント詳細</div>
					<table class="custom-table">

							<table>
								<tr>
									<td>イベント日時</td>
									<td>
									<fmt:formatDate value="${eadb.eventDate}" pattern="yyyy/MM/dd(E) HH:mm" var="eventDate" />
									<input type="text" id="eventDate" name="eventDate" value="${eventDate }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>イベントタイトル</td>
									<td><input type="text" id="eventTitle" name="eventTitle"
										value="${eadb.eventTitle }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>募集日時</td>
									<td><div>
									<fmt:formatDate value="${eadb.recruitmentStartDate}" pattern="yyyy/MM/dd(E) HH:mm" var="recruitmentStartDate" />
									<fmt:formatDate value="${eadb.recruitmentEndDate}" pattern="yyyy/MM/dd(E) HH:mm" var="recruitmentEndDate" />
											<div readonly class="readonly"><c:out value="${recruitmentStartDate}" /></div>
											～
											<div readonly class="readonly"><c:out value="${recruitmentEndDate}" /></div>
										</div></td>
								</tr>
								<tr>
									<td>主催者名</td>
									<td><input type="text" id="organizerName"
										name="organizerName" value="${eadb.organizerName }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>シナリオタイトル</td>
									<td><input type="text" id="scenarioTitle"
										name="scenarioTitle" value="${eadb.scenarioTitle }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>詳細情報</td>
									<td><textarea id="details" name="details"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:out value="${eadb.detail }" />
											</textarea></td>
								</tr>
								<tr>
									<td>募集人数</td>
									<td><input type="number" id="recruitmentNumber"
										name="recruitmentNumber" value="${eadb.memberLimit }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>募集範囲</td>
									<td><div id="openLevel" name="openLevel"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											${eadb.openLevel}</div></td>
								</tr>
								<tr>
									<td>イベントステータス</td>
									<td><input type="text" id="eventStatus" name="eventStatus"
										value="${eadb.statusName }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
							</table>
							<div class="main-button-area">
								<form action="<%=request.getContextPath()%>/event/editEventConfirm" name="updateForm" id="updateForm" method="post">
									<input type="hidden" id="eventId" name="eventId" value="${eadb.eventId}" />
									<input type="hidden" id="searchQuery" name="searchQuery" value="${searchQuery}" />
									<input type="hidden" id="backTarget" name="backTarget" value="${backTarget}" />
									<button type="button" onclick="updateEventDetail();"
										<c:if test="${!eadb.overallStatus}"> </c:if>>
										更新する</button>
								</form>
								<div>
									<c:forEach items="${eadb.userRejectList}" var="userRejectList">
										<c:out value="${userRejectList}" />
									</c:forEach>
								</div>
							</div>
							<c:if test="${organizerFlg}">
								<button type="submit">変更</button>
							</c:if>

							<div class="main-button-area">
								<form action="<%=request.getContextPath()%>/event/signUpEvent" name="signUpForm" id="signUpForm">
									<input type="hidden" id="eventId" name="eventId" value="${eadb.eventId}" />
									<input type="hidden" id="searchQuery" name="searchQuery" value="${searchQuery}" />
									<input type="hidden" id="backTarget" name="backTarget" value="${backTarget}" />
								</form>
							</div>

						<c:url var="backUrl" value="${backTarget}">
    					<c:param name="searchQuery" value="${searchQuery}" />
						</c:url>
						<form action="<%=request.getContextPath()%>/event/eventViewOrg">
						<button>戻る</button>
						<input type="hidden" id="eventId" name="eventId" value="${eadb.eventId}" />
						<input type="hidden" id="searchQuery" name="searchQuery" value="${searchQuery}" />
						<input type="hidden" id="backTarget" name="backTarget" value="${backTarget}" />
						</form>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>