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
- @WebMvcTest, 该注解仅会创建指定的Controller　bean，该Controller依赖的其他bean需要使用＠ＭockＢean注入


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
