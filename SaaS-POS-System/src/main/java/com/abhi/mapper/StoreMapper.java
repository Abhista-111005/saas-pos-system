package com.abhi.mapper;

import com.abhi.model.Store;
import com.abhi.model.User;
import com.abhi.payload.dto.StoreDto;

public class StoreMapper {

    public static StoreDto toDto(Store store) {

            StoreDto storeDto = new StoreDto();
            storeDto.setId(store.getId());
            storeDto.setBrand(store.getBrand());
            storeDto.setDescription(store.getDescription());
            storeDto.setStoreAdmin(UserMapper.toDTO(store.getStoreAdmin()));
            storeDto.setStoreType(store.getStoreType());
            storeDto.setContact(store.getContact());
            storeDto.setCreatedAt(store.getCreatedAt());
            storeDto.setUpdatesAt(store.getUpdatesAt());
            storeDto.setStatus(store.getStatus());

            return storeDto;
    }

    public static Store toEntity(StoreDto storeDto , User storeAdmin) {

        Store store = new Store();
        store.setId(storeDto.getId());
        store.setBrand(storeDto.getBrand());
        store.setDescription(storeDto.getDescription());
        store.setStoreAdmin(storeAdmin);
        store.setStoreType(storeDto.getStoreType());
        store.setContact(storeDto.getContact());
        store.setCreatedAt(storeDto.getCreatedAt());
        store.setUpdatesAt(storeDto.getUpdatesAt());
        store.setStatus(storeDto.getStatus());

        return store;

    }

}
