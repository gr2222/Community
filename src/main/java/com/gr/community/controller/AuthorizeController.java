package com.gr.community.controller;

import com.gr.community.pojo.GithubPostPojo;
import com.gr.community.pojo.GithubUser;
import com.gr.community.util.GithubRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubRequest githubRequest;
    @Value("${Github.Client.id}")
    private String clientId;
    @Value("${Github.setClient.secret}")
    private String clientSecret;
    @Value("${Github.setRedirect.uri}")
    private String redirectUri;



    @GetMapping("/githubyanzheng")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        GithubPostPojo githubPostPojo = new GithubPostPojo();
        githubPostPojo.setClient_id(clientId);
        githubPostPojo.setClient_secret(clientSecret);
        githubPostPojo.setCode(code);
        githubPostPojo.setRedirect_uri(redirectUri);
        githubPostPojo.setState(state);
        String accessToken = githubRequest.getAccessToken(githubPostPojo);
        GithubUser githubUser = githubRequest.getMag(accessToken);
        System.out.println(githubUser);
        return "index";
    }

}
