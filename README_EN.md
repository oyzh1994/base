# base

English | [简体中文](README.md)

`base` is a Java foundation library providing shared utilities, internationalization, eventing, storage, SSH, packaging, and integration foundations for MongoDB, Mosh, and ZooKeeper. It reduces the need for business projects to depend directly on scattered third-party components.

## Requirements

- JDK 25
- Maven 3.9 or newer

## Module Structure

| Module | Description |
|---|---|
| `base-common` | Core utilities for files, logging, networking, security, JSON, XML, Excel, threading, and common operations |
| `base-event` | Eventing module with a general-purpose event bus |
| `base-i18n` | Internationalization resources, locale management, and translation integration |
| `base-mongodb` | MongoDB support and driver-related components |
| `base-mosh` | Mosh protocol, transport, and terminal support |
| `base-pkg` | Cross-platform packaging for JAR, JLink, JPackage, AppImage, and related workflows |
| `base-ssh` | SSH clients, jump hosts, and port forwarding |
| `base-store` | File, JDBC, and embedded-database storage support |
| `base-zookeeper` | ZooKeeper client, protocol, and command-line support |

## Maven

Install all modules:

```bash
mvn clean install -DskipTests
```

Run tests:

```bash
mvn test
```

## Notes

- Make sure `java -version` matches the project requirement; otherwise Maven may report `invalid target release: 25`.
- The modules depend on one another. Run Maven from the repository root so they are built in the correct order.

## License

The project includes `APACHE-LICENSE-2.0.txt` and `MIT-LICENSE.txt`. Review the applicable terms before use.
