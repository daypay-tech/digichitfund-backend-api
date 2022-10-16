package com.daypaytechnologies.digichitfund.app.auth.services;

import com.daypaytechnologies.digichitfund.app.auth.data.AuthData;
import com.daypaytechnologies.digichitfund.app.auth.dto.UserAccountAuthTokenDTO;
import com.daypaytechnologies.digichitfund.app.auth.request.UserLoginRequest;
import com.daypaytechnologies.digichitfund.app.user.data.AdministrationUserAuthResponseData;
import com.daypaytechnologies.digichitfund.app.user.data.JwtResponse;
import com.daypaytechnologies.digichitfund.app.user.data.RoleData;
import com.daypaytechnologies.digichitfund.app.user.data.UserData;
import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccount;
import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccountRepository;
import com.daypaytechnologies.digichitfund.app.user.domain.account.UserAccountRepositoryWrapper;
import com.daypaytechnologies.digichitfund.app.user.domain.role.Role;
import com.daypaytechnologies.digichitfund.security.JwtTokenHandler;
import com.daypaytechnologies.digichitfund.security.UserAccountDetailsImpl;
import com.daypaytechnologies.digichitfund.security.authtoken.UserAccountUsernameAndPasswordAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenHandler jwtTokenHandler;

    private final AuthenticationManager authenticationManager;

    private final UserAccountRepositoryWrapper userAccountRepositoryWrapper;

    @Override
    public AuthData doLogin(UserLoginRequest loginRequest) {
        final UserAccountAuthTokenDTO userAccountAuthTokenDTO = new UserAccountAuthTokenDTO();
        userAccountAuthTokenDTO.setUsername(loginRequest.getEmail().trim());
        Authentication authentication = authenticationManager.authenticate(
                new UserAccountUsernameAndPasswordAuthToken(userAccountAuthTokenDTO,
                        loginRequest.getPassword().trim()));
        UserAccountDetailsImpl userAccountDetails = (UserAccountDetailsImpl) authentication.getPrincipal();
        final UserAccount userAccount = userAccountRepositoryWrapper.findOneWithNotFoundDetection(userAccountDetails.getId());
        String jwtToken = jwtTokenHandler.generateJwtToken(userAccount.getEmail(), userAccount.getId(), 1L);
        JwtResponse jwtResponse =  new JwtResponse(jwtToken);
        List<RoleData> roles = new ArrayList<>();
        for(Role role: userAccount.getRoles()) {
            roles.add(role.toData());
        }
        final UserData userData = UserData.newInstance(userAccount.getId(), "", "", userAccount.getEmail(), userAccount.getMobile());
        return AuthData.newInstance(userData, jwtResponse);
    }
}
