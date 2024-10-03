<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/event_list.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/event.css" />

<script src="<%= request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
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

							<table>
								<tr>
									<td>イベント日時</td>
									<td><input type="text" id="eventDate" name="eventDate"
										value="${eadb.eventDate }"
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
											<c:out value="${eadb.recruitmentStartDate }" />
											～
											<c:out value="${eadb.recruitmentEndDate }" />
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
									<td>参加者一覧</td>
									<td><div id="participantList" name="participantList"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:forEach items="${memberPictList }" var="memberPict" varStatus="status">
												<img class="member-icon"
													src="data:image/png;base64,${memberPict.value}"
													alt="${memberPict.key }" title="${memberPict.key }" />
											</c:forEach>
										</div></td>
								</tr>
								<tr>
									<td>申請者一覧</td>
									<td><div id="signUpList" name="signUpList"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:forEach items="${signUpMemberPictList }" var="signUpMemberPict" varStatus="status">
												<img class="member-icon"
													src="data:image/png;base64,${signUpMemberPict.value}"
													alt="${signUpMemberPict.key }" title="${memberPict.key }" />
											</c:forEach>
										</div></td>
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
								<tr>
									<td>あなたのステータス</td>
									<td><input type="text" id="yourStatus" name="yourStatus"
										value="${canSignUp }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> />
									</td>
								</tr>
							</table>
							<div class="main-button-area">
								<form action="<%= request.getContextPath() %>/event/signUpEvent" name="signUpForm" id="signUpForm">
									<input type="hidden" id="eventId" name="eventId" value="${eadb.eventId}" />
									<input type="hidden" id="searchQuery" name="searchQuery" value="${searchQuery}" />
									<input type="hidden" id="backTarget" name="backTarget" value="${backTarget}" />
									<button type="button" onclick="submitSignUpForm();"
										<c:if test="${!eadb.overallStatus}"> disabled </c:if>>
										参加表明</button>
								</form>
								<div>
									<c:forEach items="${eadb.userRejectList}" var="userRejectList">
										<c:out value="${userRejectList}" />
									</c:forEach>
								</div>
							</div>
							
<!-- イベント作成者固有エリア -->
  <form id="user-management-form" method="POST" action="<%= request.getContextPath() %>/event/eventViewOrg">
<div class="user-management-container">
    <div class="user-select-box">
      <div>承認済みユーザー</div>
      <div class="custom-select-list" id="approved-users">
        <!-- 承認済みユーザーのリストがここに表示されます -->
      </div>
    </div>
    <div class="action-buttons">
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('pending-users', 'approved-users');"><i class="bi bi-chevron-double-left"></i>← 承認</button>
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('approved-users', 'pending-users');">却下 →<i class="bi bi-chevron-double-right"></i></button>
    </div>
    <div class="user-select-box">
      <div class="text-muted">申請中ユーザー</div>
      <div class="custom-select-list" id="pending-users">
      	<c:forEach items="${memberPictList }" var="memberPict" varStatus="status">

        	<div class="select-item" data-value="1"><img class="member-icon" src="data:image/png;base64,${memberPict.value}" alt="${memberPict.key }" title="${memberPict.key }" /> ${memberPict.key }</div>
      	</c:forEach>
      </div>
    </div>
    <div class="action-buttons">
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('rejected-users', 'pending-users');"><i class="bi bi-chevron-double-left"></i>← 承認</button>
      <button type="button" class="btn btn-outline-secondary" onclick="moveGroup('pending-users', 'rejected-users');">却下 →<i class="bi bi-chevron-double-right"></i></button>
    </div>
    <div class="user-select-box">
      <div class="text-muted">申請中ユーザー</div>
      <div class="custom-select-list" id="rejected-users">

      </div>
    </div>
    <!-- Hidden fields to store the selected values -->
    <div id="approvedUsers"></div>
    <div id="pendingUsers"></div>
    <div id="rejectedUsers"></div>
</div>
    <button type="submit" class="btn btn-primary" onclick="prepareHiddenFields();">登録</button>
<div>
</div>
  </form>
<div class="row m-3">
<!--
 <div class="col-md-3">
    <button type="button" class="btn btn-primary" onclick="showSelectValues();">選択を表示</button>
</div>
 -->
</div>
							<c:if test="${organizerFlg}">
								<button type="submit">変更</button>
							</c:if>

						<c:url var="backUrl" value="${backTarget}">
    					<c:param name="searchQuery" value="${searchQuery}" />
						</c:url>
						<button onclick="location.href='${backUrl}'">戻る</button>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
	<script src="<%= request.getContextPath() %>/js/event_detail_org.js"></script>
</body>
</html>