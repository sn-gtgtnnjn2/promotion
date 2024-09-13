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
					<form method="post" action="preUserRegist">
						<div class="midashi">メールアドレスを入力してください</div>
						<table width="1000" border="0">
							<tr>
								<th>メールアドレス</th>
								<th><input type="text" maxlength="254"
									placeholder="aaaa@gmail.com" id="mail" name="mail"></th>
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