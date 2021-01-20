package com.victorthanh.utilslib.domain.model;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable
{
    private T Data;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        this.Data = data;
    }


}