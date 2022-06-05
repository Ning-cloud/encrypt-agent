基于java agent和ASM库实现的可插拔的字段级透明脱敏工具

1. 下载项目
2. mvn install 将项目安装到本地代码仓库
3. 引入项目
4. 配置启动参数：

使用样例
```
1. 在需要进行脱敏的类上标注@Encrypt

2. 在需要进行解密操作的字段上标注@FieldDecrypt注解

3. 在需要进行加密操作的字段上标注@FieldEncrypt注解
```