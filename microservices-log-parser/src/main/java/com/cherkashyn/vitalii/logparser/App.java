package com.cherkashyn.vitalii.logparser;


import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.cherkashyn.vitalii.logparser.input.ReaderResult;
import com.cherkashyn.vitalii.logparser.input.TraceReader;
import com.cherkashyn.vitalii.logparser.output.ResultPrinter;
import com.cherkashyn.vitalii.logparser.output.TraceChain;
import com.cherkashyn.vitalii.logparser.output.TraceChainWrapper;
import com.cherkashyn.vitalii.logparser.process.Bucket;
import com.cherkashyn.vitalii.logparser.process.BucketHolder;
import com.cherkashyn.vitalii.logparser.process.FullBucketListener;
import com.cherkashyn.vitalii.logparser.process.TraceChainBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class App implements FullBucketListener{
    @Parameter(names = {"-i", "--input-file"}, description = "input file for parsing", required =  true, converter =  ParameterFileConverter.class)
    File inputFile;
    @Parameter(names = {"-o", "--output-file"}, description = "output file with results", required =  false, converter =  ParameterFileConverter.class)
    File outputFile;
    @Parameter(names = {"-d", "--distance"}, description = "amount of different records before obsolete", required = false)
    int distance = 20;
    @Parameter(names = {"-e", "--error-file"}, description = "file for output errors", required =  false, converter =  ParameterFileConverter.class)
    File errorFile;
    @Parameter(names = {"-h", "--help"}, description = "file for output errors", help = true)
    boolean isHelp;

    private ResultPrinter printer;
    private ObjectMapper objectMapper;

    public static void main(String ... args) throws IOException {
        App app = new App();
        JCommander.newBuilder()
                .addObject(app)
                .build()
                .parse(args);

        if(app.isHelp){
            printHelp();// new JCommander(app, args).usage();
            System.exit(0);
        }

        app.checkAndPrepareParameters();
        app.execute();
    }

    private static void printHelp() {
        System.out.println("--------PARAMETERS--------");
        System.out.println(" [-i] path to input file ");
        System.out.println(" [-o] path to output file ");
        System.out.println(" [-d] distance ");
        System.out.println(" [-e] output error to file");
        System.out.println("--------------------------");
    }

    private void checkAndPrepareParameters() throws IOException {
        exitIfNotExists(this.inputFile);

        if(this.distance<=0)
            this.distance = 20;

        printer = new ResultPrinter(this.outputFile, this.errorFile);

        this.objectMapper = ResultPrinter.getObjectMapper();
    }

    private static void exitIfNotExists(File inputFile) {
        if(inputFile.exists())
            return;
        System.err.println("File not exists: "+inputFile);
        System.exit(1);
    }

    private void execute() {
        try(BucketHolder bucketHolder = new BucketHolder(distance, this);
            TraceReader traceIterator = new TraceReader(this.inputFile)){
            while(traceIterator.hasNext()){
                ReaderResult nextResult = traceIterator.next();
                if(nextResult.isTrace())
                    bucketHolder.add(nextResult.getTrace());
                else
                    printer.printReadError(nextResult.getTraceException());
            }
        }
        printer.close();
    }


    @Override
    public void processBucket(Bucket bucket) {
        TraceChain root = null;
        try{
            root = new TraceChainBuilder().buildRoute(bucket);
        }catch(TraceChainBuilder.BuildException ex){
            this.printer.printError(ex, bucket);
            return;
        }
        try {
            String json = objectMapper.writeValueAsString(new TraceChainWrapper(root));
            this.printer.printTrace(json);
        } catch (JsonProcessingException e) {
            this.printer.printError(root, e);
        }
    }

}




class ParameterFileConverter implements IStringConverter<File> {
    @Override
    public File convert(String value) {
        if(StringUtils.trimToNull(value)==null)
            return null;
        return new File(value);
    }
}

