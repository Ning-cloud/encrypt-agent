package inst.engine.security.agent;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class EncryptMethodVisitor extends AdviceAdapter {

    private String methodName;

    protected EncryptMethodVisitor(int api, MethodVisitor methodVisitor, int access,
        String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        char[] cs = this.methodName.substring(3).toCharArray();
        cs[0]+=32;
        String lowerName = String.valueOf(cs);

        mv.visitCode();
        // message
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        // key
        mv.visitLdcInsn(GlobalData.INSTANCE.getKey());
        mv.visitMethodInsn(INVOKESTATIC, "inst/engine/security/utils/EncryptUtils", "encryptECB", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false);
        mv.visitFieldInsn(PUTFIELD, GlobalData.INSTANCE.getCurrentProcessingClass(), lowerName, "Ljava/lang/String;");
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();
    }

    @Override
    protected void onMethodExit(int opcode) {
        // 方法结束处插入
    }
}
