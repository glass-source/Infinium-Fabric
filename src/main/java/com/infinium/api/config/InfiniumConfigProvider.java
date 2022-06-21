package com.infinium.api.config;

import com.mojang.datafixers.util.Pair;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class InfiniumConfigProvider implements SimpleConfig.DefaultConfig {

    private String configContents = "";

    public List<Pair> getConfigsList() {
        return configsList;
    }

    private final List<Pair> configsList = new ArrayList<>();

    public void addKeyValuePair(Pair<String, ?> keyValuePair) {
        configsList.add(keyValuePair);
        configContents += keyValuePair.getFirst() + "=" + keyValuePair.getSecond() + "\n";
    }

    public void removeKeyValuePair(Pair<String, ?> keyValuePair) {
        configsList.remove(keyValuePair);
    }

    @Override
    public String get(String namespace) {
        return configContents;
    }
}