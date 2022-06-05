package inst.engine.security.agent;

import java.lang.instrument.Instrumentation;

public class AgentMain {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        // 获取加密算法、密钥信息
        System.out.println("agent 执行中，参数：" + agentArgs);
        String[] args = agentArgs.split(",");
        if (!"ECB".equals(args[0])) {
            System.out.println("agent 只支持算法ECB");
            return;
        }

        if ("ECB".equals(args[0]) && args[1].length() != 16) {
            System.out.println("ECB算法key必须为16位");
        }

        GlobalData.INSTANCE.setAlgorithm(args[0]);
        GlobalData.INSTANCE.setKey(args[1]);

        instrumentation.addTransformer(new MyClassFileTransformer(), true);
    }
}
