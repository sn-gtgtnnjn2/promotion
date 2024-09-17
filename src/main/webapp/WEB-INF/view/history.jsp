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
					<div class="midashi">活動履歴</div>
					<table class="design10">
						<thead>
							<tr>
								<th>日付</th>
								<th>イベント名</th>
								<th>ゲームシステム</th>
								<th>シナリオ</th>
								<th>ステータス</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><time datetime="2024-07-19">2024/7/19</time></td>
								<td>サマーゲームフェスティバル</td>
								<td>ロールプレイングゲーム</td>
								<td>冒険の島</td>
								<td>募集中</td>
							</tr>
							<tr>
								<td><time datetime="2024-07-19">2024/7/19</time></td>
								<td>サマーゲームフェスティバル</td>
								<td>ロールプレイングゲーム</td>
								<td>冒険の島</td>
								<td>募集中</td>
							</tr>
							<tr>
								<td><time datetime="2024-07-19">2024/7/19</time></td>
								<td>サマーゲームフェスティバル</td>
								<td>ロールプレイングゲーム</td>
								<td>冒険の島</td>
								<td>募集中</td>
							</tr>
							<tr>
								<td><time datetime="2024-07-19">2024/7/19</time></td>
								<td>サマーゲームフェスティバル</td>
								<td>ロールプレイングゲーム</td>
								<td>冒険の島</td>
								<td>募集中</td>
							</tr>
							<tr>
								<td><time datetime="2024-07-19">2024/7/19</time></td>
								<td>サマーゲームフェスティバル</td>
								<td>ロールプレイングゲーム</td>
								<td>冒険の島</td>
								<td>募集中</td>
							</tr>
							<!-- 他の行も同様に追加 -->
						</tbody>
					</table>
				</div>
				<div class="box-search">
					<div class="midashi">主催イベント</div>
				</div>
				<div class="box-search">
					<div class="midashi">参加イベント</div>
				</div>
				<div class="box-search">
					<div class="midashi">キャラクター
					<button>追加</button>
				</div>


			</div>
		</div>
		</div>
	</main>
	<jsp:include page="footer.jsp" flush="true" />
</body>
</html>