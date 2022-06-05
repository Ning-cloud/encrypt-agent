package inst.engine.security.agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class EncryptAdviceClassFileVisitor extends ClassVisitor {

    private int api;

    public EncryptAdviceClassFileVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
        this.api = api;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
        String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("<init>")) return mv;
        if (GlobalData.INSTANCE.getEncryptMethod().contains(name)) {
            return new EncryptMethodVisitor(this.api, mv, access, name, descriptor);
        } else if (GlobalData.INSTANCE.getDecryptMethod().contains(name)) {
            return new DecryptMethodVisitor(this.api, mv, access, name, descriptor);
        } else {
            return mv;
        }
    }
}
