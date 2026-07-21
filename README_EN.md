# base

A Java integration library providing i18n, SSH, event bus, storage, and various utilities such as string processing — reducing dependency on third-party libraries.

---

## Dependencies

1. **JDK Version**: 25 is required

---

## Module Structure

| Module | Description |
|---|---|
| `base-common` | Core module — various utility implementations |
| `base-event` | Event module — event bus implementation |
| `base-i18n` | i18n module — internationalization resources and implementation |
| `base-ssh` | SSH module — SSH client implementation |
| `base-store` | Store module — data storage implementation |

---

## Maven

### Build
```bash
mvn -X clean install -DskipTests
```

### Notes
- Ensure that `java -version` in your terminal matches the project's JDK version. Mismatches cause errors like "invalid target release: 25".
