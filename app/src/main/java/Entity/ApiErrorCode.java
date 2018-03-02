package Entity;

public enum ApiErrorCode {
	UNKNOWN_SERVER_ERROR,
    DUPLICATE_LOGIN_ID,
    INCORRECT_LOGIN_ID_AND_PASSWORD,
    INVALIDATE_ACCESS_TOKEN,
    EXPIRED_ACCESS_TOKEN,
    DUPLICATE_CODE,
    NO_PERMISSION,
    NOT_FOUND,
    DUPLICATE_DEVICE_ID,
    INCORRECT_LOGIN_ID
}
