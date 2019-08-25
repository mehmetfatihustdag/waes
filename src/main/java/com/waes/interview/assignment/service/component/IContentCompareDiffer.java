package com.waes.interview.assignment.service.component;

/**
 * Generic interface that holds the method to compare data
 * @author Fatih Ustdag
 */
public interface IContentCompareDiffer<T,K> {

    /**
     *  method that calculate the differences of left and right side of data
     *
     */
    K doDiff(T left, T right);
}
