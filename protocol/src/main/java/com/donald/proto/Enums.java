// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: enums.proto

package com.donald.proto;

public final class Enums {
  private Enums() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  /**
   * Protobuf enum {@code ActionType}
   */
  public enum ActionType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <pre>
     * 默认
     * </pre>
     *
     * <code>DEFAULT = 0;</code>
     */
    DEFAULT(0),
    /**
     * <pre>
     * 心跳
     * </pre>
     *
     * <code>HEARTBEAT = 1;</code>
     */
    HEARTBEAT(1),
    /**
     * <pre>
     * 连接
     * </pre>
     *
     * <code>CONNECT = 2;</code>
     */
    CONNECT(2),
    /**
     * <pre>
     * 离线
     * </pre>
     *
     * <code>OFFLINE = 3;</code>
     */
    OFFLINE(3),
    /**
     * <pre>
     *&#47;//////////////////////// 单聊消息 /////////////////////
     * </pre>
     *
     * <code>SEND_C2C_MSG = 256;</code>
     */
    SEND_C2C_MSG(256),
    /**
     * <pre>
     * 发送消息响应
     * </pre>
     *
     * <code>SEND_C2C_MSG_RESPONSE = 257;</code>
     */
    SEND_C2C_MSG_RESPONSE(257),
    /**
     * <pre>
     * 消息多端同步
     * </pre>
     *
     * <code>SYNC_C2C_MSG = 258;</code>
     */
    SYNC_C2C_MSG(258),
    UNRECOGNIZED(-1),
    ;

    /**
     * <pre>
     * 默认
     * </pre>
     *
     * <code>DEFAULT = 0;</code>
     */
    public static final int DEFAULT_VALUE = 0;
    /**
     * <pre>
     * 心跳
     * </pre>
     *
     * <code>HEARTBEAT = 1;</code>
     */
    public static final int HEARTBEAT_VALUE = 1;
    /**
     * <pre>
     * 连接
     * </pre>
     *
     * <code>CONNECT = 2;</code>
     */
    public static final int CONNECT_VALUE = 2;
    /**
     * <pre>
     * 离线
     * </pre>
     *
     * <code>OFFLINE = 3;</code>
     */
    public static final int OFFLINE_VALUE = 3;
    /**
     * <pre>
     *&#47;//////////////////////// 单聊消息 /////////////////////
     * </pre>
     *
     * <code>SEND_C2C_MSG = 256;</code>
     */
    public static final int SEND_C2C_MSG_VALUE = 256;
    /**
     * <pre>
     * 发送消息响应
     * </pre>
     *
     * <code>SEND_C2C_MSG_RESPONSE = 257;</code>
     */
    public static final int SEND_C2C_MSG_RESPONSE_VALUE = 257;
    /**
     * <pre>
     * 消息多端同步
     * </pre>
     *
     * <code>SYNC_C2C_MSG = 258;</code>
     */
    public static final int SYNC_C2C_MSG_VALUE = 258;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ActionType valueOf(int value) {
      return forNumber(value);
    }

    public static ActionType forNumber(int value) {
      switch (value) {
        case 0: return DEFAULT;
        case 1: return HEARTBEAT;
        case 2: return CONNECT;
        case 3: return OFFLINE;
        case 256: return SEND_C2C_MSG;
        case 257: return SEND_C2C_MSG_RESPONSE;
        case 258: return SYNC_C2C_MSG;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ActionType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        ActionType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ActionType>() {
            public ActionType findValueByNumber(int number) {
              return ActionType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.donald.proto.Enums.getDescriptor().getEnumTypes().get(0);
    }

    private static final ActionType[] VALUES = values();

    public static ActionType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private ActionType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:ActionType)
  }

  /**
   * <pre>
   * 消息类型
   * </pre>
   *
   * Protobuf enum {@code ChatType}
   */
  public enum ChatType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <pre>
     * 单聊
     * </pre>
     *
     * <code>C2C = 0;</code>
     */
    C2C(0),
    /**
     * <pre>
     * 群聊
     * </pre>
     *
     * <code>GROUP_CHAT = 1;</code>
     */
    GROUP_CHAT(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <pre>
     * 单聊
     * </pre>
     *
     * <code>C2C = 0;</code>
     */
    public static final int C2C_VALUE = 0;
    /**
     * <pre>
     * 群聊
     * </pre>
     *
     * <code>GROUP_CHAT = 1;</code>
     */
    public static final int GROUP_CHAT_VALUE = 1;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ChatType valueOf(int value) {
      return forNumber(value);
    }

    public static ChatType forNumber(int value) {
      switch (value) {
        case 0: return C2C;
        case 1: return GROUP_CHAT;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ChatType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        ChatType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ChatType>() {
            public ChatType findValueByNumber(int number) {
              return ChatType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.donald.proto.Enums.getDescriptor().getEnumTypes().get(1);
    }

    private static final ChatType[] VALUES = values();

    public static ChatType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private ChatType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:ChatType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013enums.proto*\204\001\n\nActionType\022\013\n\007DEFAULT\020" +
      "\000\022\r\n\tHEARTBEAT\020\001\022\013\n\007CONNECT\020\002\022\013\n\007OFFLINE" +
      "\020\003\022\021\n\014SEND_C2C_MSG\020\200\002\022\032\n\025SEND_C2C_MSG_RE" +
      "SPONSE\020\201\002\022\021\n\014SYNC_C2C_MSG\020\202\002*#\n\010ChatType" +
      "\022\007\n\003C2C\020\000\022\016\n\nGROUP_CHAT\020\001B\022\n\020com.donald." +
      "protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
