package yoon.hyeon.sang.translator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import yoon.hyeon.sang.exception.ApiException;
import yoon.hyeon.sang.translator.dto.Languages;
import yoon.hyeon.sang.translator.service.TranslatorSvc;
import yoon.hyeon.sang.userObj.ApiResponse;
import yoon.hyeon.sang.util.ApiRequester;
import yoon.hyeon.sang.util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranslatorSvcImpl implements TranslatorSvc {

    private static final Logger logger = LogManager.getLogger(TranslatorSvcImpl.class);
    public static final String apiKey = "DeepL-Auth-Key " + PropertiesUtil.getProperties("translator.api.key");

    @Override
    public List<Languages> getLanguage(String destination, HttpServletRequest request) {
        String url = "https://api-free.deepl.com/v2/languages";

        Map<String, String> params = new HashMap<>();
        params.put("type", destination);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", apiKey);
        headers.put("Accept", "application/json");

        ApiRequester apiRequester = new ApiRequester(getClass(), request);

        try {
            ApiResponse response = apiRequester.callApi(apiRequester.appendQueryParams(url, params), HttpMethod.GET, headers);
            if (response.isSuccess()) {
                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(response.getResponseBody(), new TypeReference<List<Languages>>() {});
            } else {
                return Collections.emptyList();
            }
        } catch(JsonProcessingException ex1) {
            logger.error("Fail to deserialize ApiResponse", ex1);
        }

        return Collections.emptyList();
    }

    @Override
    public String translate(String targetText, String targetLang, HttpServletRequest request) {
        String url = "https://api-free.deepl.com/v2/translate";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", apiKey);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept", "application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("text", targetText);
        body.put("target_lang", targetLang);

        ApiRequester apiRequester = new ApiRequester(getClass(), request);
        ApiResponse response = apiRequester.callApi(url, HttpMethod.POST, headers, body);

        if (response.isSuccess()){
            int httpstatusCode = response.getStatusCode();
            String responseBody = response.getResponseBody();
        } else {

        }

        return "";
    }
}
