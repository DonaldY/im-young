### `Protobuf` 使用

官网下载 `Protobuf`： [官网](https://github.com/protocolbuffers/protobuf/releases/tag/v3.9.1)

1. **下载：`protobuf-java-3.9.1.tar.gz`**
2. **安装 （环境 `Ubuntu 18`）**


```shell
cd protobuf-3.9.1
./autogen.sh
./configure
make
make check
sudo make install
protoc --version
```

3. **编译生成文件：对应目录下会生成对应的文件**

```shell
protoc –java_out=im/src im/proto/AuthenticateRequest.proto

# 如果 proto 中有引用别的 proto，则加 --proto_path
```


**举个栗子：**

```shell
├─ java
│   └─ com.donald.proto
├─ proto
│   ├─ base.proto   # 引用了 enums.proto
│   └─ enums.proto
└─ resources
```



**`shell` 脚本批量生成：**根据 `proto` 目录下，生成对应 `java` 文件到 `com.donald.proto`

```shell
#!/bin/bash

cd ../proto

for file in ./*
do
    echo process file: $file
    protoc --proto_path=./ --java_out=../java $file
done
```
