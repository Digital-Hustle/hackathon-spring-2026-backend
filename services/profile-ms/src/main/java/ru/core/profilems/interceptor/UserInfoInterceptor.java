package ru.core.profilems.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.core.profilems.constants.ContextKeys;
import ru.core.profilems.constants.CustomHeaders;
import ru.core.profilems.dto.ThreadLocalMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        ThreadLocalMap.put(ContextKeys.USER_ID, request.getHeader(CustomHeaders.USER_ID));
        ThreadLocalMap.put(ContextKeys.USERNAME, request.getHeader(CustomHeaders.USERNAME));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        ThreadLocalMap.clear();
    }
}
