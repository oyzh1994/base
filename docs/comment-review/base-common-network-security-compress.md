# cn.oyzh.common.network

## NetworkUtil

- 职责：网络工具类，提供服务端口常量、连通性探测、端口扫描与端口用途识别。
- 字段（端口常量，short 类型）：`FTP_PORT=21`、`SSH_PORT=22`、`HTTP_PORT=80`、`VNC_PORT=5900`、`RDP_PORT=3389`、`HTTPS_PORT=443`、`TELNET_PORT=23`、`RTSP_PORT=554`、`RLOGIN_PORT=513`、`Mysql_PORT=3306`、`Redis_PORT=6379`、`Oracle_PORT=1521`、`MongoDB_PORT=27017`、`Zookeeper_PORT=2281`、`PostgreSQL_PORT=5432`、`Memcached_PORT=11211`、`SQLServer_PORT=1433`、`Elasticsearch_PORT=9200`。含义为对应服务的默认端口。

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `boolean reachable(String,int,int)` | 主机端口是否可达 | 用 Socket 连接，比较耗时是否在超时内 |
| `List<Integer> scan(int,int,String,int)` | 同步扫描端口区间 | 校验参数后逐个 `reachable` 收集可达端口 |
| `Thread scanAsync(int,int,int,String,BiConsumer,Runnable)` | 异步扫描 | `ThreadUtil.start` 单线程遍历，命中回调，finally 执行结束回调 |
| `Thread scanMultiple(int,int,int,int,String,ExceptionBiConsumer,Runnable)` | 多线程扫描 | 端口列表 `CollectionUtil.splitIntoParts` 分片，每片一个线程，`CountDownLatch` 等待 |
| `String detectDesc(int)` | 识别端口用途 | 大段 if 判断返回服务名（HTTP/SSH/MySQL/RDP 等），未知返回 "Unknown" |

- 调用链：`NetworkUtil.scan → NetworkUtil.reachable → Socket.connect`
- 调用链：`NetworkUtil.scanMultiple → CollectionUtil.splitIntoParts → ThreadUtil.start → CountDownLatch.await`

## ProxyUtil

- 职责：代理工具类，提供 SOCKS5 握手及 HTTP/SOCKS 代理选择器创建。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void socks5Handshake(Socket,String,int,String,String)` | Socket 版 SOCKS5 握手 | 依次发送方法协商、认证、CONNECT 请求并校验响应 |
| `void socks5Handshake(SocketChannel,InetSocketAddress,String,String)` | SocketChannel 版握手 | 委托 `SocksPerformHandler.performSocksHandshake` |
| `boolean isNeedProxy(Proxy)` | 是否需要代理 | 非空且类型非 DIRECT |
| `ProxySelector createHttpProxySelector(String,int)` | 创建 HTTP 代理选择器 | 匿名 ProxySelector，select 返回 HTTP 代理 |
| `ProxySelector createSocksProxySelector(String,int)` | 创建 SOCKS 代理选择器 | 匿名 ProxySelector，select 返回 SOCKS 代理 |

- 调用链：`ProxyUtil.socks5Handshake(SocketChannel) → SocksPerformHandler.performSocksHandshake`

## SocksPerformHandler

- 职责：SOCKS5 协议握手处理器，基于 SocketChannel 完成认证与连接请求。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| proxyUsername | String | 代理用户名 |
| proxyPassword | String | 代理密码 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void performSocksHandshake(SocketChannel,InetSocketAddress)` | 执行 SOCKS5 握手 | 发送方法协商→校验版本→按选择执行认证或无认证→`buildConnectRequest` 发送连接请求→按地址类型读取剩余响应 |
| `void performUsernamePasswordAuth(SocketChannel)` | 用户名/密码认证 | 构造认证报文发送并校验响应（0x01、0x00） |
| `byte[] buildConnectRequest(InetSocketAddress)` | 构造连接请求 | 按 IPv4(0x01)/IPv6(0x04)/域名(0x03) 组装地址与端口 |
| `int readFully(SocketChannel,ByteBuffer,int)` | 至少读指定字节 | 循环读取，读到 0 时短暂休眠 |
| `int readFully(SocketChannel,ByteBuffer)` | 读满缓冲区 | 委托带 minBytes 版本 |
| `void writeFully(SocketChannel,ByteBuffer)` | 完整写出 | 循环写直到无剩余 |
| `String getSocksErrorDescription(byte)` | 错误码描述 | switch 映射 SOCKS 错误码到文本 |
| `String getProxyUsername()/setProxyUsername(String)`、`getProxyPassword()/setProxyPassword(String)` | 访问器 | 读写用户名/密码 |

- 调用链：`SocksPerformHandler.performSocksHandshake → performUsernamePasswordAuth / buildConnectRequest → readFully/writeFully`

# cn.oyzh.common.security

> 说明：`KeyGenerator`、`KeyUtil` 整文件被注释掉，属死代码，未纳入本审查。

## AESUtil

- 职责：AES 对称加密工具类，采用 AES-128-CBC(PKCS5)，随机 IV 随密文以 `Base64(IV):Base64(密文)` 携带。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| ALGORITHM | String | 算法模式 "AES/CBC/PKCS5Padding" |
| KEY_LENGTH | int | 密钥长度 16 字节 |
| IV_LENGTH | int | IV 长度 16 字节 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String encrypt(String,String)` | 加密 | `processKey` 处理密钥，`SecureRandom` 生成 IV，CBC 加密后返回 `ivBase64:encryptedBase64` |
| `String decrypt(String,String)` | 解密 | 按 `:` 拆分 IV 与密文，Base64 解码后用相同密钥解密 |
| `byte[] processKey(String)` | 归一化密钥 | 不足 16 字节补 0，过长截断 |
| `void main(String[])` | 演示 | 加密并解密示例字符串 |

- 调用链：`AESUtil.encrypt/decrypt → AESUtil.processKey → Cipher.init/doFinal`

## SHA256Util

- 职责：SHA256 摘要工具类，提供 SHA256 十六进制哈希与 HMAC-SHA256。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `String sha256Hex(String)` | SHA256 十六进制 | `MessageDigest.getInstance("SHA-256")` 后 `HexUtil.bytesToHex` |
| `byte[] hmacSha256(byte[],String)` | HMAC-SHA256 | `Mac.getInstance("HmacSHA256")` 初始化后 `doFinal` |

- 调用链：`SHA256Util.sha256Hex → MessageDigest.digest → HexUtil.bytesToHex`

## TrustAllX509TrustManager

- 职责：信任所有证书的 X509 信任管理器（仅测试/自签名场景，生产禁用）。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| INSTANCE | TrustAllX509TrustManager | 单例实例 |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void checkClientTrusted(X509Certificate[],String)` | 校验客户端证书 | 空实现，不做校验 |
| `void checkServerTrusted(X509Certificate[],String)` | 校验服务端证书 | 空实现，不做校验 |
| `X509Certificate[] getAcceptedIssuers()` | 受信颁发者 | 返回空数组 |

- 调用链：`TrustAllX509TrustManager.INSTANCE`（供 SSLContext 使用）

# cn.oyzh.common.compress

## ArchiveUtil

- 职责：跨平台文件归档工具，支持 ZIP/TAR/TAR.GZ 并保留 Unix 权限。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| 无 | - | - |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void createZip(File,File)` / `(Path,Path)` | 目录打包为 ZIP | `Files.walk` 遍历，跳过 .DS_Store 与根目录空条目，Linux/macOS 用 `FileUtil.getUnixMode` 设置 Unix 权限 |
| `void createTar(File,File)` / `(Path,Path)` | 目录打包为 TAR | 设置 LONGFILE_POSIX，`addFilesToTar` |
| `void createTarGz(File,File)` / `(Path,Path)` | 目录打包为 TAR.GZ | 在 TAR 基础上套 `GzipCompressorOutputStream` |
| `void addFilesToTar(Path,Path,TarArchiveOutputStream)` | 递归添加文件到 TAR | 目录末尾加 `/`，非 Linux/MacOS 不设 mode，目录递归处理 |

- 调用链：`ArchiveUtil.createTarGz → GzipCompressorOutputStream → addFilesToTar`

## CompressUtil

- 职责：压缩工具类，支持 ZIP/TAR/TAR_GZ 压缩与 ZIP 解压。
- 字段：

| 字段 | 类型 | 含义 |
|---|---|---|
| BUFFER_SIZE | int | 复制缓冲区大小 8192 |
| CompressType | enum | 压缩类型枚举：ZIP、TAR、TAR_GZ |

- 方法：

| 方法 | 说明 | 关键逻辑/调用 |
|---|---|---|
| `void compress(List<File>,String,CompressType)` | 压缩文件/文件夹 | 校验源存在；按类型创建 Zip/Tar/Gzip 输出流并 `addFilesToArchive` |
| `void addFilesToArchive(ArchiveOutputStream,List<File>,String)` | 递归添加条目 | 目录加 `/` 后递归子文件，文件用 `IOUtils.copy` 写入 |
| `void compress(String,String,CompressType)` | 压缩单个路径 | 委托列表版 |
| `File unzip(String,String)` / `(File,File)` | 解压 ZIP | 校验路径归属防目录穿越，`ZipFile` 遍历解压 |
| `File zipDest(String,String)` | 打包目录为 zip | 调用 `ArchiveUtil.createZip` |
| `File zipDestByMacos(String,String)` | macOS 专用 zip | 同上 |
| `File tarDest(String,String)` / `File tgzDest(String,String)` | 打包 tar/tar.gz | 调用 `ArchiveUtil.createTar/createTarGz` |

- 调用链：`CompressUtil.compress → addFilesToArchive → ArchiveOutputStream.putArchiveEntry`
- 调用链：`CompressUtil.zipDest → ArchiveUtil.createZip`
