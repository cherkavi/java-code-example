package com.cherkashyn.vitalii.script;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

public class ScriptList {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> list = manager.getEngineFactories();
        int counter = 0;
        for (ScriptEngineFactory f : list) {
            System.out.println(String.format("---------  %4d  -----------",++counter));
            System.out.println(String.format("%20s : %-20s", "Engine Name", f.getEngineName()));
            System.out.println(String.format("%20s : %-20s", "Engine Version", f.getEngineVersion()));
            System.out.println(String.format("%20s : %-20s", "Language Name", f.getLanguageName()));
            System.out.println(String.format("%20s : %-20s", "Language Version", f.getLanguageVersion()));
            System.out.println(String.format("%20s : %-20s", "Engine Short Names", f.getNames()));
            System.out.println(String.format("%20s : %-20s", "Mime Types", f.getMimeTypes()));
            // System.out.println(String.format("%40s", "-".repeat(40)));
            System.out.println("--------------------------------");
        }
    }
}
