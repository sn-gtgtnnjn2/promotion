<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/event_list.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/event.css" />

<script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
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
					<form id="user-management-form" method="GET"
						action="<%=request.getContextPath()%>/event/editEventConfirm" onsubmit="return validateEventForm()">
						<table class="custom-table">
							<table>
								<tr>
									<td>イベント日時</td>
									<td>
									<fmt:formatDate value="${eadb.eventDate}" pattern="yyyy/MM/dd(E)" var="eventDate" />
									<fmt:formatDate value="${eadb.eventDate}" pattern="HH:mm" var="eventTime" />
										<div>
											<input type="text" id="eventDate" name="eventDate"
												maxlength="8" placeholder="例: 20241009" value="${eventDate}">
											<span class="description">YYYYMMDDで入力</span>
											<div id="date-error" class="error-message">入力値が不正です</div>
										</div>
										<div>
											<input type="text" id="eventTime" name="eventTime"
												maxlength="4" placeholder="例: 1900" value="${eventTime}">
											<span class="description">HHMMで入力</span>
											<div id="time-error" class="error-message">入力値が不正です</div>
										</div>
									</td>

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
											<fmt:formatDate value="${eadb.recruitmentStartDate}"
												pattern="yyyy/MM/dd(E)" var="recruitmentStartDate" />
											<fmt:formatDate value="${eadb.recruitmentStartDate}"
												pattern="HH:mm" var="recruitmentStartTime" />
											<input type="text" id="recruitmentStartDate" name="recruitmentStartDate" maxlength="8"
												placeholder="例: 20241009" value="<c:out value="${recruitmentStartDate }" />">
											<div id="date-error-recruitmentStartDate" class="error-message">開始日が不正です</div>
											<input type="text" id="recruitmentStartTime" name="recruitmentStartTime" maxlength="8"
												placeholder="例: 1930" value="<c:out value="${recruitmentStartTime }" />">
											<div id="time-error-recruitmentStartTime" class="error-message">開始時刻が不正です</div>
											<br>～<br>
											<fmt:formatDate value="${eadb.recruitmentEndDate}"
												pattern="yyyy/MM/dd(E)" var="recruitmentEndDate" />
											<fmt:formatDate value="${eadb.recruitmentEndDate}"
												pattern="HH:mm" var="recruitmentEndTime" />
											<input type="text" id="recruitmentEndDate" name="recruitmentEndDate" maxlength="8"
												placeholder="例: 20241009" value="<c:out value="${recruitmentEndDate }" />">
											<div id="date-error-recruitmentEndDate" class="error-message">終了日が不正です</div>
											<input type="text" id="recruitmentEndTime" name="recruitmentEndTime" maxlength="8"
												placeholder="例: 1930" value="<c:out value="${recruitmentEndTime }" />">
											<div id="time-error-recruitmentEndTime" class="error-message">終了時刻が不正です</div>
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
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>><c:out
												value="${eadb.detail }" /></textarea></td>
								</tr>
								<tr>
									<td>募集人数</td>
									<td><input type="number" id="memberLimit"
										name="memberLimit" value="${eadb.memberLimit }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> /></td>
								</tr>
								<tr>
									<td>参加者一覧</td>
									<td><div id="participantList" name="participantList"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:forEach items="${approveMemberPictList }"
												var="approveMemberPict" varStatus="status">
												<c:if test="${empty approveMemberPict.imageString}">
													<img src="<%=request.getContextPath()%>/icon/testicon.png"
														alt="${signUpMemberPict.userName }"
														title="${signUpMemberPict.userName }" />">
												</c:if>
												<c:if test="${!empty approveMemberPict.imageString}">
													<img class="member-icon"
														src="data:image/png;base64,${approveMemberPict.imageString}"
														alt="${approveMemberPict.userName }"
														title="${approveMemberPict.userName }" />
												</c:if>
											</c:forEach>
										</div></td>
								</tr>
								<tr>
									<td>申請者一覧</td>
									<td><div id="signUpList" name="signUpList"
											<c:if test="${!organizerFlg}">readonly class="readonly"</c:if>>
											<c:forEach items="${signUpMemberPictList }"
												var="signUpMemberPict" varStatus="status">
												<c:if test="${empty signUpMemberPict.imageString}">
													<img src="<%=request.getContextPath()%>/icon/testicon.png"
														alt="${signUpMemberPict.userName }"
														title="${signUpMemberPict.userName }" />">
												</c:if>
												<c:if test="${!empty signUpMemberPict.imageString}">
													<img class="member-icon"
														src="data:image/png;base64,${signUpMemberPict.imageString}"
														alt="${signUpMemberPict.userName }"
														title="${signUpMemberPict.userName }" />
												</c:if>
											</c:forEach>
										</div></td>
								</tr>
								<tr>
									<td>募集範囲</td>
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
									<td>イベントステータス</td>
									<td><input type="text" id="eventStatusName" name="eventStatusName"
										value="${eadb.statusName }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> />
									</td>
									<input type="hidden" name="eventStatus" value="${eadb.status }"/>
								</tr>
								<!-- 
								<tr>
									<td>あなたのステータス</td>
									<td><input type="text" id="yourStatus" name="yourStatus"
										value="${canSignUp }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> />
									</td>
								</tr>
								-->
							</table>
							<div class="main-button-area">
								<input type="hidden" id="eventId" name="eventId"
									value="${eadb.eventId}" /> <input type="hidden"
									id="searchQuery" name="searchQuery" value="${searchQuery}" />
								<input type="hidden" id="backTarget" name="backTarget"
									value="${backTarget}" />
								<c:if test="${organizerFlg}">
									<button type="button">編集前の状態に戻す</button>
									<button type="submit">編集内容を反映する</button>
								</c:if>
								<div>
									<c:forEach items="${eadb.userRejectList}" var="userRejectList">
										<c:out value="${userRejectList}" />
									</c:forEach>
								</div>
							</div>
					</form>

					<!-- イベント作成者固有エリア -->
							<div class="midashi">参加者管理</div>
							<div class="user-management-container">
								<div class="user-select-box">
									<div>承認済みユーザー</div>
									<div class="custom-select-list" id="approved-users">
										<!-- 承認済みユーザーのリストがここに表示されます -->
										<c:forEach items="${approveMemberPictList }" var="memberPict"
											varStatus="status">
											<div class="select-item" data-value="${memberPict.userId }">
												<c:if test="${!empty  memberPict.imageString}">
													<img class="member-icon"
														src="data:image/png;base64,${memberPict.imageString}"
														alt="${memberPict.userName }"
														title="${memberPict.userName }" />
												</c:if>
												<c:if test="${empty  memberPict.imageString}">
													<img src="<%=request.getContextPath()%>/icon/testicon.png"
														alt="${signUpMemberPict.userName }"
														title="${signUpMemberPict.userName }" />">
												</c:if>
												${memberPict.userName }
											</div>
										</c:forEach>
									</div>
								</div>

								<div class="action-buttons">
									<button type="button" class="btn btn-outline-secondary"
										onclick="moveGroup('pending-users', 'approved-users');">
										<i class="bi bi-chevron-double-left"></i>← 承認
									</button>
									<button type="button" class="btn btn-outline-secondary"
										onclick="moveGroup('approved-users', 'pending-users');">
										却下 →<i class="bi bi-chevron-double-right"></i>
									</button>
								</div>

								<div class="user-select-box">
									<div class="text-muted">申請中ユーザー</div>
									<div class="custom-select-list" id="pending-users">
										<c:forEach items="${signUpMemberPictList }" var="memberPict"
											varStatus="status">

											<div class="select-item" data-value="${memberPict.userId }">
												<c:if test="${!empty  memberPict.imageString}">
													<img class="member-icon"
														src="data:image/png;base64,${memberPict.imageString}"
														alt="${memberPict.userName }"
														title="${memberPict.userName }" />
												</c:if>
												<c:if test="${empty memberPict.imageString}">
													<img src="<%=request.getContextPath()%>/icon/testicon.png"
														alt="${memberPict.userName }"
														title="${memberPict.userName }" />
												</c:if>
												${memberPict.userName }
											</div>
										</c:forEach>
									</div>
								</div>

								<div class="action-buttons">
									<button type="button" class="btn btn-outline-secondary"
										onclick="moveGroup('rejected-users', 'pending-users');">
										<i class="bi bi-chevron-double-left"></i>← 承認
									</button>
									<button type="button" class="btn btn-outline-secondary"
										onclick="moveGroup('pending-users', 'rejected-users');">
										却下 →<i class="bi bi-chevron-double-right"></i>
									</button>
								</div>

								<div class="user-select-box">
									<div class="text-muted">申請中ユーザー</div>
									<div class="custom-select-list" id="rejected-users"></div>
								</div>
								
								<form method="post" id="addPlayerForm" name="addPlayerForm">
								<!-- Hidden fields to store the selected values -->
								<div id="approvedUsers"></div>
								<div id="pendingUsers"></div>
								<div id="rejectedUsers"></div>
								</form>
							</div>

							<div class="main-button-area">
								<input type="hidden" id="searchQuery" name="searchQuery"
									value="${searchQuery}" /> <input type="hidden" id="backTarget"
									name="backTarget" value="${backTarget}" /> <input type="hidden"
									id="eventId" name="eventId" value="${eventId}" />
								<button type="submit" class="btn btn-primary"
									onclick="prepareHiddenFields();">登録</button>
							</div>
							<div></div>
							<div class="row m-3">
								<!--
 <div class="col-md-3">
    <button type="button" class="btn btn-primary" onclick="showSelectValues();">選択を表示</button>
</div>
 -->
							</div>

							<c:url var="backUrl" value="${backTarget}">

								<c:param name="eventId" value="${eventId}" />
							</c:url>
							<button onclick="location.href='${backUrl}${searchQuery}'">戻る</button>
							</div>
							</div>
				</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
	<script src="<%=request.getContextPath()%>/js/event_detail_org.js"></script>
</body>
</html>