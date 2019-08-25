package com.waes.interview.assignment.model.dto.validation;


public class ContentDiffDataValidationDTO {
    private  String leftData;
    private  String rightData;

    public ContentDiffDataValidationDTO(String leftData, String rightData) {
        this.leftData = leftData;
        this.rightData = rightData;
    }

    public String getLeftData() {
        return leftData;
    }

    public String getRightData() {
        return rightData;
    }

}
