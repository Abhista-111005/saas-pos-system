package com.abhi.controller;

import com.abhi.domain.StoreStatus;
import com.abhi.exceptions.UserException;
import com.abhi.mapper.StoreMapper;
import com.abhi.model.Store;
import com.abhi.model.User;
import com.abhi.payload.dto.StoreDto;
import com.abhi.payload.response.ApiResponse;
import com.abhi.service.StoreService;
import com.abhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto,
                                                @RequestHeader("Authorization") String jwtToken) throws UserException {

        User user = userService.getUserFromJwtToken(jwtToken);
        return ResponseEntity.ok(storeService.createStore(storeDto, user));

    }



    @GetMapping()
    public ResponseEntity<List<StoreDto>> getAllStore(
            @RequestHeader("Authorization") String jwt) throws Exception {

        return ResponseEntity.ok(storeService.getAllStores());

    }


    @GetMapping("/admin")
    public ResponseEntity<StoreDto> getStoreByAdmin(
            @RequestHeader("Authorization") String jwtToken) throws Exception {

        return ResponseEntity.ok(StoreMapper.toDto(storeService.getStoreByAdmin()));

    }


    @GetMapping("/employee")
    public ResponseEntity<StoreDto> getStoreByEmployee(
            @RequestHeader("Authorization") String jwtToken) throws Exception {

        return ResponseEntity.ok(StoreMapper.toDto(storeService.getStoreByAdmin()));

    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> updateStore(@PathVariable Long id,
                                                @RequestBody StoreDto storeDto) throws Exception {

        return ResponseEntity.ok(storeService.updateStore(id, storeDto));

    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDto> moderateStore(@PathVariable Long id,
                                                  @RequestParam StoreStatus status) throws Exception {

        return ResponseEntity.ok(storeService.moderateStore(id, status));

    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> getStoreById(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String jwtToken) throws Exception {

        return ResponseEntity.ok(storeService.getStoreById(id));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws Exception {

        storeService.deleteStore(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Store deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

}
