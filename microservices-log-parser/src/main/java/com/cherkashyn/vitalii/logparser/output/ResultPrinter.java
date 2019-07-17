package com.cherkashyn.vitalii.logparser.output;

import com.cherkashyn.vitalii.logparser.input.ReadException;
import com.cherkashyn.vitalii.logparser.process.Bucket;
import com.cherkashyn.vitalii.logparser.process.TraceChainBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.TimeZone;

public class ResultPrinter implements Closeable {
    private BufferedWriter outputFile;
    private BufferedWriter errorFile;

    public ResultPrinter(File outputFile, File errorFile) throws IOException {
        if(outputFile != null)
            this.outputFile = new BufferedWriter(new FileWriter(outputFile));
        if(errorFile != null)
            this.errorFile = new BufferedWriter(new FileWriter(errorFile));
    }


    public void printReadError(ReadException traceException) {
        printError(traceException.getMessage());
    }

    public void printTrace(String json) {
        printOut(json);
    }

    public void printError(TraceChain root, JsonProcessingException e) {
        printError(root.toString()+">>> "+e.getMessage());
    }

    public void printError(TraceChainBuilder.BuildException e, Bucket bucket) {
        printError(e.getClass().getSimpleName()+" >>> "+bucket.toString());
    }


    private void printError(String message)  {
        if(Objects.nonNull(this.errorFile)){
            try {
                this.errorFile.write(message);
                this.errorFile.newLine();
            } catch (IOException e) {
                System.err.println("can't write error message to file: "+e.getMessage());
            }
        }else{
            System.err.println(message);
        }
    }

    private void printOut(String message)  {
        if(Objects.nonNull(this.outputFile)){
            try {
                this.outputFile.write(message);
                this.outputFile.newLine();
            } catch (IOException e) {
                System.err.println("can't write output to file: "+e.getMessage());
            }
        }else{
            System.out.println(message);
        }
    }

    @Override
    public void close(){
        try{
            if(this.errorFile!=null)
                this.errorFile.flush();
        }catch(IOException ex){ }

        try{
            if(this.outputFile!=null)
                this.outputFile.flush();
        }catch(IOException ex){}

        IOUtils.closeQuietly(this.errorFile);
        IOUtils.closeQuietly(this.outputFile);

    }

    public static ObjectMapper getObjectMapper(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new ObjectMapper()
                // .enable(SerializationFeature.INDENT_OUTPUT)
                .setDateFormat(dateFormat);
    }

}
