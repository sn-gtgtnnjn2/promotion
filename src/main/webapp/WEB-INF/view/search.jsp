<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<body>
	<header>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/pager.css" />
		<h1>
			TRPG Resume Site<img class="logo" src="img/logo.png" alt="" />
		</h1>
	</header>
	<main>
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="midashi">イベント検索</div>
					<form action="/search_results" method="get">
						<table class="design10">
							<tr>
								<td><label for="organizer">イベントID:</label></td>
								<td><input type="text" id="organizer" name="organizer"></td>
							</tr>
							<tr>
								<td><label for="organizer">主催者名:</label></td>
								<td><input type="text" id="organizer" name="organizer"></td>
							</tr>
							<tr>
								<td><label for="scenario">シナリオ名:</label></td>
								<td><input type="text" id="scenario" name="scenario"></td>
							</tr>
							<tr>
								<td><label for="month">開催月:</label></td>
								<td><input type="month" id="month" name="month"></td>
							</tr>
							<tr>
								<td><label for="followersOnly">フォロワー内検索:</label></td>
								<td><input type="checkbox" id="followersOnly"
									name="followersOnly"></td>
							</tr>
						</table>
						<input type="submit" value="検索">
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
								<tr>
									<td><c:out value="${event.eventDate}" /></td>
									<td><div class="icon-img"><img src="data:image/jpeg;base64,<c:out value="${event.organizerImageString}" />"
										alt="主催者アイコン" /></td>
									</div>
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
					<script src="<%= request.getContextPath() %>/js/search_pager.js"></script>
				</div>

			</div>
		</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>