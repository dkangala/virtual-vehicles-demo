package com.example.valet;

import java.util.Properties;

import org.restexpress.RestExpress;
import com.example.valet.objectid.TransactionController;
import com.example.valet.objectid.TransactionRepository;
import com.example.valet.objectid.TransactionService;
import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration
        extends Environment {

    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT_PROPERTY = "port";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private TransactionController transactionController;

    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        TransactionRepository valetsOidRepository = new TransactionRepository(mongo.getClient(), mongo.getDbName());
        TransactionService valetOidService = new TransactionService(valetsOidRepository);
        transactionController = new TransactionController(valetOidService);
    }

    public int getPort() {
        return port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    public MetricsConfig getMetricsConfig() {
        return metricsSettings;
    }

    public TransactionController getTransactionController() {
        return transactionController;
    }
}