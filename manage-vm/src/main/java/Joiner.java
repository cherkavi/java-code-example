import com.sun.tools.attach.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Joiner {
    /**
     * tools.jar should be added into classpath
     * @param args
     */
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        attachToProcess(String.valueOf(previousJavaApp()));
    }

    private static void attachToProcess(String processId) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        // attach to target VM
        VirtualMachine vm = VirtualMachine.attach(processId);

        // get system properties in target VM
        Properties props = vm.getSystemProperties();

        // construct path to management agent
        String home = props.getProperty("java.home");
        String agent = home + File.separator + "lib" + File.separator + "management-agent.jar";
        // load agent into target VM
        vm.loadAgent(agent, "com.sun.management.jmxremote.port=5000,com.sun.management.jmxremote.authenticate=false,com.sun.management.jmxremote.ssl=false");
        System.out.println("attached to "+processId);

        System.in.read();

        // detach
        vm.detach();
    }

    private static int previousJavaApp() {
        List<VirtualMachineDescriptor> vmList = VirtualMachine.list();
        List<Integer> ids = new ArrayList<>();
        for(VirtualMachineDescriptor vm : vmList){
            ids.add(Integer.parseInt(vm.id()));
        }
        Collections.sort(ids, (o1, o2) -> o2.intValue()-o1.intValue());
        System.out.println(" current process: "+ids.get(0)+"    target process: "+ids.get(1));
        return ids.get(1);
    }

}
