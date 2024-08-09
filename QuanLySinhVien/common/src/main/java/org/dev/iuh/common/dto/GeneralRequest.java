package org.dev.iuh.common.dto;

import java.io.Serializable;

public class GeneralRequest<T> implements Serializable {
	private static final long serialVersionUID = 8381209520562250113L;
    private String requestCode; // "EDIT_STUDENT"
    private T payload; // dữ liệu gửi lên server

    public GeneralRequest() {}

    public GeneralRequest(String requestCode, T payload) {
        this.requestCode = requestCode;
        this.payload = payload;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
