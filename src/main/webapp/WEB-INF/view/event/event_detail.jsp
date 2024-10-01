<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/event_list.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/event.css" />
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
					<div class="midashi">イベント詳細</div>
					<table class="custom-table">
						<form action="yourServletURL" method="post">
							<table>
								<tr>
									<td>イベント日時</td>
									<td><input type="text" id="eventDate" name="eventDate" value="${eadb.eventDate }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>イベントタイトル</td>
									<td><input type="text" id="eventTitle" name="eventTitle" value="${eadb.eventTitle }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
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
											<c:out  value="${eadb.detail }"/>
											</textarea></td>
								</tr>
								<tr>
									<td>募集人数</td>
									<td><input type="number" id="recruitmentNumber"
										name="recruitmentNumber" value="${eadb.memberLimit }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>参加者一覧</td>
									<td><div id="participantList" name="participantList"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:forEach items="${memberPictList }" var="memberPict" varStatus="status">
											<img class="member-icon" src="data:image/png;base64,${memberPict.value}" alt="${memberPict.key }" title="${memberPict.key }" />
											</c:forEach>
											</div></td>
								</tr>
								<tr>
									<td>申請者一覧</td>
									<td><div id="signUpList" name="signUpList"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:forEach items="${signUpMemberPictList }" var="signUpMemberPict" varStatus="status">
											<img class="member-icon" src="data:image/png;base64,${signUpMemberPict.value}" alt="${signUpMemberPict.key }" title="${memberPict.key }" />
											</c:forEach>
											</div></td>
								</tr>
								<tr>
									<td>イベントステータス</td>
									<td><input type="text" id="eventStatus" name="eventStatus" value="${eadb.statusName }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>あなたのステータス</td>
									<td><input type="text" id="yourStatus" name="yourStatus" value="${canSignUp }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
							</table>
							<c:if test="${organizerFlg}">
								<button type="submit">変更</button>
							</c:if>
						</form>
						<button onclick="location.href='<%= request.getContextPath() %>/${backTarget}?${searchQuery}'">戻る</button>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>