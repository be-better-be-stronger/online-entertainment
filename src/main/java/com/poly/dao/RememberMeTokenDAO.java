package com.poly.dao;

import com.poly.entity.RememberMeToken;

public interface RememberMeTokenDAO {
    void save(RememberMeToken token);
    RememberMeToken findByUserId(String userId);
    void deleteTokenByUserId(String userId);
}
  