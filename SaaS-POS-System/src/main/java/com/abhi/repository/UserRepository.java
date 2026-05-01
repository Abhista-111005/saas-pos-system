package com.abhi.repository;

import com.abhi.model.Store;
import com.abhi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByStore(Store store);
    List<User> findByBranchId(Long branchId);
}
