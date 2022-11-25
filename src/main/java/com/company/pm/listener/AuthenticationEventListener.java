package com.company.pm.listener;

import com.company.pm.security.FullAccessRole;
import io.jmix.core.session.SessionData;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

@Component
public class AuthenticationEventListener {

    @Autowired
    private ObjectProvider<SessionData> sessionDataProvider;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        SessionData sessionData = sessionDataProvider.getIfAvailable();
        if (sessionData != null) {
            Authentication authentication = event.getAuthentication();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(FullAccessRole.CODE)) {
                    sessionData.setAttribute("isManager", true);
                    return;
                }
            }
            sessionData.setAttribute("isManager", false);
        }
    }

}