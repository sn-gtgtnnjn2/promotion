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
<!-- 	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/endless_scroll.js"></script> -->
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/css/event_list.css">
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="<%= request.getContextPath() %>/img/logo.png" alt="" />
		</h1>
	</header>

	<script>

	
	window.onload = function() {
	    var errorListChild = '<%=request.getAttribute("errorListChild")%>';
	    var text = '<%=request.getAttribute("profText")%>';
	    console.log(errorListChild);
	    console.log("errorListChild:" + errorListChild);
	    console.log("(errorListChild == null):" + (errorListChild == null));
	    console.log("(errorListChild === null):" + (errorListChild === null));
	    console.log("(errorListChild !== null):" + (errorListChild !== null));
	    console.log("(errorListChild == undefined):" + (errorListChild == undefined));
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
					<div class="midashi">カレンダー</div>
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
					<div class="midashi">イベント一覧</div>
					<ul class="event-list">
					
					${eventInfo.organizerImageString }
					<c:forEach items="${eventInfoList}" var="eventInfo">
					<fmt:formatDate value="${eventInfo.eventDate}" pattern="yyyy/MM/dd HH:mm" var="eventDateFormatted"/>
					<li class="event-item"
							onclick="location.href='<%= request.getContextPath() %>/event/eventView?eventId=${eventInfo.eventId }&from=${screenId }'"><span
							class="event-date"><c:out value="${eventDateFormatted}" /></span> 
							
							<img src="data:image/jpeg;base64,${eventInfo.organizerImageString }"
							alt="${eventInfo.organizerName }" class="event-organizer-icon">
							 <span class="event-status">募集中</span>
							 <span class="event-title">${eventInfo.eventTitle }</span>
							 <span class="event-organizer">${eventInfo.organizerName }</span>
							<!--  <a href="<%= request.getContextPath() %>/sseventView?eventId=${eventInfo.eventId }" class="event-edit-link"
							style="display: none;">イベント編集</a> -->
					</li>
					</c:forEach>
					</ul>
					<button id="load-more" style="display: none;">もっと見る</button>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>