package com.yohanliyanage.blog.mongoit.repository;

import java.util.List;

import com.yohanliyanage.blog.mongoit.model.Sample;

/**
 * Sample Repository API.
 * 
 * @author Yohan Liyanage
 *
 */
public interface SampleRepository {

    /**
     * Persists the given Sample.
     * @param sample
     */
    void save(Sample sample);
    
    /**
     * Returns the list of samples with given key.
     * @param sample
     * @return
     */
    List<Sample> findByKey(String key);
}
