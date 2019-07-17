package com.cherkashyn.vitalii.tools;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppTest{


    @Test
    public void readTextFile() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempFile(this.getClass().getName(), "").toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/1.txt", destinationFile.getPath()});

        // then
        Assert.assertEquals("text file 1.txt", FileUtils.readFileToString(destinationFile, "utf-8"));
    }

    @Test
    public void readTextFileFromSubArchive() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempFile(this.getClass().getName(), "").toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/3/32.zip/3/31.txt", destinationFile.getPath()});

        // then
        Assert.assertEquals("text file 31.txt", FileUtils.readFileToString(destinationFile, "utf-8"));
    }

    @Test
    public void readWildcardTextFile() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempFile(this.getClass().getName(), "").toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/4*.txt", destinationFile.getPath()});

        // then
        Assert.assertEquals("text file 45.txt", FileUtils.readFileToString(destinationFile, "utf-8"));
    }

    @Test
    public void readWildcardTextFileFromSubArchive() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempFile(this.getClass().getName(), "").toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/3/32.zip/2/4*.txt", destinationFile.getPath()});

        // then
        Assert.assertEquals("text file 45.txt", FileUtils.readFileToString(destinationFile, "utf-8"));
    }

    @Test
    public void readFileFromSubfolder() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempFile(this.getClass().getName(), "").toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/3/31.txt", destinationFile.getPath()});

        // then
        Assert.assertEquals("text file 31.txt", FileUtils.readFileToString(destinationFile, "utf-8"));
    }

    @Test
    public void readFolder() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempDirectory(this.getClass().getName()).toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/3", destinationFile.getPath()});

        //
        List<String> fileList = Stream.of(destinationFile.listFiles()).map(file-> file.getName()).collect(Collectors.toList());
        Assert.assertTrue(fileList.contains("31.txt"));
        Assert.assertTrue(fileList.contains("32.zip"));
    }

    @Test
    public void readFolderFromArchive() throws IOException {
        // given
        File sourceFile = testFile();
        File destinationFile = Files.createTempDirectory(this.getClass().getName()).toFile();
        destinationFile.deleteOnExit();

        // when
        App.main(new String[]{sourceFile.getPath(), "/3/32.zip/3", destinationFile.getPath()});

        //
        List<String> fileList = Stream.of(destinationFile.listFiles()).map(file-> file.getName()).collect(Collectors.toList());
        Assert.assertTrue(fileList.contains("31.txt"));
    }


    private File testFile() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("example.zip"); // from classpath
        return new File(resource.getPath());
    }


}
