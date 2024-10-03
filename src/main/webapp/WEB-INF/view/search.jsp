<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<body>
	<header>
		<script src="<%=request.getContextPath()%>/js/search_pager.js"></script>
		<script src="<%=request.getContextPath()%>/js/event_view.js"></script>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/event.css" />
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/pager.css" />
		<h1>
			TRPG Resume Site<img class="logo" src="<%= request.getContextPath() %>/img/logo.png" alt="" />
		</h1>
	</header>
	<main>
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="midashi">イベント検索</div>
					<form action="search" method="get">
						<table class="design10">
							<tr>
								<td><label for="eventTitle">イベント名:</label></td>
								<td><input type="text" id="eventTitle" name="eventTitle" value="<c:out value="${eventTitle }" />">
									
								</td>
							</tr>
							<tr>
								<td><label for="organizerName">主催者名:</label></td>
								<td><input type="text" id="organizerName" name="organizerName" value="<c:out value="${organizerName }" />">
								
								</td>
							</tr>
							<tr>
								<td><label for="scenarioTitle">シナリオ名:</label></td>
								<td><input type="text" id="scenarioTitle" name="scenarioTitle" value="<c:out value="${scenarioTitle }" />">
								
								</td>
							</tr>
							<tr>
								<td><label for="eventDate">開催月:</label></td>
								<td><input type="month" id="eventDate" name="eventDate" value="<c:out value="${eventDate }" />"></td>
							</tr>
							<tr>
								<td><label for="followersOnly">フォロワー内検索:</label></td>
								<td><input type="checkbox" id="followersOnly"
									name="followersOnly" <c:if test="${followersOnly }">checked</c:if>></td>
							</tr>
							<tr><td colspan="2" class="search-button"><input type="submit" value="検索"></td></tr>
						</table>
						
					</form>
				</div>
				<div class="box-search">
					<div class="midashi">検索結果</div>
					<table class="design10 custom-table" id="eventTable">
						<tbody>
							<tr>
								<th>日付</th>
								<th>主催者</th>
								<th>イベントタイトル</th>
								<th>シナリオ名</th>
								<th>ステータス</th>
								<th>応募締切</th>
								<th>参加人数/募集人数</th>
							</tr>
							<c:forEach var="event" items="${eventInfoList}">
								<c:if test="${!event.organizerFlg }">
									<tr  data-href="<%= request.getContextPath() %>/event/eventView?eventId=${event.eventId}&from=${screenId}&${searchQuery}" class="event-row">							
								</c:if>
								<c:if test="${event.organizerFlg }">
									<tr  data-href="<%= request.getContextPath() %>/event/eventViewOrg?eventId=${event.eventId}&from=${screenId}&${searchQuery}" class="event-row">							
								</c:if>
									<td><c:out value="${event.eventDate}" /></td>
									<td><div class="icon-img"><img src="data:image/jpeg;base64,<c:out value="${event.organizerImageString}" />"
										alt="主催者アイコン" />
									</div>
									<div><c:out value="${event.organizerName }"/></div>
									</td>
									<td><c:out value="${event.eventTitle}" /></td>
									<td><c:out value="${event.scenarioTitle}" /></td>
									<td><c:out value="${event.statusName}" /></td>
									<td><c:out value="${event.recruitmentEndDate}" /></td>
									<td><c:out value="${event.currentApprovedNum}" /> / <c:out
											value="${event.currentSignUpNum}" /></td>
								</tr>
							</c:forEach>
							<!-- 他のレコードも同様に追加 -->
						</tbody>
					</table>
					<div id="pagination"></div>
				</div>

			</div>
		</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>