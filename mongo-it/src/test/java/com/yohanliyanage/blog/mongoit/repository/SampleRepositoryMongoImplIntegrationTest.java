package com.yohanliyanage.blog.mongoit.repository;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.yohanliyanage.blog.mongoit.model.Sample;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.extract.UserTempNaming;

/**
 * Integration Test for {@link SampleRepositoryMongoImpl}.
 * 
 * @author Yohan Liyanage
 */
public class SampleRepositoryMongoImplIntegrationTest {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "itest";
    private static final int MONGO_TEST_PORT = 27028;
    
    private SampleRepositoryMongoImpl repoImpl;

    private static MongodProcess mongoProcess;
    private static Mongo mongo;
    
    private MongoTemplate template;
    

    @BeforeClass
    public static void initializeDB() throws IOException {

        RuntimeConfig config = new RuntimeConfig();
        config.setExecutableNaming(new UserTempNaming());

        MongodStarter starter = MongodStarter.getInstance(config);

        MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
        mongoProcess = mongoExecutable.start();

        mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        mongo.close();
        mongoProcess.stop();
    }

    
    @Before
    public void setUp() throws Exception {
        repoImpl = new SampleRepositoryMongoImpl();
        template = new MongoTemplate(mongo, DB_NAME);
        repoImpl.setMongoOps(template);
    }

    @After
    public void tearDown() throws Exception {
        template.dropCollection(Sample.class);
    }
    

    @Test
    public void testSave() {
        Sample sample = new Sample("TEST", "2");
        repoImpl.save(sample);
        
        int samplesInCollection = template.findAll(Sample.class).size();
        
        assertEquals("Only 1 Sample should exist collection, but there are " 
                + samplesInCollection, 1, samplesInCollection);
    }

    @Test
    public void testFindByKey() {
        
        // Setup Test Data
        List<Sample> samples = Arrays.asList(
                new Sample("TEST", "1"), new Sample("TEST", "25"), 
                new Sample("TEST2", "66"),  new Sample("TEST2", "99"));
        
        for (Sample sample : samples) {
            template.save(sample);
        }        
        
        // Execute Test
        List<Sample> matches = repoImpl.findByKey("TEST");
        
        // Note: Since our test data (populateDummies) have only 2 
        // records with key "TEST", this should be 2
        assertEquals("Expected only two samples with key TEST, but there are " 
                + matches.size(), 2, matches.size());
    }
    
}