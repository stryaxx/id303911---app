package com.example.storefrontv2.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String token;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String token) {
        this.token = token;
    }

    String getToken() {
        return token;
    }
}