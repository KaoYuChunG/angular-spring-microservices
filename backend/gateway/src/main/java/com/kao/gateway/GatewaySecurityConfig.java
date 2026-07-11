package com.kao.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
        
        // 在安全驗證與授權決定之後加入自訂 Header 傳播過濾器
        http.addFilterAfter(new HeaderPropagationFilter(), org.springframework.security.web.access.intercept.AuthorizationFilter.class);

        return http.build();
    }

    public static class HeaderPropagationFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                Jwt jwt = jwtAuth.getToken();
                String userId = jwt.getSubject();
                
                // 從 Keycloak JWT 提取 realm_access.roles
                Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                List<String> roles = new ArrayList<>();
                if (realmAccess != null && realmAccess.get("roles") instanceof List<?>) {
                    roles = ((List<?>) realmAccess.get("roles")).stream()
                            .map(Object::toString)
                            .collect(Collectors.toList());
                }

                // 包裝 Request 以加入 Header
                HeaderRequestWrapper wrappedRequest = new HeaderRequestWrapper(request);
                wrappedRequest.addHeader("X-User-Id", userId);
                wrappedRequest.addHeader("X-User-Roles", String.join(",", roles));
                
                filterChain.doFilter(wrappedRequest, response);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }

    // 自訂 Request Wrapper 以支援動態新增 Header
    public static class HeaderRequestWrapper extends HttpServletRequestWrapper {
        private final Map<String, String> customHeaders = new HashMap<>();

        public HeaderRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        public void addHeader(String name, String value) {
            customHeaders.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            if (customHeaders.containsKey(name)) {
                return customHeaders.get(name);
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (customHeaders.containsKey(name)) {
                return Collections.enumeration(Collections.singletonList(customHeaders.get(name)));
            }
            return super.getHeaders(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Set<String> names = new LinkedHashSet<>();
            Enumeration<String> originalNames = super.getHeaderNames();
            if (originalNames != null) {
                while (originalNames.hasMoreElements()) {
                    names.add(originalNames.nextElement());
                }
            }
            names.addAll(customHeaders.keySet());
            return Collections.enumeration(names);
        }
    }
}
