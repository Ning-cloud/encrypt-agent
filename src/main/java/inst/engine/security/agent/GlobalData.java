package inst.engine.security.agent;

import java.util.ArrayList;
import java.util.List;

public enum GlobalData {
    INSTANCE;

    private List<String> encryptMethodList = new ArrayList<>();
    private List<String> decryptMethodList = new ArrayList<>();
    private String algorithm;
    private String key;
    private String currentProcessingClass;

    public String getCurrentProcessingClass() {
        return this.currentProcessingClass;
    }

    public void setCurrentProcessingClass(String currentProcessingClass) {
        this.currentProcessingClass = currentProcessingClass;
    }

    public void setEncryptMethod(String name) {
        this.encryptMethodList.add(name);
    }

    public List<String> getEncryptMethod() {
        return this.encryptMethodList;
    }

    public void setDecryptMethod(String name) {
        this.decryptMethodList.add(name);
    }

    public List<String> getDecryptMethod() {
        return this.decryptMethodList;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void removeStoredMethod() {
        this.encryptMethodList.clear();
        this.decryptMethodList.clear();
    }
}
