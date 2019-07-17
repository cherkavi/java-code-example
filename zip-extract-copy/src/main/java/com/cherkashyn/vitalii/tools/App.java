package com.cherkashyn.vitalii.tools;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class App {
    private final static String DELIMITER = "/";
    private final static List<String> ZIP_EXTENSION = Arrays.asList("zip", "jar", "war", "ear");

    public static void main( String[] args ) throws IOException {
        InputParameters input = InputParameters.parse(args);
        unzipValue(new ZipFile(new File(input.zip)), input.source, input.destination);
    }

    private static void unzipValue(ZipFile rootFs, String source, String destinationPath) throws IOException {
        ZipFile currentFileSystem = rootFs;
        String currentPath = null;
        ZipEntry currentFile = null;

        for(String eachPart:source.split(DELIMITER)){
            if(eachPart.equals("")){
                continue;
            }
            if(isWildcard(eachPart)){
                currentPath = (currentPath==null)?"":currentPath+DELIMITER;
                List<ZipEntry> entries = getEntriesFromCurrentFolder(currentFileSystem, currentPath);
                for(ZipEntry entry:entries){
                    String candidate = zipFileName(entry);
                    if(matchWildcardString(eachPart, candidate)){
                        currentFile = entry;
                        currentPath = StringUtils.endsWith(currentPath, DELIMITER)?currentPath:currentPath+DELIMITER;
                        break;
                    }
                }
            }else{
                currentPath = (currentPath==null)?currentPath = eachPart:currentPath + DELIMITER + eachPart;
                currentFile = currentFileSystem.getEntry(currentPath);
                if(currentFile==null){
                    throw new IllegalArgumentException(String.format("path not exists %s on step: %s",source, currentPath));
                }
            }
            if(isZipArchive(currentFile)){
                currentFileSystem = getZipFile(currentFileSystem, currentFile);
                currentPath = null;
            }
        }

        if(currentFile.isDirectory()){
            currentPath = currentPath+DELIMITER;
            File destinationFolder = new File(destinationPath);
            destinationFolder.mkdirs();
            for(ZipEntry entry: getEntries(currentFileSystem, currentPath)){
                String destinationFileName = zipFileName(entry);
                FileUtils.copyInputStreamToFile(currentFileSystem.getInputStream(entry),
                        new File(destinationPath+DELIMITER+destinationFileName));
            }
        }else{
            FileUtils.copyToFile(currentFileSystem.getInputStream(currentFile), new File(destinationPath));
        }
    }


    private static String zipFileName(ZipEntry entry) {
        String innerFilePath = entry.getName();
        if(innerFilePath.contains(DELIMITER)){
            return StringUtils.substringAfterLast(innerFilePath, DELIMITER);
        }else{
            return innerFilePath;
        }

    }

    private static List<ZipEntry> getEntriesFromCurrentFolder(ZipFile currentFileSystem, String currentPath) {
        Enumeration<? extends ZipEntry> listOfEntries = currentFileSystem.entries();
        List<ZipEntry> returnValue = filesFromZip(currentPath, listOfEntries);
        return returnValue.stream().filter(entry -> !entry.getName().substring(currentPath.length()).contains(DELIMITER)).collect(Collectors.toList());
    }

    private static List<ZipEntry> filesFromZip(String currentPath, Enumeration<? extends ZipEntry> listOfEntries) {
        List<ZipEntry> returnValue = new ArrayList<>();
        while(listOfEntries.hasMoreElements()){
            ZipEntry entry = listOfEntries.nextElement();
            if(entry.getName().equals(currentPath)){
                continue;
            }
            if(entry.getName().startsWith(currentPath)){
                returnValue.add(entry);
            }
        }
        return returnValue;
    }


    private static List<ZipEntry> getEntries(ZipFile currentFileSystem, String currentPath) {
        Enumeration<? extends ZipEntry> allFiles = currentFileSystem.entries();
        return filesFromZip(currentPath, allFiles);
    }


    private static boolean isWildcard(String eachPart) {
        return eachPart.contains("*")||eachPart.contains("?");
    }

    private static boolean isZipArchive(ZipEntry currentFile) {
        if(currentFile.isDirectory()){
            return false;
        }
        return ZIP_EXTENSION.contains(StringUtils.substringAfterLast(currentFile.getName(),".").toLowerCase());
    }

    private final static ZipFile getZipFile(ZipFile zipFile, ZipEntry entry) throws IOException {
        File tempFile = Files.createTempFile(App.class.getName(), "").toFile();
        tempFile.deleteOnExit();
        FileUtils.copyToFile(zipFile.getInputStream(entry), tempFile);
        return new ZipFile(tempFile);
    }

    private static boolean matchWildcardString(String pattern, String input) {
        if (pattern.equals(input)) {
            return true;
        }
        else if(StringUtils.trimToNull(input)==null) {
            if (pattern.startsWith("*")){
                return true;
            }else{
                return false;
            }
        } else if(pattern.length() == 0) {
            return false;
        } else if (pattern.charAt(0) == '?') {
            return matchWildcardString(pattern.charAt(1)+"", input.charAt(1)+"");
        } else if (pattern.charAt(pattern.length() - 1) == '?') {
            return matchWildcardString(
                    pattern.substring(0, pattern.length() - 1),
                    input.substring(0, input.length() - 1));
        } else if (pattern.charAt(0) == '*') {
            if (matchWildcardString(pattern.substring(1), input)) {
                return true;
            } else {
                return matchWildcardString(pattern, input.substring(1));
            }
        } else if (pattern.charAt(pattern.length() - 1) == '*') {
            if (matchWildcardString(pattern.substring(0, pattern.length() - 1), input)) {
                return true;
            } else {
                return matchWildcardString(pattern, input.substring(0, input.length() - 1));
            }
        } else if (pattern.charAt(0) == input.charAt(0)) {
            return matchWildcardString(pattern.substring(1), input.substring(1));
        }
        return false;
    }

}

class InputParameters{
    static class NotEnoughParameters extends RuntimeException{
        NotEnoughParameters(String text){
            super(text);
        }
    }
    String zip;
    String source;
    String destination;

    static InputParameters parse(String[] arguments){
        if(arguments.length<3){
            throw new NotEnoughParameters("not enough parameters: {zip} {source} {destination}");
        }
        InputParameters parameters = new InputParameters();
        parameters.zip = arguments[0];
        parameters.source = arguments[1].replaceAll("\\\\", "/");
        parameters.destination = arguments[2];
        return parameters;
    }

}
