# Bean Mock
- @MockBean
- @SpyBean

# 集成测试
## 启用
- 集成测试使用@SpringBootTest注解来加载所有的bean，　使用＠ＭockＢean或＠ＳpyＢean来替换指定的Ｂean，使用Mock或Running Server并配合相应的工具类完成测试

## Test Running Web server by WebTestClient or TestRestTemplate
- @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
- @Autowired WebTestClient webClient(This setup requires spring-webflux on the classpath)
- @Autowired TestRestTemplate restTemplate

## Test Mock Web server by MockMvc
- @SpringBootTest
- @AutoConfigureMockMvc
- @Autowired MockMvc


# 切片测试
## WebMvc切面测试
- @WebMvcTest, 该注解仅会创建指定的Ｃontroller　bean，该Ｃontroller依赖的其他bean需要使用＠ＭockＢean注入


# Assert Framework
- Junit自带的Assert
- Hamcrest
- Assertj
- JSONAssert(JSONPath)

# Core classes of Junit5
- org.junit.jupiter.api.Test@
- org.junit.jupiter.api.Assertions;
- org.junit.jupiter.api.BeforeAll@
- org.junit.jupiter.api.BeforeEach@
- org.junit.jupiter.api.AfterAll@
- org.junit.jupiter.api.AfterEach@
- org.junit.jupiter.api.TestInstance@
- org.junit.jupiter.api.DisplayName@
- org.junit.jupiter.api.extension.ExtendWith@
- org.junit.jupiter.params.ParameterizedTest@
- org.junit.jupiter.params.provider.CsvSource@
- org.junit.jupiter.params.provider.ValueSource@

# Core classes of Junit4
- org.junit.Test@
- org.junit.Before@
- org.junit.Ignore@
- org.junit.Assert
- org.junit.runner.RunWith@

# Core classes of Springboot test
- org.springframework.boot.test.context.SpringBootTest@
- org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest@
- org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc@
- org.springframework.boot.test.web.client.TestRestTemplate
- org.springframework.boot.test.mock.mockito.MockBean@
- org.springframework.boot.test.mock.mockito.SpyBean@
- org.springframework.test.context.ActiveProfiles@
- org.springframework.test.context.TestPropertySource@
- org.springframework.test.web.servlet.MockMvc
- org.springframework.test.web.servlet.MvcResult
- org.springframework.test.web.servlet.RequestBuilder
- org.springframework.test.web.servlet.request.MockMvcRequestBuilders
- org.springframework.test.web.servlet.result.MockMvcResultMatchers
- org.springframework.test.util.ReflectionTestUtils

# Core classes of assertj
- org.assertj.core.api.Assertions

# Core classes of mockito
- org.mockito.Mockito
- org.mockito.BDDMockito
- org.mockito.Mock@
- org.mockito.Spy@
- org.mockito.InjectMocks@
- org.mockito.ArgumentCaptor
- org.mockito.ArgumentMatchers
- org.mockito.MockedStatic
- org.mockito.quality.Strictness
- org.mockito.junit.MockitoJUnitRunner
- org.mockito.junit.jupiter.MockitoExtension
- org.mockito.junit.jupiter.MockitoSettings

# Core classes of Openfeign
- org.springframework.cloud.openfeign.EnableFeignClients;
- org.springframework.cloud.openfeign.FeignClient;

# Core classes of Resilience4j
- io.github.resilience4j.bulkhead.annotation.Bulkhead@
- io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker@
- io.github.resilience4j.ratelimiter.annotation.RateLimiter@
- io.github.resilience4j.retry.annotation.Retry@
- io.github.resilience4j.timelimiter.annotation.TimeLimiter@
- io.github.resilience4j.circuitbreaker.CircuitBreaker
- io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
- io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
- io.github.resilience4j.ratelimiter.RateLimiter
- io.github.resilience4j.ratelimiter.RateLimiterConfig
- io.github.resilience4j.ratelimiter.RateLimiterRegistry
- io.github.resilience4j.retry.Retry
- io.github.resilience4j.retry.RetryConfig
- io.github.resilience4j.retry.RetryRegistry
- io.github.resilience4j.timelimiter.TimeLimiter
- io.github.resilience4j.timelimiter.TimeLimiterConfig
- io.github.resilience4j.timelimiter.TimeLimiterRegistry
- io.github.resilience4j.bulkhead.autoconfigure.BulkheadAutoConfiguration
- io.github.resilience4j.bulkhead.autoconfigure.BulkheadMetricsAutoConfiguration
- io.github.resilience4j.bulkhead.autoconfigure.ThreadPoolBulkheadMetricsAutoConfiguration
- io.github.resilience4j.retry.autoconfigure.RetryAutoConfiguration
- io.github.resilience4j.retry.autoconfigure.RetryMetricsAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerStreamEventsAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerMetricsAutoConfiguration
- io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakersHealthIndicatorAutoConfiguration
- io.github.resilience4j.ratelimiter.autoconfigure.RateLimiterAutoConfiguration
- io.github.resilience4j.ratelimiter.autoconfigure.RateLimiterMetricsAutoConfiguration
- io.github.resilience4j.ratelimiter.autoconfigure.RateLimitersHealthIndicatorAutoConfiguration
- io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterAutoConfiguration
- io.github.resilience4j.timelimiter.autoconfigure.TimeLimiterMetricsAutoConfiguration
- io.github.resilience4j.scheduled.threadpool.autoconfigure.ContextAwareScheduledThreadPoolAutoConfiguration

# Openfeign Configuration
- /org/springframework/cloud/spring-cloud-openfeign-core/3.1.7/spring-cloud-openfeign-core-3.1.7.jar!/META-INF/additional-spring-configuration-metadata.json

# Resilience4j Configuration
- /io/github/resilience4j/resilience4j-spring-boot2/1.7.0/resilience4j-spring-boot2-1.7.0.jar!/META-INF/spring-configuration-metadata.json