package com.sebastien.testontouch.testontouch.bean;

/**
 * Created by Sébastien on 09-07-16.
 */
public class AsyncTaskResult<T> {
    private T result;
    private Exception exception;

    public T getResult() {
        return result;
    }

    public AsyncTaskResult(T result) {
        super();
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public AsyncTaskResult(Exception exception) {
        super();
        this.exception = exception;
    }

}
