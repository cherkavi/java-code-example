package com.cherkashyn.vitalii.example.restjdbcstatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.cherkashyn.vitalii.example.restjdbcstatus.domain.DeployStatus;
import com.cherkashyn.vitalii.example.restjdbcstatus.domain.Status;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestJdbcStatusApplicationTests {

	@LocalServerPort
	int portNumber;

	private String getUrl(){
		return String.format("http://localhost:%d/status", portNumber);
	}

	private static String MY_KEY = "personal_key";
    private static String MY_KEY2 = "personal_key2";

	@Test
	public void test_01createRecord() {
		// given
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DeployStatus> request = new HttpEntity<>(new DeployStatus(Status.NEW, MY_KEY), headers);
        RestTemplate restTemplate = new RestTemplateBuilder().build();

		// when
		DeployStatus response = restTemplate.postForObject(
				getUrl(),
				request,
				DeployStatus.class
		);

		// then
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getId());
		Assert.assertEquals(1, response.getId());
	}

    @Test
    public void test_02createSecondRecord() {
        // given
        HttpEntity<DeployStatus> request = new HttpEntity<>(new DeployStatus(Status.NEW, MY_KEY2));
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        // when
        DeployStatus response = restTemplate.postForObject(
                getUrl(),
                request,
                DeployStatus.class
        );

        // then
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getId());
        Assert.assertEquals(2, response.getId());
    }

    @Test
    public void test_03getAll() throws URISyntaxException {
        // given
        HttpEntity<DeployStatus> request = new HttpEntity<>(new DeployStatus(Status.NEW, MY_KEY2));
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        // when
	// restTemplate.exchange(body, headers, method ... 
        ResponseEntity<String> response = restTemplate.exchange(
                new RequestEntity<String>(HttpMethod.GET, new URI(getUrl() + "/search/findBy")),
                String.class);

        // then
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(2, JSONPath.eval(JSON.parseObject(response.getBody()),"/page/totalElements"));
    }

    @Test
    public void test_04getById() throws URISyntaxException {
        // given
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        // when
        DeployStatus result = restTemplate.getForObject(getUrl() + "/1", DeployStatus.class);

        // then
        Assert.assertNotNull(result);
        Assert.assertEquals(MY_KEY, result.getKey());
        Assert.assertEquals(Status.NEW, result.getStatus());
    }

    @Test
    public void test_06update() throws URISyntaxException {
        // given
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        DeployStatus existingObject = restTemplate.getForObject(getUrl() + "/1", DeployStatus.class);
        existingObject.setStatus(Status.DEPLOYING);

        // when
        restTemplate.put(getUrl() + "/1", existingObject);
        DeployStatus result = restTemplate.getForObject(getUrl() + "/1", DeployStatus.class);

        // then
        Assert.assertNotNull(result);
        Assert.assertEquals(MY_KEY, result.getKey());
        Assert.assertEquals(Status.DEPLOYING, result.getStatus());
    }

    @Test
    public void test_07getByKey() throws URISyntaxException {
        // given
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        // when
        DeployStatus result = restTemplate.getForObject(getUrl() + "/search/findByKey?key="+MY_KEY, DeployStatus.class);

        // then
        Assert.assertNotNull(result);
        Assert.assertEquals(MY_KEY, result.getKey());
        Assert.assertEquals(Status.DEPLOYING, result.getStatus());
    }

//            RestTemplate restTemplate = new RestTemplateBuilder().build();
//            restTemplate.setErrorHandler(new SkipNotFound());
    private static class SkipNotFound implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
        }
        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            if (clientHttpResponse.getStatusCode().is4xxClientError()){
                return;
            }
            throw new RestClientException(IOUtils.toString(clientHttpResponse.getBody(), "utf-8"));
        }
    }

}


