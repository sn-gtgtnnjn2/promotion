package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DateValidator {
    // エラーメッセージを格納するための変数
    private List<String> errorList;
    
    // コンストラクタ
    public DateValidator() {
        this.errorList = new ArrayList<String>();
    }
    
    // エラーメッセージを取得
    public List<String> getErrorList() {
        return errorList;
    }
    
    // 必須項目の場合に行うチェック
    public boolean isNotEmpty(Date checkDate, String itemName) {
        if (Objects.isNull(checkDate)) {
        	errorList.add(itemName + ":が入力されていません。");
            return false;
        }
        return true;
    }
    
    public boolean isFutureDate(Date checkDate, String itemName) {
    	Date currentDate = new Date();
    	if (checkDate.before(currentDate)) {
    	    errorList.add(itemName + ":過去日付は設定できません");
    	    return false;
    	}
    	return true;
    }
    
    public boolean validate(Date checkDate, String itemName) {
    	return isNotEmpty(checkDate, itemName) & isFutureDate(checkDate, itemName);
    }
    
    public boolean basicValidate(Date checkDate, String itemName) {
    	return isNotEmpty(checkDate, itemName);
    }
    
    public void clearErrorMessage() {
        this.errorList = new ArrayList<String>();
    }
}
