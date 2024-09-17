<%@page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/common-taglibs.jspf" %>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="/WEB-INF/view/header.jsp" flush="true" />
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
					<form method="post" action="userRegist">
						<div class="midashi">メールアドレスを入力してください</div>
						<table width="1000" border="0">
							<tr>
								<th>メールアドレス</th>
								<th>${email }</tr>
							<tr>
								<th>トークン</th>
								<th>${uuid }</th>
							</tr>
							<tr>
								<th>URL</th>
								<th>${url }</th>
							</tr>
							<tr>
								<th>認証期限</th>
								<th>${expiresAt }</th>
							</tr>
						</table>
						<br> 
						<input type="submit" value="仮登録メール送信" id="touroku">
					</form>
						<button onclick="location.href='index'">戻る</button>

				</div>
			</div>
		</div>
	</main>
</body>
</html>