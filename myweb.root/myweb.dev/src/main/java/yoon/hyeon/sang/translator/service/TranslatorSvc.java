package yoon.hyeon.sang.translator.service;

import yoon.hyeon.sang.translator.dto.Languages;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TranslatorSvc {

    public List<Languages> getLanguage(String destination, HttpServletRequest request);
    public String translate(String text, String targetLang, HttpServletRequest request);
}
