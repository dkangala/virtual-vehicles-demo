package com.example.valet;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/virtual/valet/transaction/{oid}.{format}", config.getTransactionController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_TRANSACTION);

        server.uri("/virtual/valet/transactions.{format}", config.getTransactionController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.TRANSACTION_COLLECTION);
    }
}