package controller;
import java.util.HashMap;
import java.util.Map;

public class NavigationManager {
    private static final Map<String, String> navigationMap = new HashMap<>();
    
    public static final String SCREEN_SEARCH = "search";
    public static final String SCREEN_EVENT_DETAIL_VIEW = "/event/eventView";
    public static final String SCREEN_PORTAL = "portal";
    public static final String SCREEN_ADD_EVENT = "addEvent";
    public static final String SCREEN_ADD_CHARACTER = "addCharacter";
    public static final String SCREEN_HISTORY = "history";
    public static final String SCREEN_MURMUR = "murmur";

    static {
        navigationMap.put(SCREEN_PORTAL, "/portal");
        navigationMap.put(SCREEN_SEARCH, "/search");
        navigationMap.put(SCREEN_EVENT_DETAIL_VIEW, "/event/eventView");
        navigationMap.put("dashboard", "/dashboardServlet");
        // 他の画面名とサーブレットのマッピングを追加
    }

    public static String getServletURL(String screenName) {
        return navigationMap.get(screenName);
    }
}