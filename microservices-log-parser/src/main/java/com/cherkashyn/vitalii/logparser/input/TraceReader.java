package com.cherkashyn.vitalii.logparser.input;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Iterator;

public class TraceReader implements Iterator<ReaderResult>, AutoCloseable {

    private final BufferedReader source;

    public TraceReader(File file){
        try {
            this.source = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new ReadException(e);
        }
    }

    public void close(){
        IOUtils.closeQuietly(this.source);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
    }

    private String nextLine = null;

    @Override
    public boolean hasNext() {
        if(readNext())
            return true;

        this.close();
        return false;
    }

    private boolean readNext(){
        try {
            return (this.nextLine = this.source.readLine())!=null;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public ReaderResult next() {
        return ReaderResult.parse(this.nextLine);
    }

}
