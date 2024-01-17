package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {


}
