package com.cherkashyn.vitalii.logparser;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AppTest {


    @Test
    public void classicExecutionExample() throws IOException {
        // given
        String inputFile = Thread.currentThread().getClass().getResource("/small-log.txt").getFile();
        String[] parameters = new String[]{"--input-file", inputFile};

        // when
        App.main(parameters);

        // then
    }

    @Test
    public void outputResultsToFile() throws IOException {
        // given
        String inputFile = Thread.currentThread().getClass().getResource("/small-log.txt").getFile();
        File outputFile = File.createTempFile(AppTest.class.getName(), "tmp");
        outputFile.deleteOnExit();
        String[] parameters = new String[]{"--input-file", inputFile, "--output-file", outputFile.getAbsolutePath()};

        // when
        App.main(parameters);

        // then
        assertEquals(71, amountOfLines(outputFile));

        outputFile.delete();
    }

    @Test
    public void outputResultsToMediumFile() throws IOException {
        // given
        String inputFile = Thread.currentThread().getClass().getResource("/medium-log.txt").getFile();
        File outputFile = File.createTempFile(AppTest.class.getName(), "tmp");
        outputFile.deleteOnExit();
        File errorFile = File.createTempFile(AppTest.class.getName(), "tmp");
        errorFile.deleteOnExit();
        String[] parameters = new String[]{"--input-file", inputFile, "--output-file", outputFile.getAbsolutePath(), "--error-file", errorFile.getAbsolutePath()};

        // when
        App.main(parameters);

        // then
        assertEquals(39372, amountOfLines(outputFile));

        outputFile.delete();
        errorFile.delete();
    }



    @Ignore
    @Test
    public void checkOutputResult() throws IOException, JSONException {
        // given
        String inputFile = Thread.currentThread().getClass().getResource("/small-log.txt").getFile();
        File fileForComparing = new File(Thread.currentThread().getClass().getResource("/small-traces.txt").getFile());
        File outputFile = File.createTempFile(AppTest.class.getName(), "tmp");
        outputFile.deleteOnExit();
        String[] parameters = new String[]{"--input-file", inputFile, "--output-file", outputFile.getAbsolutePath()};

        // when
        App.main(parameters);

        // then
        assertEquals(71, amountOfLines(outputFile));

        List<JSONObject> objectsFromEtalon = readJsonObjects(fileForComparing);
        List<JSONObject> objectsFromOutput = readJsonObjects(outputFile);

        assertEquals(objectsFromEtalon.size(), objectsFromOutput.size());

        outputLoop: for(JSONObject outputObject : objectsFromOutput){
            for(JSONObject etalon : objectsFromEtalon){
                JSONCompareResult result = JSONCompare.compareJSON(etalon, outputObject, JSONCompareMode.STRICT);
                if(result.passed())
                    continue outputLoop;
            }
            fail("can't find the same object for: "+outputObject);
        }

        outputFile.delete();

    }

    private List<JSONObject> readJsonObjects(File fileForComparing) throws IOException {
        List<String> lines = FileUtils.readLines(fileForComparing, Charset.defaultCharset());
        List<JSONObject> returnValue = new ArrayList<>(lines.size());
        for(String line: lines)
            try {
                returnValue.add(new JSONObject(line));
            } catch (JSONException e) {
                System.err.println("can't understand the line: "+line);
            }
        return returnValue;
    }


    private int amountOfLines(File outputFile) {
        int counter=0;
        try(BufferedReader reader = new BufferedReader(new FileReader(outputFile))){
            while(StringUtils.trimToNull(reader.readLine())!=null){
                counter++;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return counter;
    }

}
