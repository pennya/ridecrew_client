package Entity;

import util.LogUtil;

public class ApiError {
	private ApiErrorType type;
    private ApiErrorCode code;
    private String message;
    private String errorStack;

    public ApiError() {
        type = ApiErrorType.UNKNOWN;
    }

    public ApiError(ApiErrorType type, ApiErrorCode code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public ApiError(ApiErrorType type, ApiErrorCode code, String message, Throwable e) {
        this.type = type;
        this.code = code;
        this.message = message;
        this.errorStack = LogUtil.getStackTrace(e);
    }

    public ApiError(Throwable e) {
        this.type = ApiErrorType.UNKNOWN;
        this.code = ApiErrorCode.UNKNOWN_SERVER_ERROR;
        this.message = e.toString();
        this.errorStack = LogUtil.getStackTrace(e);
    }

    public ApiError(Throwable e, String message, ApiErrorCode code) {
        this.type = ApiErrorType.UNKNOWN;
        this.message = message;
        this.code = code;
        this.message = e.toString();
        this.errorStack = LogUtil.getStackTrace(e);
    }

	public ApiErrorType getType() {
		return type;
	}

	public void setType(ApiErrorType type) {
		this.type = type;
	}

	public ApiErrorCode getCode() {
		return code;
	}

	public void setCode(ApiErrorCode code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorStack() {
		return errorStack;
	}

	public void setErrorStack(String errorStack) {
		this.errorStack = errorStack;
	}
    
    
}
