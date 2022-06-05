package inst.engine.security.agent;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class DecryptMethodVisitor extends AdviceAdapter {

    private String methodName;

    protected DecryptMethodVisitor(int api, MethodVisitor methodVisitor, int access,
        String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        char[] cs = this.methodName.substring(3).toCharArray();
        cs[0]+=32;
        String lowerName = String.valueOf(cs);

        // 方法开始前插入
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, GlobalData.INSTANCE.getCurrentProcessingClass(), lowerName, "Ljava/lang/String;");
        // 加载常量
        mv.visitLdcInsn(GlobalData.INSTANCE.getKey());
        mv.visitMethodInsn(INVOKESTATIC, "inst/engine/security/utils/EncryptUtils", "decryptECB", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();
    }

    @Override
    protected void onMethodExit(int opcode) {
        // 方法结束处插入
    }
}
