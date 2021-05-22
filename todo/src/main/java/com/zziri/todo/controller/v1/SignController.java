package com.zziri.todo.controller.v1;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.SocialProfile;
import com.zziri.todo.service.SocialService;
import com.zziri.todo.service.SocialServiceFactory;
import com.zziri.todo.service.security.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final SocialServiceFactory socialServiceFactory;

    @PostMapping(value = "/signin")
    public Response<String> signin(@RequestParam String account, @RequestParam String password) {
        return signService.signin(account, password);
    }

    @PostMapping("/signup")
    public Response<String> signup(@RequestParam String account, @RequestParam String password, @RequestParam String name) {
        return signService.signup(account, password, name);
    }

    @PostMapping(value = "/signin/{provider}")
    public Response<String> signinByProvider(@PathVariable String provider, @RequestParam String accessToken) {
        SocialService service = socialServiceFactory.getService(provider);
        SocialProfile profile = service.getProfile(accessToken);
        return signService.signinByProvider(provider, profile);
    }

    @PostMapping(value = "/signup/{provider}")
    public Response<String> signupByProvider(@PathVariable String provider, @RequestParam String accessToken, @RequestParam String name) {
        SocialService service = socialServiceFactory.getService(provider);
        SocialProfile profile = service.getProfile(accessToken);
        return signService.signupProvider(provider, name, profile);
    }
}
