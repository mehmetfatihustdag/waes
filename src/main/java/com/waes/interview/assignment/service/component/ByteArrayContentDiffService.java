package com.waes.interview.assignment.service.component;

import com.waes.interview.assignment.model.dto.response.ContentDiffDetailInfo;
import com.waes.interview.assignment.model.dto.response.ContentDiffResponse;
import com.waes.interview.assignment.model.dto.response.ContentDiffResultType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service which represent Implementation of {@link IContentCompareDiffer}
 * in order to compare differences of byte arrays
 * @author Fatih Ustdag
 */
@Component
public class ByteArrayContentDiffService implements IContentCompareDiffer<byte[], ContentDiffResponse> {

    @Override
    public ContentDiffResponse doDiff(byte[] leftData, byte[] rightData) {
       //Null Checks
        if(leftData ==null){
            leftData = new byte[]{};
        }
        if(rightData ==null){
            rightData = new byte[]{};
        }

        final ContentDiffResponse contentGetDifferenceResponse = new ContentDiffResponse();
        //Check if arrays are equals if so just return
        if (Arrays.equals(leftData, rightData)) {
            contentGetDifferenceResponse.setMessage(ContentDiffResultType.EQUAL.toString());
            return contentGetDifferenceResponse;
        }

        //Check if arrays are the same length,if not just return
        else if (leftData.length != rightData.length) {
            contentGetDifferenceResponse.setMessage(ContentDiffResultType.DIFFERENT_LENGTH.toString());
            return contentGetDifferenceResponse;
        }
        contentGetDifferenceResponse.setMessage(ContentDiffResultType.NOT_EQUAL.toString());
        contentGetDifferenceResponse.setDifferences(calculateDiffsOnEqualSizeData(leftData,rightData));
        return contentGetDifferenceResponse;
    }


    /**
     *  Method to compare left and right side of array
     */
    private List<ContentDiffDetailInfo> calculateDiffsOnEqualSizeData(byte[] leftData, byte[] rightData) {
        //If same length and different, calculate diff
        final List<ContentDiffDetailInfo> differences = new ArrayList<>();
        boolean preChecked = false;
        ContentDiffDetailInfo contentDifferenceResult =null ;
        //Length left and right same //Just take one of them
        for (int i = 0; i < leftData.length; i++) {
            if(!Optional.ofNullable(contentDifferenceResult).isPresent()) {
                contentDifferenceResult = new ContentDiffDetailInfo();
            }
            if (!preChecked && leftData[i] != rightData[i]) {
                contentDifferenceResult.setOffset(i);
                preChecked = true;
            } else if (preChecked && leftData[i] == rightData[i]) {
                contentDifferenceResult.setPosition(i-contentDifferenceResult.getOffset());
                differences.add(contentDifferenceResult);
                preChecked = false;
                contentDifferenceResult = null;
            }
        }
        return differences;
    }

}




























