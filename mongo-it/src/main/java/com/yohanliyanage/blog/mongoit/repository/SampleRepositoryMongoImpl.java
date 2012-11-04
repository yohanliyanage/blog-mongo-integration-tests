package com.yohanliyanage.blog.mongoit.repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.yohanliyanage.blog.mongoit.model.Sample;

/**
 * Sample Repository MongoDB Implementation.
 * 
 * @author Yohan Liyanage
 *
 */
@Repository
public class SampleRepositoryMongoImpl implements SampleRepository {

    @Autowired
    private MongoOperations mongoOps;
    
    /**
     * {@inheritDoc}
     */
    public void save(Sample sample) {
        mongoOps.save(sample);
    }

    /**
     * {@inheritDoc}
     */
    public List<Sample> findByKey(String key) {
        return mongoOps.find(query(where("key").is(key)), Sample.class);
    }

    /**
     * Sets the MongoOps implementation. 
     * 
     * @param mongoOps the mongoOps to set
     */
    public void setMongoOps(MongoOperations mongoOps) {
        this.mongoOps = mongoOps;
    }

}
