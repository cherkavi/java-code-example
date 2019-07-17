import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsShell;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.stream.Stream;

import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.FS_DEFAULT_NAME_KEY;

public class App {
    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();

        String hdfsHost = "172.17.0.2";
        // org.apache.hadoop.security.AccessControlException: SIMPLE authentication is not enabled.  Available:[TOKEN]
        int hdfsPort = 8020;

        // org.apache.hadoop.ipc.RpcException: RPC response exceeds maximum data length;
        // int hdfsPort = 8042;
        // int hdfsPort = 50075;
        // int hdfsPort = 50070;
        // int hdfsPort = 2122;

        // java.io.EOFException: End of File Exception between local host
        // int hdfsPort = 50010;

        String hdfsRootPath = "/";

        System.out.println("--begin--");

        FileSystem fs = obtainHdfs(hdfsHost, hdfsPort);
        // FileSystem fs = getFileSystem(hdfsHost, hdfsPort);
        dfsLs(hdfsRootPath, fs)
                .forEach(System.out::println);
        
        // write data into existing file, all elements of path will be created
        // FSDataOutputStream output = fs.create(new Path("/hdfs/path/new_file_name"), boolean overrideIt = true)
        // FSDataOutputStream output = fs.append(new Path("/hdfs/path/new_file_name"))
        // output.write(“test”.getBytes()); // only one Process can write into file
        // output.flush();
        // output.sync();
        // output.close();
        
        // read data from existing file
        //InputStream is=fs.open(new Path("/hdfs/path/filename"))

        System.out.println(">>>"+fs);

        fs.close();
        System.out.println("-- end --");
    }

    private static Stream<String> dfsLs(String hdfsRootPath, FileSystem fs) throws IOException {
        FileStatus[] fsStatus = fs.listStatus(new Path(hdfsRootPath));
        return Stream.of(fsStatus).map(FileStatus::getPath).map(Path::toString);
    }

    private static FileSystem obtainHdfs(final String hdfsHost, final int hdfsPort) throws IOException {
        Configuration conf = getConfiguration(hdfsHost, hdfsPort);
        return FileSystem.get(conf);
    }

    private static Configuration getConfiguration(String hdfsHost, int hdfsPort) {
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        Configuration conf = new Configuration();
        conf.set(FS_DEFAULT_NAME_KEY, "hdfs://" + hdfsHost + ":" + hdfsPort);
        conf.set("hadoop.user.name", "hdfs");
        // conf.set("hadoop.security.authentication", "kerberos");
        return conf;
    }

    private static FileSystem getFileSystem(String host, int port) throws IOException {
        return new FsShell(getConfiguration(host, port)){
            @Override
            public FileSystem getFS() throws IOException {
                return super.getFS();
            }
        }.getFS();
    }
}

/*
"19888/tcp": null,
"2122/tcp": null,
"49707/tcp": null,
"50010/tcp": null,
"50020/tcp": null,
"50070/tcp": null,
"50075/tcp": null,
"50090/tcp": null,
"8030/tcp": null,
"8031/tcp": null,
"8032/tcp": null,
"8033/tcp": null,
"8040/tcp": null,
"8042/tcp": null,
*/
