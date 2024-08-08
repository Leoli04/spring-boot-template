```javascript
keytool -genkey -alias icai -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore D:\httpsKey.p12 -validity 3650
```

命令含义如下：

> - keytool: 表示keytool工具 
> - genkey：表示要创建一个新的密钥。 
> - alias：表示 keystore 的别名。anyname 都可以,这里是icai 
> - storetype：表示密钥的仓库类型，存储格式是PKCS12. 
> - keyalg：表示使用的加密算法是 RSA ，一种非对称加密算法。
> -  keysize：表示密钥的长度。这里是2048.
> -  keystore：表示生成的证书文件存放位置。 这里是D:\httpsKey.p12 ，有时候放C盘可能有权限问题 
> - validity：表示证书的有效时间，单位为天。这里是3650天也就是十年。