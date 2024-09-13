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
					<div class="midashi">イベント検索</div>
					<form action="/search_results" method="get">
						<table>
							<tr>
								<td><label for="organizer">イベントID:</label></td>
								<td><input type="text" id="organizer" name="organizer"></td>
							</tr>
							<tr>
								<td><label for="organizer">主催者名:</label></td>
								<td><input type="text" id="organizer" name="organizer"></td>
							</tr>
							<tr>
								<td><label for="scenario">シナリオ名:</label></td>
								<td><input type="text" id="scenario" name="scenario"></td>
							</tr>
							<tr>
								<td><label for="month">開催月:</label></td>
								<td><input type="month" id="month" name="month"></td>
							</tr>
							<tr>
								<td><label for="followersOnly">フォロワー内検索:</label></td>
								<td><input type="checkbox" id="followersOnly"
									name="followersOnly"></td>
							</tr>
						</table>
						<input type="submit" value="検索">
					</form>
				</div>
				<div class="box-search">
					<div class="midashi">検索結果</div>
					<table class="design10">
						<tr>
							<th>イベントID</th>
							<th>日付</th>
							<th>主催者</th>
							<th>シナリオ名</th>
							<th>フォロワーかどうか</th>
							<th>イベントタイトル</th>
							<th>募集締切</th>
						</tr>
						<tr>
							<td>1</td>
							<td>2024-05-24</td>
							<td>ABC主催</td>
							<td>冒険のシナリオ</td>
							<td>はい</td>
							<td><a href="eventDetail">大冒険の会</a></td>
							<td>2024-06-10</td>
						</tr>
						<tr>
							<td>9</td>
							<td>2024-05-24</td>
							<td>ABC主催</td>
							<td>冒険のシナリオ</td>
							<td>はい</td>
							<td>大冒険の会</td>
							<td>2024-06-10</td>
						</tr>
						<tr>
							<td>562</td>
							<td>2024-05-24</td>
							<td>ABC主催</td>
							<td>冒険のシナリオ</td>
							<td>はい</td>
							<td>大冒険の会</td>
							<td>2024-06-10</td>
						</tr>
						<tr>
							<td>32684</td>
							<td>2024-05-24</td>
							<td>ABC主催</td>
							<td>冒険のシナリオ</td>
							<td>はい</td>
							<td>大冒険の会</td>
							<td>2024-06-10</td>
						</tr>
						<!-- 他のレコードも同様に追加 -->
					</table>
				</div>

			</div>
		</div>
		</div>
	</main>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>