package Define;

/**
 * Created by kim on 2017. 12. 8..
 */

public class DefineValue {
    public static final String TAG = "RideCrewLog";
    public static final String END_POINT = "http://ridecrew.ap-northeast-2.elasticbeanstalk.com/";
    //public static final String END_POINT = "http://42c5cf41.ngrok.io/";

    // activity return
    public static final int MY_PAGE_FRAGMENT_REQEUST_CODE = 1001;
    public static final int SCHEDULE_FRAGMENT_REQEUST_CODE = 1002;
    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 2001;
    public static final int SIGN_UP_ACTIVITY_REQEUST_CODE = 2002;


    // 자동 로그인 용
    public static final String LOGIN_ID = "LOGIN_ID";
    public static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";

    // 로그인 후 저장 값
    public static final String LOGIN_ID_PK = "LOGIN_ID_PK";
    public static final String CURRENT_LOGIN_ID = "CURRENT_LOGIN_ID";
    public static final String NICKNAME = "NICKNAME";
    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String IS_LOGIN = "IS_LOGIN";
}
