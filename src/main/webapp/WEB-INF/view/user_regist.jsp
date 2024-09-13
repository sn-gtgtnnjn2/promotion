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
					<form method="post" action="userRegist">
						<div class="midashi">会員情報を入力してください</div>
						<table width="1000" border="0">
							<tr>
								<th>ユーザー名</th>
								<th><input type="text" maxlength="100" placeholder="山田太郎"
									id="name"></th>
							</tr>
							<tr>
								<th>ユーザー名(ふりがな)</th>
								<th><input type="text" maxlength="200" placeholder="やまだたろう"
									id="name_huri"></th>
							</tr>
							<tr>
								<th>メールアドレス</th>
								<th><input type="text" maxlength="254"
									placeholder="aaaa@gmail.com" id="mail"></th>
							</tr>
							<tr>
								<th>パスワード</th>
								<th><input type="password" maxlength="20"
									placeholder="半角英数8文字以上" id="pass"></th>
							</tr>
							<tr>
								<th>パスワード再入力</th>
								<th><input type="password" maxlength="20"
									placeholder="半角英数8文字以上" id="repass"></th>
							</tr>
						</table>
						<br> 
						<input type="submit" value="会員登録を行う" id="touroku">
					</form>
						<button onclick="location.href='index'">戻る</button>

				</div>
			</div>
		</div>
	</main>
</body>
</html>