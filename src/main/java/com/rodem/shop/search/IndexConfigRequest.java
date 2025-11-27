package com.rodem.shop.search;

public record IndexConfigRequest(
        Integer numberOfShards,
        Integer numberOfReplicas
) {
}
