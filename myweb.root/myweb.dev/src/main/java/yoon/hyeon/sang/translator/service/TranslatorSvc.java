package yoon.hyeon.sang.translator.service;

import javax.servlet.http.HttpServletRequest;

public interface TranslatorSvc {

    public String translate(String text, String targetLang, HttpServletRequest request);
}
