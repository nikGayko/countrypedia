package com.example.nick.countrypedia.model.parameter;

public enum DisplayParameter {
    REGION {
        @Override
        public int getValue() {
            return 0;
        }
    }, ALPHABET {
        @Override
        public int getValue() {
            return 1;
        }
    };

    public abstract int getValue();

    public static DisplayParameter getByValue(int value) {
        switch (value) {
            case 0:
                return REGION;
            case 1:
                return ALPHABET;
            default:
                return null;
        }
    }
}
