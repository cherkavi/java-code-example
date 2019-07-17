/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package launcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import launcher.settings.ISettingsService;
import launcher.settings.SettingsItem;
import launcher.settings.exceptions.ESettingsException;
import launcher.settings.properties.PropertiesLoader;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * this is project for automatization of console work ( ant compile, run ... )
 */
public class Main {
    private static final String antPath="/usr/app/ant/bin/ant";
    private static final String javaHome="/usr/app/java";

    public static void main(String[] args) throws ESettingsException {
        System.out.println("0 - path to property file");
        System.out.println("1..n - execute command (or path to build.xml )");
        System.out.println("begin");
        // args=new String[]{"/home/vcherkashin/workspaces/launcher/properties/launcher.properties","tcopy"};
        // args=new String[]{"/home/vcherkashin/workspaces/launcher/launcher.properties","/usr/app/ant/bin/ant"};
        ISettingsService service=new PropertiesLoader(args[0]);
        List<SettingsItem> list=service.getAllValues();
        for(int counter=1;counter<args.length;counter++){
            try{
                System.out.println("Execute:"+args[counter]);
                SettingsItem item=SettingsItem.getSettingsItemByName(list, args[counter]);
                if(item!=null){
                    int returnValue=runCommand(detectAntScript(item.getValue()));
                    if(returnValue>0){
                        System.out.println("Command execute with Error: "+item);
                        break;
                    }
                }else{
                    System.out.println("command does not found in property file: "+args[counter]);
                    runCommand(detectAntScript(args[counter]));
                }
            }catch(ESettingsException ex){
                System.out.println(ex.getMessage());
                break;
            }
        }
        System.out.println("-end-");
    }

    /** return string command  */
    private static String[] detectAntScript(String command) throws ESettingsException{
        if(command!=null){
            if(command.endsWith("build.xml")){
                try{
                    runAntScript(command);return null;
                    // return new String[]{antPath,"-buildfile",command};
                }catch(Exception ex){
                    // System.out.println("Run Exception:"+ex.getMessage());
                    throw new ESettingsException("Run Exception:"+ex.getMessage());
                }
            }else{
                return detectMultiCommand(command);
            }
        }else{
            return new String[]{};
        }
    }




    private static void runAntScript(String fileBuild) throws Exception{
        File buildFile = new File(fileBuild);
        System.out.println("   >>>> "+buildFile.getAbsolutePath().trim());
        Project p = new Project();
        p.setUserProperty("ant.file", buildFile.getAbsolutePath());
        p.setUserProperty("build.compiler", "extJavac");
        p.setProperty("java.home", javaHome);
        p.setUserProperty("java.home", javaHome);
        p.init();
        ProjectHelper helper = ProjectHelper.getProjectHelper();
        p.addReference("ant.projectHelper", helper);
        helper.parse(p, buildFile);
        p.executeTarget(p.getDefaultTarget());
    }

    /** run command */
    private static int runCommand(String[] command){
        if(command==null)return 0;
        try {
            printArray("   >>>> ", command);
          Process p = Runtime.getRuntime().exec(command);
          p.waitFor();
          if(p.exitValue()>0){
            printInputStream(p.getErrorStream());
          }else{
            printInputStream(p.getInputStream());
          }
          return p.exitValue();
        }
        catch (Exception err) {
          printArray(null, command);
          err.printStackTrace();
          return 2;
        }
    }

    private static void printInputStream(InputStream is) throws IOException{
        System.out.print("   ... ");
        while(is.available()>0){
            System.out.print( (char)is.read());
        }
        System.out.println();
    }

    private static void printArray(String preambula, String[] values){
        StringBuffer buffer=new StringBuffer();
        if(preambula!=null)buffer.append(preambula);
        for(int counter=0;counter<values.length;counter++){
            buffer.append(values[counter]);
            buffer.append("  ");
        }
        System.out.println(buffer.toString());
    }

    private static String[] detectMultiCommand(String command) {
        return command.trim().split(" ");
    }
}
