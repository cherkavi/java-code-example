import java.util.Arrays;

public class ProcessImprovement {
	
    public static void main(String[] args) {
    	System.out.println(" some processes list: ");
    	ProcessHandle.allProcesses()
    	.filter(ProcessHandle::isAlive)
    	.skip(15)
    	.limit(4)
    	.forEach(ProcessImprovement::printProcessHandler);
    	
    	
    	System.out.println(" current process information: ");
    	printProcessHandler(ProcessHandle.current());
    }
    
    
    private static void printProcessHandler(ProcessHandle ph) {
		System.out.println(String.format("PID: %d   Command: %s  Arguments: %s ", 
				ph.pid(), ph.info().command().orElse(""), Arrays.toString(ph.info().arguments().orElse(new String[] {}) ) ));
    }
}

