package org.dev.iuh.common.dto;

import java.io.Serializable;

public class GeneralMessage<T> implements Serializable {
	private static final long serialVersionUID = 8708408441420625246L;
    private String statusCode;  // thành công hay ko
    private String requestCode; // "EDIT_STUDENT"
    private T result; // dữ liệu trả về từ server
    
    public GeneralMessage() {}

    public GeneralMessage(T result) {
        this.result = result;
        this.statusCode = "OK";
        this.requestCode = "ALL";
    }

    public GeneralMessage(String requestCode, T result) {
        this.result = result;
        this.statusCode = "OK";
        this.requestCode = requestCode;
    }

    public GeneralMessage(String statusCode, String requestCode, T result) {
        this.result = result;
        this.statusCode = statusCode;
        this.requestCode = requestCode;
    }

    public static<T> GeneralMessage<T> createSuccessMessage(T result, String requestCode) {
        return new GeneralMessage<>(requestCode, result);
    }

    public static GeneralMessage<String> createErrorMessage(String requestCode, String errorCode, String errorMessage) {
        return new GeneralMessage<String>(errorCode, requestCode, errorMessage);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

