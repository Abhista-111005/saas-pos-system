package com.abhi.service;

import com.abhi.domain.StoreStatus;
import com.abhi.exceptions.UserException;
import com.abhi.model.Store;
import com.abhi.model.User;
import com.abhi.payload.dto.StoreDto;


import java.util.List;


public interface StoreService {

    StoreDto createStore(StoreDto storeDto, User user) ;
    StoreDto getStoreById(Long id) throws Exception;
    List<StoreDto> getAllStores();
    Store getStoreByAdmin() throws UserException;
    StoreDto updateStore(Long id, StoreDto storeDto) throws Exception;
    void deleteStore(Long id) throws UserException;
    StoreDto getStoreEmployees() throws UserException;

    StoreDto moderateStore(Long id, StoreStatus status) throws Exception;

}