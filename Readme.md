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

# Rest api test
- Rest-assured

# SpringBoot-Test + Mockito Mock
- @SpringBootTest(classes = {*****})
- @Autowired
- @MockBean
- @SpyBean

# Mockito Mock
- @InjectMocks
- @Mock
- @Spy

# Core classes of Junit5
- org.junit.jupiter.api.Test@
- org.junit.jupiter.api.BeforeAll@
- org.junit.jupiter.api.BeforeEach@
- org.junit.jupiter.api.AfterAll@
- org.junit.jupiter.api.AfterEach@
- org.junit.jupiter.api.TestInstance@
- org.junit.jupiter.api.DisplayName@
- org.junit.jupiter.params.ParameterizedTest@
- org.junit.jupiter.params.provider.CsvSource@
- org.junit.jupiter.params.provider.ValueSource@
- org.junit.jupiter.api.extension.ExtendWith@
- org.junit.jupiter.api.extension.Extension
- org.mockito.junit.jupiter.MockitoExtension
- org.springframework.test.context.junit.jupiter.SpringExtension
- com.github.tomakehurst.wiremock.junit5.WireMockExtension
- org.junit.jupiter.api.Assertions;


# Core classes of Junit4
- org.junit.Test@
- org.junit.Before@
- org.junit.Ignore@
- org.junit.runner.RunWith@
- org.junit.runner.Runner
- org.mockito.junit.MockitoJUnitRunner
- org.springframework.test.context.junit4.SpringJUnit4ClassRunner
- org.junit.Assert

# Core classes of Springboot test
- org.springframework.boot.test.context.SpringBootTest@[Include @ExtendWith]
- org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest@[Include @ExtendWith]
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

# Core classes of JSONAssert
- org.skyscreamer.jsonassert.JSONAssert
- org.skyscreamer.jsonassert.JSONCompareMode
- org.skyscreamer.jsonassert.JSONCompare
- org.skyscreamer.jsonassert.JSONCompareResult
- org.skyscreamer.jsonassert.comparator.JSONComparator[interface]
- org.skyscreamer.jsonassert.comparator.AbstractComparator
- org.skyscreamer.jsonassert.comparator.CustomComparator
- org.skyscreamer.jsonassert.Customization
- org.skyscreamer.jsonassert.comparator.DefaultComparator
- org.skyscreamer.jsonassert.comparator.ArraySizeComparator
- org.skyscreamer.jsonassert.ValueMatcher<T>[interface]
- org.skyscreamer.jsonassert.RegularExpressionValueMatcher<T>

# Core classes of WireMock
- com.github.tomakehurst.wiremock.junit5.WireMockExtension;
- com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
- com.github.tomakehurst.wiremock.junit5.WireMockTest[Include @ExtendWith]
- com.github.tomakehurst.wiremock.client.WireMock;
- com.github.tomakehurst.wiremock.matching.ValueMatcher
- com.github.tomakehurst.wiremock.matching.NamedValueMatcher
- com.github.tomakehurst.wiremock.stubbing.StubMapping
- com.github.tomakehurst.wiremock.verification.RequestJournal
- com.github.tomakehurst.wiremock.WireMockServer

# Core classes of RestTemplate
- org.springframework.web.client.RestTemplate;
- org.springframework.http.client.ClientHttpResponse[interface]
- org.springframework.http.client.HttpComponentsClientHttpResponse
- org.springframework.http.client.OkHttp3ClientHttpResponse
- org.springframework.http.client.ClientHttpRequestFactory[interface]
- org.springframework.http.client.HttpComponentsClientHttpRequestFactory
- org.springframework.http.client.OkHttp3ClientHttpRequestFactory
- org.springframework.http.HttpStatus
- org.springframework.http.RequestEntity
- org.springframework.http.ResponseEntity
- org.springframework.http.converter.HttpMessageConverter[interface]
- org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
- org.springframework.http.converter.json.GsonHttpMessageConverter
- org.springframework.web.client.ResponseErrorHandler[interface]
- org.springframework.web.client.DefaultResponseErrorHandler
- org.springframework.web.client.ExtractingResponseErrorHandler
- org.springframework.boot.test.web.client.TestRestTemplate.NoOpResponseErrorHandler
- org.springframework.web.client.RestClientException
- org.springframework.web.client.RestClientResponseException
- org.springframework.web.client.HttpStatusCodeException
- org.springframework.web.client.HttpClientErrorException
- org.springframework.web.client.HttpClientErrorException$BadRequest
- org.springframework.web.client.HttpServerErrorException
- org.springframework.web.client.HttpServerErrorException$InternalServerError
- org.springframework.web.client.UnknownHttpStatusCodeException
- org.springframework.web.client.ResponseExtractor<T>[interface]
- org.springframework.web.client.RestTemplate$HeadersExtractor[HttpHeaders]
- org.springframework.web.client.RestTemplate$ResponseEntityResponseExtractor<T>
- org.springframework.web.client.HttpMessageConverterExtractor<T>[ResponseEntity<T>]
- org.springframework.web.client.RequestCallback[interface]
- org.springframework.web.util.UriTemplateHandler[interface]
- org.springframework.web.util.WebUtils
- org.springframework.util.Assert;
- org.springframework.util.ClassUtils;

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

# Static vs. instance of Wiremock
- with the declarative form, each WireMock server will be started before the first test method in the test class and stopped after the last test method has completed, with a call to reset before each test method.
- However, if the extension fields are declared at the instance scope (without the static modifier) each WireMock server will be created and started before each test method and stopped after the end of the test method.

# how to log request and response in ClientHttpRequestInterceptor
1. Using BufferingClientHttpRequestFactory
2. Using BufferingClientHttpResponseWrapper

# Java Contract Programming
- org.springframework.util.Assert
- com.google.common.base.Preconditions
- java.util.Objects.require***
