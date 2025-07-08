package yoon.hyeon.sang.translator.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import yoon.hyeon.sang.translator.service.TranslatorSvc;

import java.io.IOException;

@Controller
public class TranslatorCon {

    @Autowired
    private TranslatorSvc translatorSvc;

    private static final Logger logger = LogManager.getLogger(TranslatorCon.class);

    @RequestMapping(value = "/translator", method = RequestMethod.GET)
    public ModelAndView goPage() throws IOException {

        String aaa = translatorSvc.translate("안녕", "en");
        String returnURL = "translator/translator";
        ModelAndView mv = new ModelAndView();
        mv.setViewName(returnURL);
        return  mv;
    }
}
