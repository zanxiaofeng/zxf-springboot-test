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

