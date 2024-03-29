
目录结构：
```shell
├─ common      # 公共
├─ protocol    # 协议
├─ gateway     # 网关
├─ business    # 业务组件
└─ sdk-android # android 对接
```


#### 协议

基础知识准备：[TCP粘包？粘包警察是什么梗](https://juejin.cn/post/7135839422360551455)


自定义协议：
> 这里借鉴 `MQTT` 协议的固定头部、载荷长度计算，以及编解码。

**主要分为 3个 部分：**

1. 定义协议体
2. 编码器：`MsgEncoder`
3. 解码器：`MsgDecoder`


<font color=blur>**`Tips`：载荷里存储的数据，以 `Protobuf` 进行编解码。**</font>


**定义协议体：** 由固定头部和载荷构成。
| 名称                     | 说明                         |
| :----------------------- | :--------------------------- |
| 固定头部（Fixed header） | 固定报头，所有控制报文都包含 |
| 载荷（Payload）          | 有效载荷，部分控制报文包含   |

**详细字节如下：**
```shell
+-------------------------------+
| 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
+-------------------------------+
| 魔数 4bit      | 版本 4bit     |
+-------------------------------+
| 延续位 1bit | 数据体字节个数 7bit|
+-------------------------------+
```


#### 网关层



#### 客户端

1. 搭建

2. 测试