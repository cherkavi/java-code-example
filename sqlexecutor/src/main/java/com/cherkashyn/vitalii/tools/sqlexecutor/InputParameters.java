package com.cherkashyn.vitalii.tools.sqlexecutor;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.List;

/**
 * Created by vitalii.cherkashyn on 10/11/2017.
 */
public class InputParameters {
    @Option(name="-brandserveryaml")
    private File brandServerSettings;

    @Option(name="-sqlfile")
    private File sqlTemplate;

    @Argument
    private List<String> templateValues;

    public File getBrandServerSettings() {
        return brandServerSettings;
    }

    public File getSqlTemplate() {
        return sqlTemplate;
    }

    public List<String> getTemplateValues() {
        return templateValues;
    }
}
