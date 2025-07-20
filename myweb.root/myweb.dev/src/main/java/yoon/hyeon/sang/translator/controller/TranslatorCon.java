package yoon.hyeon.sang.translator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import yoon.hyeon.sang.translator.dto.Languages;
import yoon.hyeon.sang.translator.service.TranslatorSvc;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class TranslatorCon {

    @Autowired
    private TranslatorSvc translatorSvc;

    private static final Logger logger = LogManager.getLogger(TranslatorCon.class);

    @RequestMapping(value = "/translator", method = RequestMethod.GET)
    public ModelAndView goPage(HttpServletRequest request) throws IOException {
        String returnURL = "translator/translator";
        ModelAndView mv = new ModelAndView();

        List<Languages> sourceLang = translatorSvc.getLanguage("source", request);
        List<Languages> targetLang = translatorSvc.getLanguage("target", request);

        ObjectMapper mapper = new ObjectMapper();
        String targetLangString = mapper.writeValueAsString(targetLang);

        mv.addObject("sourceLang", sourceLang);
        mv.addObject("targetLang", targetLang);
        mv.addObject("targetLangStr", targetLangString);
        mv.setViewName(returnURL);
        return  mv;
    }

    @RequestMapping(value = "/translate", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> translate(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {

        //String a = requestBody.get("inputString");
        String b = requestBody.get("targetLang").toString();



        //String test = translatorSvc.translate("안녕", "en", request);

        return null;
    }
}
