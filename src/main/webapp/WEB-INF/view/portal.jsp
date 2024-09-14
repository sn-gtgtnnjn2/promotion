<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="header.jsp" flush="true" />
<body>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/calender.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/modal.js"></script>
	
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="img/logo.png" alt="" />
		</h1>
	</header>
	
	<main>
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="midashi">${userName}</div>
					<div class="profile_img image-container">
						<img src="<%=request.getContextPath()%>/icon/testicon.png"
							alt="【プロフィール名を表示】">
						<div class="edit-icon" onclick="openForm()">
							<img src="<%=request.getContextPath()%>/img/pen.png"
								style="width: 25px;" alt="" />
						</div>
					</div>
					<div class="overlay" id="overlay"></div>
					<div id="form-container" class="form-container">
						<form>
							<input type="file" id="image-input" name="profile-image"
								accept="image/*" onchange="previewImage(event)"> <img
								id="image-preview" src="" alt="プレビュー画像" style="display: none;">
							<button type="submit">アップロード</button>
						</form>
					</div>
				</div>
				<div class="box">
					<div class="midashi">プロフィール【ラベル】</div>
					<p>AA 自己紹介メモ</p>
				</div>
			</div>
			<div class="block">
				<div class="box">
					<div class="midashi">カレンダー【ラベル】</div>
					<div id="calendar">
						<div class="calendar-header">
							<span id="prev" class="arrow">&#9664;</span> <span
								id="month-year"></span> <span id="next" class="arrow">&#9654;</span>
						</div>
					</div>
				</div>
				<div class="box">
					<div class="midashi">イベント一覧【ラベル】</div>
					<ul class="sample">
						<li>HTMLHTML</li>
						<li>CSS</li>
					</ul>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>