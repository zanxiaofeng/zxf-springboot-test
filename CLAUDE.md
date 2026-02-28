# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build everything
mvn clean install

# Build without tests
mvn clean install -DskipTests

# Build a single module
mvn clean install -pl zxf-springboot-ea-service

# Run all tests
mvn test

# Run tests for one module
mvn test -pl zxf-springboot-ea-service

# Run a specific test class
mvn test -pl zxf-springboot-ea-service -Dtest=HomeControllerTest

# Run a specific test method
mvn test -pl zxf-springboot-ea-service -Dtest=HomeControllerTest#testMethod

# Start EA service (port 8080)
cd zxf-springboot-ea-service && mvn spring-boot:run

# Start PA service (port 8081)
cd zxf-springboot-pa-service && mvn spring-boot:run

# Start MySQL dependency
docker compose up -d
```

**JDK**: Use JDK 21 (`/home/davis/.jdks/ms-21.0.10`). Set `JAVA_HOME` if needed.

## Architecture Overview

This is a two-service Spring Boot 3.5.11 / Java 21 project demonstrating REST API and testing patterns.

### Services

- **EA Service** (`zxf-springboot-ea-service`, port 8080): The primary service. Exposes REST endpoints (`/a/json`, `/b/json`, `/c/json`), calls PA service via HTTP, and queries MySQL.
- **PA Service** (`zxf-springboot-pa-service`, port 8081): A downstream partner API service. Standalone with its own REST endpoints. EA service calls it via `PAClient`.

### EA Service Layers

```
Controller (rest/) → Service (service/) → Client (client/) → PA Service (HTTP)
                   ↘                   ↘
                    DBService           NamedParameterJdbcTemplate → MySQL
```

- **`client/PAClient`**: Makes HTTP calls to PA service using a custom-configured `RestTemplate`
- **`client/http/`**: `RestTemplateFactory`, `LoggingRequestInterceptor` (logs + filters sensitive headers), `MyResponseErrorHandler`, buffering response wrapper
- **`configuration/ClientHttpRequestFactoryConfiguration`**: Configures separate timeouts for internal vs external HTTP calls via `httpclient5` (`HttpComponentsClientHttpRequestFactory`)
- **`jdbc/ProjectRowMapper`**: Maps SQL result sets to domain objects

### Database

- **Production**: MySQL on port 3308 (Docker), database `ea-service`
- **Tests**: H2 in-memory (`:mem:test`). The test `application.properties` sets `spring.sql.init.mode=never` to prevent duplicate script execution; `@Sql` annotations control data setup/teardown per test.

### Test Architecture

Five distinct test approaches coexist in `zxf-springboot-ea-service/src/test/`:

| Class | Approach | Key Annotations |
|---|---|---|
| `SmokeTest` | Context load | `@SpringBootTest` |
| `HomeControllerTest`, `BControllerTest` | Full context + MockMvc | `@SpringBootTest(MOCK)` + `@AutoConfigureMockMvc` |
| `AControllerTest` | Full context + TestRestTemplate + spy | `@SpringBootTest(RANDOM_PORT)` + `@MockitoSpyBean` |
| `CControllerTest` | Web layer slice + spy | `@WebMvcTest` + `@Import(PAService.class)` + `@MockitoSpyBean` |
| `ApiTestsWithMockModeTest` | MockMvc + WireMock | `@SpringBootTest(MOCK)` + `@WireMockTest(8089)` |
| `ApiTestsWithServerModeTest` | Full server + TestRestTemplate | `@SpringBootTest(RANDOM_PORT)` |
| `ApiTestsWithRestAssuredTest` | Rest-Assured + WireMock | `@SpringBootTest(RANDOM_PORT)` + `@WireMockTest(8090)` |

**WireMock stubs** live in `src/test/resources/mappings/`. WireMock mocks the PA service (port 8081) during tests.

**SQL test data** is organized in `src/test/resources/sql/`:
- `init/` — schema and seed data
- `cleanup/` — cleanup scripts run before each test
- `cases/` — test-specific data setup

**JSONAssert with regex matchers** is used for flexible response validation where fields like timestamps vary.

**Mock annotations**: Use `@MockitoBean` / `@MockitoSpyBean` from `org.springframework.test.context.bean.override.mockito` (Spring Boot 3.4+). The old `@MockBean` / `@SpyBean` from `org.springframework.boot.test.mock.mockito` are deprecated and removed. Note: `@MockitoSpyBean` requires the bean to already exist in the context — use `@Import` in slice tests (`@WebMvcTest`) to register the real bean first.

## Key Configuration Properties

EA service `application.properties` configures:
- MySQL datasource (`spring.datasource.*`)
- HTTP client timeouts: `client.internal.*` and `client.external.*` (connect/read/connection-request timeouts)
- PA service base URL: `pa-service.url`

## Key Dependencies

Managed outside the Spring Boot BOM (declared in root `pom.xml` `dependencyManagement`):

| Dependency | Version | Reason |
|---|---|---|
| `org.wiremock:wiremock-jetty12` | 3.13.0 | Not in Spring Boot BOM; Jetty 12 variant required for Spring Boot 3.5.x compatibility |
| `io.rest-assured:rest-assured-bom` | 6.0.0 | Pins all rest-assured artifacts together |
| `org.mozilla:rhino` | 1.7.15 | Overrides vulnerable 1.7.7.2 pulled transitively by `rest-assured:json-schema-validator` |

All other versions (MySQL connector, httpclient5, H2, AssertJ, etc.) are managed by the Spring Boot BOM.
