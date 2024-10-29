<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/event_list.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/event.css" />
<script>var ctx = "<%=request.getContextPath()%>"</script>
<script src="<%=request.getContextPath()%>/js/event_detail.js"></script>
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
									<input type="text" id="eventDate" name="eventDate"
										value="${eventDate }"
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
											<input type="text" id="eventTitle" name="eventTitle" value="<c:out value="${recruitmentStartDate }" />" <c:if test="${!organizerFlg}">readonly class="readonly"</c:if>/>
											～
											<input type="text" id="eventTitle" name="eventTitle" value="<c:out value="${recruitmentEndDate }" />" <c:if test="${!organizerFlg}">readonly class="readonly"</c:if>/>
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
									<td><textarea id="details" name="details" <c:if test="${!organizerFlg}">readonly class="readonly"</c:if>><c:out value="${eadb.detail }" /></textarea></td>
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
											<c:forEach items="${memberPictList }" var="memberPict"
												varStatus="status">
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
											<c:forEach items="${signUpMemberPictList }"
												var="signUpMemberPict" varStatus="status">
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
										value="${userApprovalStatusName }"
										<c:if test="${!organizerFlg}">readonly class="readonly"</c:if> />
									</td>
								</tr>
							</table>
							<div class="main-button-area">
								<form action="<%=request.getContextPath()%>/event/signUpEvent" name="signUpForm" id="signUpForm">
									<input type="hidden" id="eventId" name="eventId" value="${eadb.eventId}" />
									<input type="hidden" id="searchQuery" name="searchQuery" value="${searchQuery}" />
									<input type="hidden" id="backTarget" name="backTarget" value="${backTarget}" />
									<c:if test="${(approveStatus != '1' )}">
									<button type="button" onclick="submitSignUpForm();"
										<c:if test="${!eadb.overallStatus}"> disabled </c:if>>
										参加表明</button>
									</c:if>
									<c:if test="${(approveStatus == '1' )}">
									 <button type="button" onclick="cancelForm(${eadb.eventId},${userId });">
										キャンセルする</button>
									</c:if>
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
							
<!-- キャラクター登録機能（承認者のみ） -->
						<c:if test="${approveStatus == '1'}">
						<div class="box">
							<div class="chara-card-area">
								<div class="midashi">登録しているキャラクター</div>
									<div>
										<!-- 登録キャラクター表示場所 -->
										<button type="button" onclick="openSearchDiv()">検索する</button>
									</div>
								<div class="" id="returnMsg"></div>
								<div class="character-container" id="characterContainer">
									<c:forEach items="${charaListForScreen}" var="chara"
										varStatus="status">
										<div class="character-card"
											onclick="viewCharacterDetails('${chara.characterId}')">
											<img
												src="<%=request.getContextPath()%>/${chara.imageFilePath}"
												alt="キャラクター画像" class="character-image">
											<c:if test="${chara.isLoginUserOwner }">
												<span class="delete-button"
													onclick="deleteCharacter(event,'${chara.characterId}')">×</span>
											</c:if>
											<p class="character-name">${chara.name }</p>
											<p class="player-name">${player.name }</p>
										</div>

									</c:forEach>
									<!-- <div id="characterContainer"></div> -->
									<!-- 他のキャラクターカードも同様に追加 -->
								</div>
								<button onclick="updateCharacterList()">キャラクター一覧を更新する
							</div>
						</div>

						<div id="overlay" style="display: none;"></div>
						<div id="childScreen" style="display: none;">
							キャラクターの名前: 
							<input type="text" id="characterName" onkeyup="fetchSuggestions()">
							<input type="hidden" id="characterId" value="${characterId }"/>
							<ul id="suggestions" style="display: none;"></ul>
							<div id="characterDetails" style="display: none;">
								<p id="characterInfo">
									<c:out value="${characterInfo }" />
								</p>
								<button type="button" onclick="registerParticipant()">参加登録する</button>
							</div>
							<button type="button" onclick="searchCharacter()">検索</button>
							<button type="button" onclick="openRegisterDiv()">新規登録</button>
						</div>
						</c:if>
<!-- キャラクター登録機能（承認者のみ）ここまで -->

						<c:url var="backUrl" value="${backTarget}">
    					<c:param name="searchQuery" value="${searchQuery}" />
						</c:url>
						<button onclick="location.href='${backUrl}'">戻る</button>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="/WEB-INF/view/footer.jsp" flush="true" />
</body>
</html>