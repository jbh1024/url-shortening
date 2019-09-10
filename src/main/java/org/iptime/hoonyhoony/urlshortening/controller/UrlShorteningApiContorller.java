package org.iptime.hoonyhoony.urlshortening.controller;

import org.iptime.hoonyhoony.urlshortening.common.ValidatorUtil;
import org.iptime.hoonyhoony.urlshortening.controller.dto.UrlInfo;
import org.iptime.hoonyhoony.urlshortening.service.UrlShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api")
public class UrlShorteningApiContorller {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @RequestMapping(value = "/generateShortening", method = RequestMethod.POST)
    public ResponseEntity<UrlInfo> generateShortening(@RequestBody String originUrl, HttpServletRequest request) {
        String shorteningUrl = request.getRequestURL().toString();
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setOriginUrl(originUrl);
        urlInfo.setShorteningUrl(shorteningUrl.replace(request.getRequestURI(), "/" + urlShorteningService.generateShorteningUrl(ValidatorUtil.checkUrl(originUrl))));
        return ResponseEntity.ok(urlInfo);
    }
}
