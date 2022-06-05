基于java agent和ASM库实现的可插拔的字段级透明脱敏工具，目前支持对string类型字段的setter方法进行加密、对string类型的getter方法进行解密，加密算法目前仅支持AES ECB。

1. 下载项目

   ```
   git clone https://github.com/Ning-cloud/encrypt-agent.git
   ```
   
2. mvn install 将项目安装到本地代码仓库

3. 引入项目

   ```
   <dependency>
     <groupId>com.nsy</groupId>
     <artifactId>encrypt-agent</artifactId>
     <version>1.0</version>
   </dependency>
   ```

   

4. 配置启动参数：

   ```
   -javaagent:/Users/ningshangyong/Downloads/encrypt-agent/target/encrypt-agent-1.0.jar=ECB,1234567890123456
   ```

   agent的参数用英文逗号分隔，第一个参数为加密需要使用的算法，第二个参数表示加密使用的key

使用样例
```
1. 在需要进行脱敏的类上标注@Encrypt

2. 在需要进行解密操作的字段上标注@FieldDecrypt注解

3. 在需要进行加密操作的字段上标注@FieldEncrypt注解
```