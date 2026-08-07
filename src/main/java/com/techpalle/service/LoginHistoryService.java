package com.techpalle.service;

import com.techpalle.entity.LoginHistory;
import com.techpalle.entity.User;

import java.util.List;

public interface LoginHistoryService {

    void recordLogin(User user,String ipAddress,String userAgent);

    void recordLogout(User user);

    List<LoginHistory> getLoginHistory(User user);
}