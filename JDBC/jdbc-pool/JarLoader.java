import java.io.*;
import java.net.*;
import java.util.jar.*;
 
public class JarLoader {
 
  public static void main(String[] args) throws FileNotFoundException, IOException {
    String jarName = "c:/temp/temp.jar"; // заменить на свой
 
    URLClassLoader urlLoader = getURLClassLoader(new URL("file", null, jarName));
 
    JarInputStream jis = new JarInputStream(new FileInputStream(jarName));
    JarEntry entry = jis.getNextJarEntry();
    int loadedCount = 0, totalCount = 0;
 
    while (entry != null) {
      String name = entry.getName();
      if (name.endsWith(".class")) {
        totalCount++;
        name = name.substring(0, name.length() - 6);
        name = name.replace('/', '.');
        System.out.print("> " + name);
 
        try {
          urlLoader.loadClass(name);
          System.out.println("\t- loaded");
          loadedCount++;
        } catch (Throwable e) {
          System.out.println("\t- not loaded");
          System.out.println("\t " + e.getClass().getName() + ": " + e.getMessage());
        }
 
      }
      entry = jis.getNextJarEntry();
    }
 
    System.out.println("\n---------------------");
    System.out.println("Summary:"); 
    System.out.println("\tLoaded:\t" + loadedCount); 
    System.out.println("\tFailed:\t" + (totalCount - loadedCount)); 
    System.out.println("\tTotal:\t" + totalCount); 
  }
  
  private static URLClassLoader getURLClassLoader(URL jarURL) {
    return new URLClassLoader(new URL[]{jarURL});
  }
}