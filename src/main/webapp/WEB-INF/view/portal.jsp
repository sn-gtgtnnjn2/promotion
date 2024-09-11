<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>画面名</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/unique.css">
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/calender.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/modal.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <header>
        <h1>Welcome to My Unique Website</h1>
    </header>
    <main>
        <div class="container">
            <div class="block">
                <div class="box">
                    <div class="midashi">アイコン【ラベル】</div>
                    <img src="<%= request.getContextPath() %>/icon/testicon.png" alt="てすとあいこん">
                </div>
                <div class="box">
                    <div class="midashi">プロフィール【ラベル】</div>
                    <p>AA 自己紹介メモ</p>
                </div>
            </div>
            <div class="block">
                <div class="box">
                    <div class="midashi">カレンダー【ラベル】</div>
                    <div id="calendar" class="calendar"></div>
                    <script>
                        function clicked(date) {
                            alert("日付.." + date.toString());
                        }

                        const elem = document.getElementById("calendar");
                        const calendar = new Calendar(elem, new Date());
                        calendar.setDatePickedCallback(clicked);

                        function currentDate() {
                            const date = calendar.getCurrentDate();
                            const span = document.getElementById("current-date");
                            span.innerText = "今選ばれている日付.." + date.toString();
                        }
                    </script>
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
    <footer>
        <div class="navbar">
            <img class="menu-icon" src="img/btn_logout.png" alt="ログアウト" onclick="location.href='login.html'">
            <img class="menu-icon" src="img/btn_search.png" alt="検索" onclick="location.href='search.html'">
            <img class="menu-icon" src="img/btn_histedit.png" alt="">
            <img class="menu-icon" src="img/btn_post.png" alt="">
            <img class="menu-icon" src="img/btn_home.png" alt="HOMEへ" onclick="location.href='index.html'">
        </div>
        <p>© 2024 My Unique Website</p>
    </footer>
</body>
</html>