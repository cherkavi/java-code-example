package com.cherkashyn.vitalii.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RunScript {
    public static void main(String ... args){
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("javascript");
        try {
            // `cp script-on-disk.js /tmp/`
            scriptEngine.eval("load('/tmp/script-on-disk.js')");
            scriptEngine.eval("print('hello from script')");
        } catch (ScriptException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
