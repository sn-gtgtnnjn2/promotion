<%@page pageEncoding="UTF-8"%>
    <footer>
        <div class="navbar">
            <img class="menu-icon" src="<%= request.getContextPath() %>/img/btn_logout.png" alt="ログアウト" onclick="location.href='<%= request.getContextPath() %>/logout'">
            <img class="menu-icon" src="<%= request.getContextPath() %>/img/btn_search.png" alt="検索" onclick="location.href='<%= request.getContextPath() %>/search'">
            <img class="menu-icon" src="<%= request.getContextPath() %>/img/btn_addEvent.png" alt="イベント作成" onclick="location.href='<%= request.getContextPath() %>/event/addEvent'">
            <img class="menu-icon" src="<%= request.getContextPath() %>/img/btn_histedit.png" alt="履歴" onclick="location.href='<%= request.getContextPath() %>/history'">
            <img class="menu-icon" src="<%= request.getContextPath() %>/img/btn_post.png" alt="つぶやく" onclick="location.href='<%= request.getContextPath() %>/murmur'">
            <img class="menu-icon" src="<%= request.getContextPath() %>/img/btn_home.png" alt="HOMEへ" onclick="location.href='<%= request.getContextPath() %>/portal'">
        </div>
        <p>© 2024 TRPG Resume Site</p>
    </footer>