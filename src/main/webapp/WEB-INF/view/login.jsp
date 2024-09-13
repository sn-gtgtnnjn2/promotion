<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<jsp:include page="header.jsp" flush="true" />
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
					<h1>ログイン</h1>
					<form method="post" action="portal">
						<input type="text" name="username" placeholder="ユーザー名" required>
						<br> 
						<input type="password" name="password" placeholder="パスワード" required>
						<br>
						<input type="submit" value="ログイン">
					</form>
					<a href="userRegist">新規会員登録はこちら。</a>

				</div>
			</div>
		</div>
	</main>
</body>
</html>