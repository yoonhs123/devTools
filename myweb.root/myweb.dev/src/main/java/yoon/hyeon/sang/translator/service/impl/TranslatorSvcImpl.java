package yoon.hyeon.sang.translator.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import yoon.hyeon.sang.exception.ApiException;
import yoon.hyeon.sang.translator.dto.Languages;
import yoon.hyeon.sang.translator.service.TranslatorSvc;
import yoon.hyeon.sang.userObj.ApiResponse;
import yoon.hyeon.sang.util.ApiRequester;
import yoon.hyeon.sang.util.PropertiesUtil;
import yoon.hyeon.sang.util.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TranslatorSvcImpl implements TranslatorSvc {

    private static final Logger logger = LogManager.getLogger(TranslatorSvcImpl.class);
    public static final String apiKey = "DeepL-Auth-Key " + PropertiesUtil.getProperties("translator.api.key");

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Languages> getLanguage(String destination, HttpServletRequest request) {

        String redisKey = "lang.".concat(destination);
        long timeoutMillis = 30 * 60 * 1000;
        if (redisUtil.hasValue(redisKey)) {
            return (List<Languages>) redisUtil.get(redisKey);
        }

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
                List<Languages> languages = mapper.readValue(response.getResponseBody(), new TypeReference<List<Languages>>() {});

                languages.sort(Comparator.comparingInt(lang -> {

                    if (destination.equals("source")){
                        switch (lang.getLanguage().toUpperCase()) {
                            case "KO": return 0;
                            case "EN": return 1;
                            case "ZH": return 2;
                            case "JA": return 3;
                            default: return 4;
                        }
                    }
                    else {
                        switch (lang.getLanguage().toUpperCase()) {
                            case "KO": return 0;
                            case "EN-US": return 1;
                            case "ZH": return 2;    //chinese simplified
                            case "ZH-HANT": return 3;   //chinese traditional
                            case "JA": return 4;
                            default: return 5;
                        }
                    }
                }));

                redisUtil.set(redisKey, languages, timeoutMillis);
                return languages;
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
