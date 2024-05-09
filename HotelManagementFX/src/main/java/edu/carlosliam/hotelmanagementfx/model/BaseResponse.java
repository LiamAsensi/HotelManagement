package edu.carlosliam.hotelmanagementfx.model;

public class BaseResponse {
    public boolean error;
    public String errorMessage;

    public boolean isError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
