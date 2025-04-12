package com.janvi.reservation_system.middleware;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.janvi.reservation_system.constants.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class API {

    @JsonProperty("data")
    private Object data;

    @JsonProperty("message")
    private String message;

    public API(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public static ResponseEntity<API> setSuccess(Object body) {
        return new ResponseEntity<>(new API(body, HttpStatus.OK.getReasonPhrase()), HttpStatus.OK);
    }

    public static ResponseEntity<API> setInternalServerError() {
        return new ResponseEntity<>(new API(null, ErrorMessage.SOMETHING_WENT_WRONG), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<API> setResponse(Object body, HttpStatus status, String message) {
        return new ResponseEntity<>(new API(body, message), status);
    }

    public static ResponseEntity<API> setResponse(Object body, MultiValueMap<String, String> headers, HttpStatus status, String message) {
        return new ResponseEntity<>(new API(body, message), headers, status);
    }
}
