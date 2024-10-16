/**
 * 
 */

     function openSearchWindow() {
        document.getElementById('overlay').style.display = 'block';
        window.open('CharaSearchServlet', 'SearchWindow', 'width=600,height=400');
    }

    document.getElementById('overlay').addEventListener('click', function() {
        document.getElementById('overlay').style.display = 'none';
        window.close();
    });