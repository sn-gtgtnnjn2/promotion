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
				<div class="errorMessege">${errorList }</div>
				<div class="box">
					<h1>ログイン</h1>
					<form method="post">
						<input type="text" name="userId" placeholder="ユーザー名" required>
						<br> 
						<input type="password" name="password" placeholder="パスワード" required>
						<br>
						<input type="submit" value="ログイン">
					</form>
					<a href="preUserRegist">新規会員登録はこちら。</a>

				</div>
			</div>
		</div>
	</main>
</body>
</html>