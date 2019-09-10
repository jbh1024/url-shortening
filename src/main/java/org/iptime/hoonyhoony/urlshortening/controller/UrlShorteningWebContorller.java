package org.iptime.hoonyhoony.urlshortening.controller;

import org.iptime.hoonyhoony.urlshortening.controller.dto.UrlInfo;
import org.iptime.hoonyhoony.urlshortening.service.UrlShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UrlShorteningWebContorller {

    @Autowired
    private UrlShorteningService urlShorteningService;

    //shortening url 입력시 redirect
    @RequestMapping(value = "/{alias}", method = RequestMethod.GET)
    public String redirect(@PathVariable String alias) {
        String redirectUrl = urlShorteningService.getOriginUrlByAlias(alias);  // 리다이렉트용 url
        //존재하지 않는 alias로 요청 할 경우 error page return. 추가 개선&처리 필요
        if (StringUtils.isEmpty(redirectUrl)) {
            return "error";
        } else {
            return "redirect:" + redirectUrl;
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(UrlInfo urlInfo) {
        return "index";
    }

    //전체 조회
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("urlInfos", urlShorteningService.getAllUrlShortening());
        return "urlInfos";
    }
}
