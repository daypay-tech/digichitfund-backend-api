package com.daypaytechnologies.digichitfund.infrastructure.utils;

import com.daypaytechnologies.digichitfund.app.user.constants.RoleConstants;
import com.daypaytechnologies.digichitfund.app.user.domain.administrationuser.AdministrationUser;
import com.daypaytechnologies.digichitfund.app.user.domain.role.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleCheckerUtils {

    public boolean isSuperAdmin(final AdministrationUser administrationUser) {
        for(Role role: administrationUser.getAccount().getRoles()) {
            if(role.getCode().equals(RoleConstants.ROLE_SUPER_ADMIN)) {
                return true;
            }
        }
        return false;
    }
}
