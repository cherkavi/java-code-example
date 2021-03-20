class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent attached at startup");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent attached to running VM");
    }
}
