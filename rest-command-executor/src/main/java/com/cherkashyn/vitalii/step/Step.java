package com.cherkashyn.vitalii.step;

import java.util.Map;

@FunctionalInterface
public interface Step {
    void execute(Map<String, Object> parameters);
}
