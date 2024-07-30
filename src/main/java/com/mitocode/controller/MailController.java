package com.mitocode.controller;

import com.mitocode.model.ResetMail;
import com.mitocode.model.User;
import com.mitocode.service.ILoginService;
import com.mitocode.service.IResetMailService;
import com.mitocode.util.EmailUtil;
import com.mitocode.util.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final ILoginService loginService;
    private final IResetMailService resetMailService;
    private final EmailUtil emailUtil;


    @PostMapping("/sendMail")
    public ResponseEntity<Integer> sendMail(@RequestBody String username) throws Exception {
        int rpta = 0;
        final int EXPIRATION_TIME = 10;

        User us = loginService.checkUsername(username);
        if(us != null && us.getIdUser() > 0){

            ResetMail resetMail = new ResetMail();
            resetMail.setRandom(UUID.randomUUID().toString());
            resetMail.setUser(us);
            resetMail.setExpiration(EXPIRATION_TIME);
            resetMailService.save(resetMail);

            Mail mail = new Mail();
            mail.setFrom("email.prueba.demo@gmail.com");
            mail.setTo(us.getUsername());
            mail.setSubject("RESET PASSWORD MEDIAPP");

            Map<String, Object> model = new HashMap<>();
            String url = "http://localhost:4200/#/forgot/" + resetMail.getRandom();
            model.put("user", resetMail.getUser().getUsername());
            model.put("resetUrl", url);
            mail.setModel(model);

            emailUtil.sendMail(mail);

            rpta = 1;
        }

        return ResponseEntity.ok(rpta);
    }

    @GetMapping("/reset/check/{random}")
    public ResponseEntity<Integer> checkRandom(@PathVariable("random") String random){
        int rpta = 0;

        ResetMail rm = resetMailService.findByRandom(random);
        if(rm != null && rm.getId() > 0){
            if(!rm.isExpired()){
                rpta = 1;
            }
        }

        return ResponseEntity.ok(rpta);
    }

    @PostMapping("/reset/{random}")
    public ResponseEntity<Integer> resetPassword(@PathVariable("random") String random, @RequestBody String password){
        int rpta = 0;
        ResetMail rm = resetMailService.findByRandom(random);
        if(rm != null && rm.getId() > 0){
            if(!rm.isExpired()){
                loginService.changePassword(password, rm.getUser().getUsername());
                resetMailService.delete(rm);
                rpta = 1;
            }
        }

        return ResponseEntity.ok(rpta);
    }

}
