package com.grndctl.model.taf;

/**
 * <p>
 * Possible time type parameters for retrieving a {@link TAF}.
 * </p>
 *
 * @author Michael Di Salvo
 */
public enum TimeType {

    ISSUE("issue"), VALID("valid");

    private String value;

    TimeType(String value) {
        this.value = value;
    }

    public String valueOf() {
        return value;
    }

}
