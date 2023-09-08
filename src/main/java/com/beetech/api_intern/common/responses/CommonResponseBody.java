package com.beetech.api_intern.common.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Common response body.
 *
 * @param <T> the type parameter
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseBody<T> {
    private Integer status;
    private String message;
    private T data;

    /**
     * Instantiates a new Common response body.
     *
     * @param data the data
     */
    public CommonResponseBody(T data) {
        setStatus(200);
        setMessage("OK");
        setData(data);
    }

    /**
     * Instantiates a new Common response body.
     *
     * @param status  the status
     * @param message the message
     * @param data    the data
     */
    public CommonResponseBody(Integer status, String message, T data) {
        setStatus(status);
        setMessage(message);
        setData(data);
    }

    /**
     * Instantiates a new Common response body.
     */
    public CommonResponseBody() {
        setStatus(200);
        setMessage("OK");
    }
}
