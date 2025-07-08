package yoon.hyeon.sang.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import yoon.hyeon.sang.exception.ApiException;

import java.util.Map;

/// API 요청 템플릿
///TODO: 향후 Cookie, User-Agent, Referer 등 인자가 추가될때 추가개발 예정
public class ApiRequestUtil {

    private final Logger logger;

    public ApiRequestUtil(Class<?> className) {
        this.logger = LogManager.getLogger(className);
    }

    public static <T> ResponseEntity<String> callApi(
            String url,
            HttpMethod method,
            Map<String, String> headersMap,
            Map<String, ?> bodyMap
    ) {
        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(headersMap);

        // 헤더의 Content-Type 추출
        String contentTypeStr = headersMap.get("Content-Type");
        MediaType contentType = null;
        if (contentTypeStr != null) {
            try {
                contentType = MediaType.parseMediaType(contentTypeStr);
            } catch (Exception e) {
                throw new ApiException.InvalidContentType(e);
            }
        } else {
            contentType = MediaType.APPLICATION_JSON;   // Content-Type이 지정되지않았을 경우 application/json 으로 기본값 설정
        }

        headers.setContentType(contentType);

        HttpEntity<?> requestEntity;

        if (MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)) {    //Content-Type = application/x-www-form-urlencoded 인 경우
            MultiValueMap<String, String> formBody = new LinkedMultiValueMap<>();
            for (Map.Entry<String, ?> entry : bodyMap.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue().toString());
            }
            requestEntity = new HttpEntity<>(formBody, headers);
        } else {
            requestEntity = new HttpEntity<>(bodyMap, headers); // 그외의 Content-Type (application/json, application/xml 등..)
        }

        return restTemplate.exchange(url, method, requestEntity, String.class);
    }
}
