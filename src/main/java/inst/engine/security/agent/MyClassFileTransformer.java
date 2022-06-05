package inst.engine.security.agent;

import static org.objectweb.asm.Opcodes.ASM7;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class MyClassFileTransformer implements ClassFileTransformer {

    public MyClassFileTransformer() {

    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classfileBuffer)
        throws IllegalClassFormatException {

        if (className.startsWith("java") || className.startsWith("jdk") || className.startsWith("javax") || className.startsWith("sun") || className.startsWith("com/sun")) {
            //return null或者执行异常会执行原来的字节码
            return null;
        }

        ClassNode cn = new ClassNode();
        ClassReader cr = new ClassReader(classfileBuffer);
        cr.accept(cn, 0);
        //获取声明的所有注解
        List<AnnotationNode> annotations = cn.visibleAnnotations;
        if (annotations != null) {
            for (AnnotationNode node : annotations) {
                // 完全限定类名
                String fqcn = node.desc.replaceAll("/", ".");
                String annoName = fqcn.substring(1, fqcn.length()-1);
                if ("inst.engine.security.annotation.Encrypt".equals(annoName)) {
                    System.out.println("处理类：" + className);
                    GlobalData.INSTANCE.setCurrentProcessingClass(className);
                    List<FieldNode> fields = cn.fields;
                    for (FieldNode fieldNode : fields) {
                        List<AnnotationNode> fieldAnnList = fieldNode.visibleAnnotations;
                        for (AnnotationNode fieldAnn : fieldAnnList) {
                            // 加密逻辑
                            if ("Linst/engine/security/annotation/FieldEncrypt;".equals(fieldAnn.desc)) {
                                char[] cs = fieldNode.name.toCharArray();
                                cs[0]-=32;
                                String upperName = String.valueOf(cs);
                                GlobalData.INSTANCE.setEncryptMethod("set" + upperName);
                            }

                            // 解密逻辑
                            if ("Linst/engine/security/annotation/FieldDecrypt;".equals(fieldAnn.desc)) {
                                char[] cs = fieldNode.name.toCharArray();
                                cs[0]-=32;
                                String upperName = String.valueOf(cs);
                                GlobalData.INSTANCE.setDecryptMethod("get" + upperName);
                            }
                        }
                    }
                    ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
                    ClassVisitor cv = new EncryptAdviceClassFileVisitor(ASM7, cw);
                    cr.accept(cv, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
                    GlobalData.INSTANCE.removeStoredMethod();
                    return cw.toByteArray();
                }
            }
        }
        return classfileBuffer;
    }
}
