package edu.carlosliam.hotelmanagementfx.model.response;

public class Response<T> {
    private boolean error;
    private String errorMessage;
    private T result;

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getResult() {
        return result;
    }
}
