# Spring Boot Demo

用 Java + Spring Boot 實作的 RESTful API 練習專案。

## 技術棧

- Java 17
- Spring Boot 3.5.14
- Spring Data JPA
- MySQL
- Maven

## 專案結構

```
src/main/java/com/example/demo/
├── DemoApplication.java        # 入口點
├── User.java                   # Entity
├── UserRepository.java         # 資料庫操作
├── UserService.java            # 商業邏輯
├── UserController.java         # HTTP 請求處理
├── GlobalExceptionHandler.java # 全域例外處理
└── UserNotFoundException.java  # 自訂 Exception
```

## 環境設定

### 需求
- JDK 17
- MySQL

### 資料庫設定
```sql
CREATE DATABASE demo;
```

`src/main/resources/application.properties`：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

### 啟動
```bash
./mvnw spring-boot:run
```

## API

| 方法 | 路徑 | 說明 |
|---|---|---|
| GET | /users | 取得所有使用者 |
| GET | /users/{id} | 取得單一使用者 |
| POST | /users | 新增使用者 |
| DELETE | /users/{id} | 刪除使用者 |

### 範例

**新增使用者**
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com"}'
```

**取得所有使用者**
```bash
curl http://localhost:8080/users
```

**刪除使用者**
```bash
curl -X DELETE http://localhost:8080/users/1
```

## 驗證規則

| 欄位 | 規則 |
|---|---|
| name | 不能是空的 |
| email | 不能是空的、需符合 email 格式 |
