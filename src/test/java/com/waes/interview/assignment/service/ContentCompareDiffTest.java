package com.waes.interview.assignment.service;

import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.dto.response.ContentDiffResultType;
import com.waes.interview.assignment.service.component.ByteArrayContentDiffService;
import com.waes.interview.assignment.service.component.IContentCompareDiffer;
import org.junit.Before;
import org.junit.Test;

public class ContentCompareDiffTest {

    private IContentCompareDiffer <byte[],ContentDiffResponse> compareService;
    byte[] byte1 = new byte[] {0,1,2,3,4,5};
    byte[] byte2 = new byte[] {0,1,2,3,4,5,6};
    byte[] byte3 = new byte[] {0,1,8,3,4,7,6};

    @Before
    public void setup() {
        this.compareService = new ByteArrayContentDiffService();
    }

    @Test
    public void testShouldReturnEqual() {
        ContentDiffResponse result = compareService.doDiff(byte1,byte1);

        assert (result.getMessage() == ContentDiffResultType.EQUAL.toString());
    }

    @Test
    public void testShouldReturnSizeMismatch() {
        ContentDiffResponse result  = compareService.doDiff(byte1, byte2);

        assert (result.getMessage() == ContentDiffResultType.DIFFERENT_LENGTH.toString());
    }

    @Test
    public void testShouldReturnSizeMatchIfLeftSizeNullArray() {
        ContentDiffResponse result  = compareService.doDiff(null, byte2);

        assert (result.getMessage() == ContentDiffResultType.DIFFERENT_LENGTH.toString());
    }

    @Test
    public void testShouldReturnSizeMatchIfRightSizeNullArray() {
        ContentDiffResponse result  = compareService.doDiff(byte2, null);

        assert (result.getMessage() == ContentDiffResultType.DIFFERENT_LENGTH.toString());
    }

    @Test
    public void testShouldReturnSizeMatchWithLeftSizeNullArray() {
        ContentDiffResponse result  = compareService.doDiff(null, byte2);

        assert (result.getMessage() == ContentDiffResultType.DIFFERENT_LENGTH.toString());
    }

    @Test
    public void testShouldReturnEqualWithBothSizeNullArray() {
        ContentDiffResponse result  = compareService.doDiff(null, null);

        assert (result.getMessage() == ContentDiffResultType.EQUAL.toString());
    }

    @Test
    public void testShouldReturnDifferences() {
        ContentDiffResponse result = compareService.doDiff(byte2, byte3);
        assert (result.getMessage() == ContentDiffResultType.NOT_EQUAL.toString());
        assert (result.getDifferences().size() == 2);
        assert (result.getDifferences().get(0).getOffset() ==2);
        assert (result.getDifferences().get(0).getPosition() ==1);
        assert (result.getDifferences().get(1).getOffset() ==5);
        assert (result.getDifferences().get(1).getPosition() ==1);
    }

}
