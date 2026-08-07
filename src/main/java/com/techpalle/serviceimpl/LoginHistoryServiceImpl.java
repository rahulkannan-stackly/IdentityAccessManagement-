package com.techpalle.serviceimpl;

import com.techpalle.entity.LoginHistory;
import com.techpalle.entity.User;
import com.techpalle.repository.LoginHistoryRepository;
import com.techpalle.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    @Override
    public void recordLogin( User user, String ipAddress,String userAgent)
    {
        log.info( "Recording login history for user: {}", user.getUsername() );

        LoginHistory loginHistory = LoginHistory.builder() .user(user)
                .loginTimestamp(LocalDateTime.now()).ipAddress(ipAddress)
                .userAgent(userAgent).loginStatus("SUCCESS").build();

        loginHistoryRepository.save(loginHistory);

        log.info( "Login history recorded successfully for user: {}", user.getUsername());
    }

    @Override
    public void recordLogout(User user) {

        log.info("Recording logout history for user: {}",user.getUsername() );

        List<LoginHistory> loginHistories =loginHistoryRepository.findByUser(user);

        if (!loginHistories.isEmpty()) {

            LoginHistory latestLogin = loginHistories.get(loginHistories.size() - 1);

            latestLogin.setLogoutTimestamp(LocalDateTime.now());

            loginHistoryRepository.save(latestLogin);

            log.info("Logout history recorded successfully for user: {}",user.getUsername());
        }
    }

    @Override
    public List<LoginHistory> getLoginHistory(User user) {

        log.info( "Fetching login history for user: {}", user.getUsername());

        return loginHistoryRepository.findByUser(user);
    }
}
