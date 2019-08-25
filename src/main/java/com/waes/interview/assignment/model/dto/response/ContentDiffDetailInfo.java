package com.waes.interview.assignment.model.dto.response;

import java.util.Objects;

/**
  Class represents offset and position info for difference array
  *@author Fatih Ustdag
 **/
public final class ContentDiffDetailInfo {

    private  int offset;
    private  int position;

    public ContentDiffDetailInfo(){

    }

    public ContentDiffDetailInfo(int offset, int position) {
        this.offset = offset;
        this.position = position;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentDiffDetailInfo)) return false;
        ContentDiffDetailInfo that = (ContentDiffDetailInfo) o;
        return getOffset() == that.getOffset() &&
                getPosition() == that.getPosition();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOffset(), getPosition());
    }
}
