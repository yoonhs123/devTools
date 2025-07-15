package yoon.hyeon.sang.translator.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import yoon.hyeon.sang.translator.service.TranslatorSvc;
import yoon.hyeon.sang.userObj.ApiResponse;
import yoon.hyeon.sang.util.ApiRequester;
import yoon.hyeon.sang.util.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class TranslatorSvcImpl implements TranslatorSvc {

    private static final Logger logger = LogManager.getLogger("externalApiCall");

    @Override
    public String translate(String targetText, String targetLang, HttpServletRequest request) {

        String url = PropertiesUtil.getProperties("translator.api.key");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "DeepL-Auth-Key 3eded9b9-a77d-4a58-ad41-1b3ac0c7e4a1:fx");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept", "application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("text", targetText);
        body.put("target_lang", targetLang);


        ApiRequester apiRequest = new ApiRequester(getClass(), request);
        ApiResponse response = apiRequest.callApi(url, HttpMethod.POST, headers, body);

        if (response.isSuccess()){
            int httpstatusCode = response.getStatusCode();
            String responseBody = response.getResponseBody();
        } else {

        }

        return "";
    }
}
