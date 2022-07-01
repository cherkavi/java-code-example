package com.cherkashyn.investigation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        readJsonFile("data.json");
        readJsonFile("data-nested.json");
    }

    private static void readJsonFile(String filePath) {
        System.out.println("parse file: "+filePath);
        try(Reader reader = new InputStreamReader(new FileInputStream(Main.class.getResource(filePath).getFile()))){
            JsonMapping metadata = new ObjectMapper().readValue(reader, JsonMapping.class);
            System.out.println(metadata);
        } catch (IOException e) {
            System.err.println("can't read file: "+e.getMessage());
        }
    }
}