package com.bemychef.chefs.constants;

import com.bemychef.chefs.util.PropertiesUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorConstants implements Serializable {

    CONTACT_ADMIN("C50000", ""), INVALID_EMAIL("C50003", ""), INVALID_FIRST_NAME("C50001", ""),
    INVALID_LAST_NAME("C50002", ""), EITHER_MOBILE_OR_EMAIL("C50004", ""), EMAIL_ALREADY_EXISTS("C50005", ""),
    CHEF_NOT_FOUND("C50006", "");

    private final String errorCode;
    private final String errorMessage;

    ErrorConstants(final String errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return PropertiesUtil.getDataByKey(this.name());
    }

    @JsonCreator
    static ErrorConstants findValue(@JsonProperty("errorCode") String errorCode, @JsonProperty("errorMessage") String errorMessage) {

        return Arrays.stream(ErrorConstants.values()).filter(v -> v.errorCode == errorCode && v.errorMessage.equals(errorMessage)).findFirst().get();
    }

}