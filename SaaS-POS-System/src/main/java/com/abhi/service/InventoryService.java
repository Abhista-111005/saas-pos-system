package com.abhi.service;

import com.abhi.payload.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    InventoryDto createInventory(InventoryDto inventoryDto) throws Exception;
    InventoryDto updateInventory(Long id,InventoryDto inventoryDto);
    void deleteInventory(Long id);
    InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId);
    InventoryDto getInventoryById(Long id);
    List<InventoryDto> getAllInventoryByBranchId(Long branchId);


}
