package com.zootopia.userservice.aop;

import com.zootopia.userservice.controller.FriendController;
import com.zootopia.userservice.controller.PointController;
import com.zootopia.userservice.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
@RequiredArgsConstructor
public class JWTAspect {

    private final JwtProvider jwtProvider;

    @Before("execution(* com.zootopia.userservice.controller.FriendController.*(..)) || " +
            "execution(* com.zootopia.userservice.controller.PointController.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.substring(7);

        String memberID = jwtProvider.getMemberIDFromToken(accessToken);

        if (joinPoint.getTarget() instanceof FriendController) {
            FriendController controller = (FriendController) joinPoint.getTarget();
            controller.setMemberID(memberID);
        } else {
            PointController controller = (PointController) joinPoint.getTarget();
            controller.setMemberID(memberID);
        }
    }
}
