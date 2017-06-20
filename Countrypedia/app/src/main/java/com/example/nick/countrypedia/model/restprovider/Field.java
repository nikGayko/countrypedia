package com.example.nick.countrypedia.model.restprovider;

public enum Field {
    NAME {
        @Override
        String getValue() {
            return "name";
        }
    }, REGION {
        @Override
        String getValue() {
            return "region";
        }
    }, CAPITAL {
        @Override
        String getValue() {
            return "capital";
        }
    }, FLAG {
        @Override
        String getValue() {
            return "flag";
        }
    };

    abstract String getValue();
}


