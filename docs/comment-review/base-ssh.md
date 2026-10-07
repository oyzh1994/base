# base-ssh

> 模块：base-ssh　包：cn.oyzh.ssh、.domain、.jump、.tunneling、.util
> 说明：基于 Apache MINA SSHD（2 版）+ JGit（ssh agent 支持）实现的 SSH 连接、跳板机转发与隧道转发能力；域名对象用 `@Column` 注解支持持久化。
> 跳过（整文件注释的死代码）：`SSHForwarder`、`domain/SSHJumpConfig`、`jump/SSHJumpForwarder`、`tunneling/SSHTunnelingForwarder`、`util/JschLogger`、`util/JschUtil`、`util/SSHHolder`、`util/OpenSSHRSAUtil`、`util/OpenSSHED25519Util`、`util/OpenSSHECDSAUtil`（这些均为旧 jsch 实现或历史实现，全部以注释保留）。

## SSHException
> 包：cn.oyzh.ssh

- 职责：SSH 运行时异常。
- 字段：无（继承 RuntimeException）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `SSHException()` / `(String)` / `(String, Throwable)` / `(Exception)` | 四种构造 | 委托父类构造 |

- 调用链：`SSHJumpForwarder2.forward → throw SSHException`

## SSHForwarder2
> 包：cn.oyzh.ssh

- 职责：SSH 转发器基类（SSHD 版），持有会话集合并负责清理端口转发。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | sessions | Set\<ClientSession\> | 会话集合（ConcurrentHashMap.newKeySet，final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void close()` | 关闭 | 遍历会话的本地/远程转发绑定并逐一 `stopLocalPortForwarding`/`stopRemotePortForwarding`，清空集合 |

- 调用链：`SSHJumpForwarder2.close → super.close()`；`SSHTunnelingForwarder2` 复用 sessions

## SSHConnect
> 包：cn.oyzh.ssh.domain

- 职责：SSH 连接信息模型（地址、端口、用户、认证方式、证书/密钥等），支持字段级持久化与对象拷贝。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | name | String | 名称（为空返回 "untitled"） |
  | order | int | 顺序 |
  | port | int | 连接端口，默认 22 |
  | host | String | 连接地址 |
  | user | String | ssh 用户名 |
  | password | String | ssh 密码 |
  | timeout | int | 连接超时（毫秒），最小 3000 |
  | authMethod | String | 认证方式（password/certificate/key/sshAgent） |
  | forwardAgent | boolean | 是否客户端转发 |
  | certificatePath | String | 证书路径 |
  | certificatePwd | String | 证书密码 |
  | certificatePubKey | String | 公钥 |
  | certificatePriKey | String | 私钥 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `isPasswordAuth/isCertificateAuth/isKeyAuth/isSSHAgentAuth` | 认证方式判定 | 依据 authMethod 字符串比较 |
  | `getTimeout()/setTimeout(int)` | 超时读写 | 小于 3000 时按 3000 处理 |
  | `getTimeoutSecond()` | 超时秒值 | timeout/1000 |
  | `getCertificatePubKeyBytes()/getCertificatePriKeyBytes()` | 密钥字节 | `getBytes(UTF_8)` |
  | `copy(SSHConnect)` | 对象拷贝 | 逐字段复制（ObjectCopier 实现） |
  | 各 `get/set` | 属性读写 | 简单 getter/setter |
  | `toString()` | 描述 | 输出全部字段 |

- 调用链：`SSHJumpForwarder2.initSession(connect) → connect.isXxxAuth()`；`SSHJumpForwarder2.forward → connect.getHost/getPort`

## SSHProxyConfig
> 包：cn.oyzh.ssh.domain

- 职责：SSH 代理配置（协议、地址、端口、认证），支持 http/socks4/socks5 判定。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | protocol | String | 代理协议 |
  | host | String | 连接地址 |
  | port | int | 连接端口 |
  | authType | String | 认证类型 |
  | user | String | 用户名 |
  | password | String | 密码 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `isHttpProxy/isSocksProxy/isSocks4Proxy/isSocks5Proxy/isNoneProxy` | 代理类型判定 | 依据 protocol（`@JSONField` 不参与序列化） |
  | `isPasswordAuth()` | 是否密码认证 | authType == "password" |
  | `copy(SSHProxyConfig)` | 对象拷贝 | 逐字段复制 |
  | 各 `get/set` | 属性读写 | 简单 getter/setter |

- 调用链：`SSHProxyConfig.isSocks5Proxy`（供代理构建逻辑分支）

## SSHTunneling
> 包：cn.oyzh.ssh.domain

- 职责：SSH 隧道配置（名称、类型、本地/远程地址端口），支持本地/远程/动态转发判定。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | name | String | 名称 |
  | type | String | 类型（local/remote/dynamic） |
  | localPort | int | 本地端口 |
  | localHost | String | 本地地址 |
  | remotePort | int | 远程端口 |
  | remoteHost | String | 远程地址 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `isLocalType/isRemoteType/isDynamicType` | 转发类型判定 | 比较 type 字符串 |
  | `getLocalHostName()` / `getRemoteHostName()` | 地址端口串 | `host + ":" + port` |
  | `copy(SSHTunneling)` | 对象拷贝 | 逐字段复制 |
  | 各 `get/set` | 属性读写 | 简单 getter/setter |

- 调用链：`SSHTunnelingForwarder2.forward → tunneling.isLocalType/isRemoteType/isDynamicType`

## SSHJumpForwarder2
> 包：cn.oyzh.ssh.jump

- 职责：SSH 跳板转发器（SSHD/JGit 版），逐级建立跳板会话并做本地端口转发。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | clients | Set\<SshClient\> | 客户端集合（ConcurrentHashMap.newKeySet，final） |
  | userInteraction | UserInteraction | 交互式认证处理器 |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `JGitSshClient initClient(SSHConnect)` | 初始化客户端 | 构建 ClientBuilder（KeepAlive、DH 交换、签名/通道工厂），ssh agent 用 `SSHAgentConnectorFactory`；设置优先认证方式、认证工厂、交互、信任所有服务端 key、心跳、超时；`sshClient.start()` 并入列表 |
  | `ClientSession initSession(SSHConnect)` | 建立会话 | `connect(entry)` 后按认证方式设置密码/证书（`SSHKeyUtil.loadKeysFromFile`）/密钥（`loadKeysForStr`），`session.auth().verify` |
  | `int forward(List<? extends SSHConnect>, SSHConnect)` | 端口转发 | 依次对每跳建会话，`SSHUtil.findAvailablePort` 取本地端口，`session.startLocalPortForwarding` 转发到下一跳/目标；异常时 `close()` 并抛 SSHException |
  | `void close()` | 关闭 | 先 `super.close()` 清理端口转发，再关闭所有 SshClient |
  | `getUserInteraction/setUserInteraction` | 交互处理器读写 | 简单 getter/setter |

- 调用链：`业务 → SSHJumpForwarder2.forward → initSession → initClient → SshClient.connect`；`forward → session.startLocalPortForwarding → SSHUtil.findAvailablePort`

## SSHTunnelingForwarder2
> 包：cn.oyzh.ssh.tunneling

- 职责：SSH 隧道转发器（SSHD 版），对既有会话建立本地/远程/动态端口转发。
- 字段：无（继承 SSHForwarder2.sessions）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `void forward(List<? extends SSHTunneling>, ClientSession)` | 端口转发 | 遍历隧道：local→`startLocalPortForwarding`，remote→`startRemotePortForwarding`，dynamic→`startDynamicPortForwarding`；加入 sessions；IOException 抛 SSHException |

- 调用链：`业务 → SSHTunnelingForwarder2.forward → session.startLocalPortForwarding/startRemotePortForwarding/startDynamicPortForwarding`

## SSHAgentConnectorFactory
> 包：cn.oyzh.ssh.util

- 职责：SSH agent 连接工厂，按平台创建 agent 连接器（Windows 用 Pageant，类 Unix 用 Unix 域套接字）。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `SSHAgentConnectorFactory()` / `(File)` / `(ConnectorFactory, File)` | 构造 | 委托父类 JGitSshAgentFactory |
  | `Connector create(String, File)` | 创建连接器 | Windows→PageantConnector；否则取 `SSHUtil.getSSHAgentSockFile` 建 UnixDomainSocketConnector |
  | `boolean isSupported()` | 是否支持 | 返回 false |
  | `getName()` | 名称 | "ssh agent" |
  | `getSupportedConnectors()` / `getDefaultConnector()` | 连接器列表/默认 | 空列表 / null |
  | `List<ChannelFactory> getChannelForwardingFactories(FactoryManager)` | 通道转发工厂 | OPENSSH、IETF |

- 调用链：`SSHJumpForwarder2.initClient → sshClient.setAgentFactory(new SSHAgentConnectorFactory())`

## SSHUtil
> 包：cn.oyzh.ssh.util

- 职责：SSH 通用工具，本地可用端口探测、控制字符/ANSI 清理、ssh agent sock 文件定位。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | EXCLUDED_PORT | Set\<Integer\> | 排除端口集合（21/22/80/443/3306/6379/8080 等，final static） |
  | ANSI_PATTERN | Pattern | ANSI 转义正则（惰性初始化） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `findAvailablePort()` / `(Set<Integer>)` / `(Set<Integer>, int, int)` | 找可用端口 | 在 10000-12000 区间跳过排除端口，`isPortAvailable` 校验，找不到返回 -1 |
  | `isPortAvailable(int, int)` | 端口是否可用 | 尝试 bind 127.0.0.1:port |
  | `removeAnsi(String)` | 去 ANSI | 正则替换 |
  | `removeControl(String)` | 去控制字符 | 移除 `\b \a \r \t \n` |
  | `getSSHAgentSockFile()` | 定位 agent sock | 读 `SSH_AUTH_SOCK` 并 `ssh-add -l` 校验；可疑时 macOS 下遍历 `TMPDIR` 的 `ssh-*` 目录 |

- 调用链：`SSHJumpForwarder2.forward → SSHUtil.findAvailablePort`；`SSHAgentConnectorFactory.create → SSHUtil.getSSHAgentSockFile`

## SSHKeyUtil
> 包：cn.oyzh.ssh.util

- 职责：SSH 密钥工具，生成各类密钥对、探测密钥长度/类型、从字符串/字节/文件加载密钥。
- 字段：无
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `generateEd25519(int, String)` / `generateRsa` / `generateDsa` / `generateEcdsa` | 生成密钥对 | `KeyUtils.generateKeyPair` 后用 `OpenSSHKeyPairResourceWriter` 输出 OpenSSH 公私钥字符串，密码非空时用 `OpenSSHKeyEncryptionContext` |
  | `getKeySize(String, String)` | 取密钥长度 | `SecurityUtils.loadKeyPairIdentities` 加载后 `KeyUtils.getKeySize` |
  | `getKeyType(String, String)` | 取密钥类型 | 同上，`KeyUtils.getKeyType` |
  | `loadKeysForStr(String, String)` | 由文本加载 | 委托 `loadKeysForBytes` |
  | `loadKeysForBytes(byte[], String)` | 由字节加载 | `SecurityUtils.loadKeyPairIdentities`，密码用 `FilePasswordProvider.of` |
  | `loadKeysFromFile(String, String)` | 由文件加载 | 同上，FileInputStream 后关闭 |

- 调用链：`SSHJumpForwarder2.initSession → SSHKeyUtil.loadKeysFromFile/loadKeysForStr`

## PemUtil
> 包：cn.oyzh.ssh.util

- 职责：PEM 文件解析工具（基于 BouncyCastle），加载证书与私钥。
- 字段：无（静态块注册 BouncyCastleProvider）
- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `static PemKeyCertData loadKeyAndCertificates(String, String)` | 解析证书+私钥 | `PEMParser` 逐对象解析 X509CertificateHolder/PEMKeyPair/PrivateKeyInfo/PEMEncryptedKeyPair（加密需密码），返回数据类 |
  | `static X509Certificate[] loadCertificates(String)` | 仅加载证书 | 遍历 PEM 取 X509CertificateHolder 转证书 |

- 调用链：`PemUtil.loadKeyAndCertificates → PEMParser.readObject → JcaPEMKeyConverter`

### PemKeyCertData（PemUtil 内部静态类）
> 包：cn.oyzh.ssh.util

- 职责：承载解析结果（私钥 + 证书列表）。
- 字段：

  | 字段 | 类型 | 含义 |
  |---|---|---|
  | privateKey | PrivateKey | 私钥（final） |
  | certificates | List\<X509Certificate\> | 证书列表（final） |

- 方法：

  | 方法 | 说明 | 关键逻辑/调用 |
  |---|---|---|
  | `PemKeyCertData(PrivateKey, List<X509Certificate>)` | 构造 | 赋值 |
  | `getPrivateKey()` / `getCertificates()` | 取值 | 返回字段 |

- 调用链：`PemUtil.loadKeyAndCertificates → new PemKeyCertData`
