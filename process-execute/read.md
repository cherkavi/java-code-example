exeucte command line application and get the result ( with pipes )
```sh
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("/bin/sh", "-c", String.format("cat %s | grep %s | head -n 1", csvPath, occurence));
	// !!! do not use without "/bin/sh" !!!!
        Process proc = pb.start();
        try {
            proc.waitFor(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }

        String responseLine = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            responseLine = reader.readLine();
        }
	// no result has found
        if(responseLine == null){            
            return StringUtils.EMPTY;
        }

```

[example](https://github.com/zeroturnaround/zt-exec)
```java
        result = new ProcessExecutor().commandSplit(commandLine).start().getFuture().get();
        if(result.getExitValue()!=0){
            throw new IllegalStateException("result of command line execution is not 0: "+result.getExitValue());
        }

        List<String> output = new ProcessExecutor().commandSplit(commandLine).execute().getOutput().getLines();
	
```

http://commons.apache.org/proper/commons-exec/tutorial.html
