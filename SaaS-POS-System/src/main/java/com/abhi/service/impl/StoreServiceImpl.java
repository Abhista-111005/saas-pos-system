package com.abhi.service.impl;

import com.abhi.domain.StoreStatus;
import com.abhi.exceptions.UserException;
import com.abhi.mapper.StoreMapper;
import com.abhi.model.Store;
import com.abhi.model.StoreContact;
import com.abhi.model.User;
import com.abhi.payload.dto.StoreDto;
import com.abhi.repository.StoreRepository;
import com.abhi.service.StoreService;
import com.abhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public StoreDto createStore(StoreDto storeDto, User user) {

        Store store = StoreMapper.toEntity(storeDto , user);
        return StoreMapper.toDto(storeRepository.save(store));
    }

    @Override
    public StoreDto getStoreById(Long id) throws Exception {

        Store store = storeRepository.findById(id).orElseThrow(
                () -> new Exception("Store not found with id: " )
        );
        return StoreMapper.toDto(store);
    }

    @Override
    public List<StoreDto> getAllStores() {
        List<Store> dtos = storeRepository.findAll();
        return dtos.stream().map(StoreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Store getStoreByAdmin() throws UserException {

        User admin = userService.getCurrentUser();

        return storeRepository.findByStoreAdminId(admin.getId());
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) throws Exception {

        User currentUser = userService.getCurrentUser();

        Store exsisting = storeRepository.findByStoreAdminId(currentUser.getId());

        if(exsisting == null) {
            throw new Exception("Store not found");
        }
        exsisting.setBrand(storeDto.getBrand());
        exsisting.setDescription(storeDto.getDescription());

        if(storeDto.getStoreType() != null) {
            exsisting.setStoreType(storeDto.getStoreType());
        }

        if(storeDto.getContact() != null) {
            StoreContact contact = StoreContact.builder()
                    .address(storeDto.getContact().getAddress())
                    .phone(storeDto.getContact().getPhone())
                    .email(storeDto.getContact().getEmail())
                    .build();
            exsisting.setContact(contact);
        }
        Store updatedStore = storeRepository.save(exsisting);
        return StoreMapper.toDto(updatedStore);
    }

    @Override
    public void deleteStore(Long id) throws UserException {

        Store store = getStoreByAdmin();
        storeRepository.delete(store);



    }

    @Override
    public StoreDto getStoreEmployees() throws UserException {

        User currentUser = userService.getCurrentUser();

        if(currentUser == null) {
            throw new UserException("Ypu dont have permission to access this store");
        }


        return StoreMapper.toDto(currentUser.getStore());
    }

    @Override
    public StoreDto moderateStore(Long id, StoreStatus status) throws Exception {

        Store store = storeRepository.findById(id).orElseThrow(
                () -> new Exception("Store not found....")
        );
        store.setStatus(status);
        Store updatedStore = storeRepository.save(store);
        return StoreMapper.toDto(updatedStore);


    }
}
