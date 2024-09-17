package util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringValidator {
    // エラーメッセージを格納するための変数
    private List<String> errorList;
    
    // 文字種チェック用の条件コード
    public static final int NO_CHECK = 0;
    public static final int FULL_WIDTH_ONLY = 1;
    public static final int FULL_KATAKANA_ONLY = 2;
    public static final int HALF_ALF_NUM_SBL = 3;
    public static final int DIGITS_ONLY = 4;
    
    // 文字種判定用正規表現
    private String FULL_WIDTH_ONLY_RGX = "/^[^\\x01-\\x7E\\xFF61-\\xFF9F]+$/";
    private String FULL_KATAKANA_ONLY_RGX = "/^[ァ-ンヴー]+$/";
    private String HALF_ALF_NUM_SBL_RGX = "/^[\\x20-\\x7E]+$/";
    private String DIGITS_ONLY_RGX = "/^\\d+$/";
    
    // コンストラクタ
    public StringValidator() {
        this.errorList = new ArrayList<String>();
    }

    // 文字列が入力されているかチェック
    public boolean isNotEmpty(String input, String itemName) {
        if (input == null || input.isEmpty()) {
        	errorList.add(itemName + ":文字列が入力されていません。");
            return false;
        }
        return true;
    }

    // 文字の種類が適切かチェック（例: 英数字のみ）
    public boolean isValidType(String input, Pattern pattern, String chartype, String itemName) {
        if (pattern.matcher(input).matches()) {
        	errorList.add(itemName + ":"+String.format("文字の種類が不適切です。%sのみ使用できます。", chartype));
            return false;
        }
        return true;
    }

    // 文字の長さが適切かチェック（例: 5〜10文字）
    public boolean isValidLength(String input, int minLength, int maxLength, String itemName) {
        if (input.length() < minLength || input.length() > maxLength) {
        	errorList.add(itemName + ":文字の長さが不適切です。" + minLength + "〜" + maxLength + "文字で入力してください。");
            return false;
        }
        return true;
    }

    // エラーメッセージを取得
    public List<String> getErrorList() {
        return errorList;
    }

    // すべてのチェックを実行
    public boolean validate(String input, int minLength, int maxLength, int checkType, String itemName) {
	
    	String checkRegex;
    	String charType;
    	
    	if(checkType == NO_CHECK) {
    		return isNotEmpty(input, itemName) && isValidLength(input, minLength, maxLength, itemName);
    	}
    	
    	
    	switch(checkType) {
    	case 1:
    		checkRegex = this.FULL_WIDTH_ONLY_RGX;
    		charType = "全角文字";
    		break;
    	case 2:
    		checkRegex = this.FULL_KATAKANA_ONLY_RGX;
    		charType = "カタカナ";
    		break;
    	case 3:
    		checkRegex = this.HALF_ALF_NUM_SBL_RGX;
    		charType = "半角英数字と_#!$";
    		break;
    	default:
    		checkRegex = this.DIGITS_ONLY_RGX;
    		charType = "文字型";
    		break;
    	}
    	
    	Pattern pattern = Pattern.compile(checkRegex);
        return isNotEmpty(input, itemName) && isValidType(input, pattern, charType, itemName) && isValidLength(input, minLength, maxLength, itemName);
    }

    public void clearErrorMessage() {
        this.errorList = new ArrayList<String>();
    }
    
}