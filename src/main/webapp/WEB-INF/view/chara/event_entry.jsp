<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="">
<script src="<%= request.getContextPath() %>/js/event_detail_child.js"></script>
</head>
<body>
<div id="overlay" style="display:none;"></div>
<button onclick="openSearchWindow()">検索する</button>

<c:out value="${test }"/>

<style>
#overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    z-index: 1000;

</style>

</body>
</html>