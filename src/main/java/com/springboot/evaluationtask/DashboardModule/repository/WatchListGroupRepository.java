package com.springboot.evaluationtask.DashboardModule.repository;

import com.springboot.evaluationtask.DashboardModule.enity.WatchListGroup;
import com.springboot.evaluationtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchListGroupRepository extends JpaRepository<WatchListGroup,Long> {

}
