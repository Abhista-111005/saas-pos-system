package com.abhi.repository;

import com.abhi.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store , Long> {

    Store findByStoreAdminId(String Id);

}
