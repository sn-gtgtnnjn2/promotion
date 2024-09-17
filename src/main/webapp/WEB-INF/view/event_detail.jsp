<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/event_list.css" />
<body>
	<header>
		<h1>
			TRPG Resume Site<img class="logo" src="img/logo.png" alt="" />
		</h1>
	</header>
	<main>
		<div class="container">
			<div class="block">
				<div class="box">
					<div class="midashi">イベント詳細</div>
					<table class="custom-table">
						<tr>
							<th><label for="organizer">イベント日時:</label></td>
							<td>【テストデータ】2024/7/19</td>
						</tr>
						<tr>
							<th><label for="scenario">イベントタイトル:</label></td>
							<td>【テストデータ】●●で遊ぶ会</td>
						</tr>
						<tr>
							<th><label for="month">主催者:</label></td>
							<td>【テストデータ】TEST_USER</td>
						</tr>
						<tr>
							<th><label for="followersOnly">シナリオ:</label></td>
							<td>【テストデータ】●●ストーリー</td>
						</tr>
						<tr>
							<th><label for="followersOnly">詳細:</label></td>
							<td>【テストデータ】【テストデータ】【テストデータ】【テストデータ】【テストデータ】【テストデータ】【テストデータ】【テストデータ】【テストデータ】【テストデータ】</td>
						</tr>
						<tr>
							<th><label for="followersOnly">募集人数:</label></td>
							<td>【テストデータ】8人</td>
						</tr>
						<tr>
							<th><label for="followersOnly">募集ステータス:</label></td>
							<td>【テストデータ】募集中</td>
						</tr>
						<tr>
							<th><label for="followersOnly">参加希望者:</label></td>
							<td>Aさん<br>Bさん<br>Cさん<br></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>