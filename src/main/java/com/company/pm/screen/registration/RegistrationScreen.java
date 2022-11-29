package com.company.pm.screen.registration;

import com.company.pm.entity.User;
import com.company.pm.screen.login.LoginScreen;
import com.company.pm.services.UserService;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("RegistrationScreen")
@UiDescriptor("registration-screen.xml")
@Route(value = "register", root = true)
public class RegistrationScreen extends Screen {
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private UserService userService;
    @Autowired
    private TextField<String> usernameField;
    @Autowired
    private PasswordField passwordField;
    @Autowired
    private Notifications notifications;

    @Subscribe("loginBnt")
    public void onLoginBntClick(Button.ClickEvent event) {
        screenBuilders.screen(this)
                .withScreenClass(LoginScreen.class)
                .withOpenMode(OpenMode.ROOT)
                .show();
    }

    @Subscribe("registerButton")
    public void onRegisterButtonClick(Button.ClickEvent event) {
        User register = userService.register(usernameField.getRawValue(), passwordField.getValue());
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("User %s was registered".formatted(register.getDisplayName()))
                .show();
        usernameField.clear();
        passwordField.clear();
    }
}