package com.waes.interview.assignment.model.dto.response;

/**
  Enum class represents result type for compared data.
 **/
public enum ContentDiffResultType {

    EQUAL("Data are equal, there is not difference"),

    DIFFERENT_LENGTH ("Data have both different length"),

    NOT_EQUAL ("Data are not equal");

    private String message;

    ContentDiffResultType(String message){
       this.message = message;

    }

    public String toString() {
        return this.message;
    }

}
