package com.company.pm.screen.userinfo;

import io.jmix.ui.screen.*;
import com.company.pm.entity.UserInfo;

@UiController("UserInfo.browse")
@UiDescriptor("user-info-browse.xml")
@LookupComponent("userInfoesTable")
public class UserInfoBrowse extends StandardLookup<UserInfo> {
}