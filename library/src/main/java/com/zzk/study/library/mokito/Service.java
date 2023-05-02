package com.zzk.study.library.mokito;

public interface Service {
    void doAction(String request, Callback<Response> callback);
}
