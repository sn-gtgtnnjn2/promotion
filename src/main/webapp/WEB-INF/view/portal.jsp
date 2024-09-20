<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<body>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/calender.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/modal.js"></script>
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/css/event_list.css">
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="img/logo.png" alt="" />
		</h1>
	</header>

	<script>

	
	window.onload = function() {
	    var errorListChild = '<%=request.getAttribute("errorListChild")%>';
	    var text = '<%=request.getAttribute("profText")%>';
	    console.log(errorListChild);
	    console.log("errorListChild:" + errorListChild);
	    console.log("(errorListChild !== null):" + (errorListChild !== null));
	    console.log(!(errorListChild !== null));

	    if (errorListChild !== "null" && errorListChild !== null && errorListChild !== "" && errorListChild.trim().length > 0) {
	        console.log(errorListChild.length);
	        openEditDialogWithError('<%=request.getAttribute("profText")%>');
			}
		};
	</script>

	<main>
		<div class="container">
			<div class="block">
				<div>${errorList }</div>
				
				<div class="box">
					<div class="midashi">${userName}</div>
					<div class="profile_img image-container">
						<c:if test="${empty strImageData }">
							<img src="<%=request.getContextPath()%>/icon/testicon.png"
								alt="<c:out value="${userName }の画像" />">
						</c:if>
						<c:if test="${!empty strImageData }">
							<img src="data:image/jpeg;base64,${strImageData }"
								alt="<c:out value="${userName }の画像" />">
						</c:if>
						<div class="edit-icon" onclick="openForm()">
							<img src="<%=request.getContextPath()%>/img/pen.png"
								style="width: 25px;" alt="" />
						</div>
					</div>
					<div class="overlay" id="overlay"></div>
					<div id="form-container" class="form-container">
						<form method="post" action="upload" enctype="multipart/form-data">
							<input type="hidden" value="${userId }" name="userId" />
							<div>新しいプロフィール画像</div>
							<input type="file" id="image-input" name="profile-image"
								accept="image/*" onchange="previewImage(event)"> <img
								id="image-preview" src="" alt="プレビュー画像" style="display: none;">
							<button type="submit">アップロード</button>
						</form>
					</div>
				</div>
				<div class="box">
					<div class="midashi">
						プロフィール
						<div class="edit-icon-display" onclick="openEditDialog()">
							<img src="<%=request.getContextPath()%>/img/pen.png"
								style="width: 25px;" alt="" />
						</div>
					</div>
					<div class="overlay" id="prof-text-overlay"></div>
					<div id="prof-text-container" class="form-container">
						<form action="changePrfText" method="post">
							<div>
								<label for="profText">本文内容</label>
							</div>
							<div class="errorListChild" id="errorListChild"
								name="errorListChild">
								<c:out value="${errorListChild }" />
							</div>
							<div>
								<textarea class="prof-input" rows="4" cols="16" maxlength="200" wrap="soft"
									id="profText" name="profText"><c:out
										value="${profText}" /></textarea>
							</div>
							<div>
								<button type="submit">編集</button>
							</div>
						</form>
					</div>
					<div>
						<c:out value="${profText}" />
					</div>
				</div>
								<div class="box">
					<div class="midashi">カレンダー【ラベル】</div>
					<div id="calendar">
						<div class="calendar-header">
							<span id="prev" class="arrow">&#9664;</span> <span
								id="month-year"></span> <span id="next" class="arrow">&#9654;</span>
						</div>
					</div>
				</div>
			</div>
			<div class="block">

				<div class="box">
					<div class="midashi">イベント一覧【ラベル】</div>
					
					<ul id="event-list" class="event-list">
						<li class="event-item"
							onclick="location.href='event-details.html'"><span
							class="event-date">2024-09-25</span> <img src="organizer1.jpg"
							alt="主催者アイコン" class="event-organizer-icon"> <span
							class="event-status">募集中</span> <span class="event-title">秋の音楽祭</span>
							<span class="event-organizer">音楽協会</span> <a
							href="event-edit.html" class="event-edit-link"
							style="display: none;">イベント編集</a></li>
						<li class="event-item"
							onclick="location.href='event-details.html'"><span
							class="event-date">2024-10-10</span> <img src="organizer2.jpg"
							alt="主催者アイコン" class="event-organizer-icon"> <span
							class="event-status">開催待ち</span> <span class="event-title">ハロウィンパーティー</span>
							<span class="event-organizer">地域コミュニティ</span> <a
							href="event-edit.html" class="event-edit-link"
							style="display: none;">イベント編集</a></li>
						<!-- 他のイベントも同様に追加 -->
					</ul>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>