package com.gumo.demo.controller;

import com.gumo.demo.constants.RedisConstants;
import com.gumo.demo.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/v1/captcha")
@Slf4j
public class CaptchaController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 获取验证码
     * 图片验证码
     *
     * @param username 账号/邮箱
     * :.+   解决SpringMvc请求参数中的点后面的参数丢失
     */
    @GetMapping("/{username:.+}")
    public void getCaptcha(HttpServletResponse response, @PathVariable("username") String username) {
        try (OutputStream oStream = response.getOutputStream()) {
            response.setContentType("image/png");
            Object[] objects = CaptchaUtil.getGraphCaptcha();
            String captcha = (String) objects[0];
            // 将验证码存到redis
            redisTemplate.opsForValue().set(RedisConstants.BUILDING_USER_CAPTCHA + username,
                    captcha, 10, TimeUnit.MINUTES);
            BufferedImage image = (BufferedImage) objects[1];
            ImageIO.write(image, "png", oStream);
            oStream.flush();
        } catch (Exception e) {
            log.error("验证码获取失败");
        }
    }

    @PostMapping("/check")
    public Boolean checkCaptcha(@RequestBody Map<String, String> params) {
        String captcha = params.get("captcha");
        String username = params.get("username");
        String key = RedisConstants.BUILDING_USER_CAPTCHA + username;
        String value = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        if (Objects.nonNull(value)) {
            return value.equalsIgnoreCase(captcha);
        }
        return false;
    }
}
