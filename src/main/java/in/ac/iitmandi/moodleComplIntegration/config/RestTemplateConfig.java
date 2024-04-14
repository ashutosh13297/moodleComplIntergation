package in.ac.iitmandi.moodleComplIntegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplateForCompl")
    public RestTemplate prepareRestTemplateForProductService() {

//        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(productServiceUsername, productServicePassword));
//
//        RequestConfig.Builder requestBuilder = RequestConfig.custom();
//        requestBuilder = requestBuilder.setConnectTimeout(1000);
//
//        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
//        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//        httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());
//        CloseableHttpClient httpClient = httpClientBuilder.build();
//
//        HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);

//        return new RestTemplate(rf);
        return new RestTemplate();
    }
}
