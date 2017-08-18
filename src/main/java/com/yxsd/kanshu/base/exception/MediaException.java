package com.yxsd.kanshu.base.exception;

/**
 * @author hushengmeng
 * @date 2017/7/5.
 */
public class MediaException extends Exception {
    private static final long serialVersionUID = -4294734450421367192L;
    String errorMsg;
    String errorCode;

    public MediaException() {
    }

    public MediaException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public MediaException(String errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
