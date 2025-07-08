package yoon.hyeon.sang.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class errorPageCon {

    @RequestMapping(value = "/errorPopup", method = RequestMethod.GET)
    public ModelAndView errorPopup(@RequestParam String msg,
                                   @RequestParam String url,
                                   @RequestParam String trace) {

        List<String> lines = Arrays.stream(trace.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        String returnURL = "common/error/commonErrorPop";
        ModelAndView mv = new ModelAndView();
        mv.setViewName(returnURL);
        mv.addObject("errorMessage", msg);
        mv.addObject("url", url);
        mv.addObject("stackTrace", lines);
        return  mv;
    }

}
