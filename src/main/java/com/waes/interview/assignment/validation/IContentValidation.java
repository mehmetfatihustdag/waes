package com.waes.interview.assignment.validation;

/**
 *  Validation interface for  Service which do save and diff operation with content data
 * @author Fatih Ustdag
 */
public interface IContentValidation<K,L,M> {

   void validateSave(K object);
   void validateDiff(L object);
   void validateTransactionId(M object);

}

