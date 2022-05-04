package com.zzk.study.fastjson;

import java.util.List;

/**
 * @author zhangzhongkun
 * @since 2019-07-26 17:47
 **/
public class RootEntity {
    private String errorCode;
    private String errorMsg;
    private List<UserEntity> data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<UserEntity> getData() {
        return data;
    }

    public void setData(List<UserEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RootEntity [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", data=" + data + "]";
    }
}
