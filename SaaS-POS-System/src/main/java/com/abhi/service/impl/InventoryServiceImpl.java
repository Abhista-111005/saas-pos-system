package com.abhi.service.impl;

import com.abhi.mapper.InventoryMapper;
import com.abhi.model.Branch;
import com.abhi.model.Inventory;
import com.abhi.model.Product;
import com.abhi.payload.dto.InventoryDto;
import com.abhi.repository.BranchRepository;
import com.abhi.repository.InventoryRepository;
import com.abhi.repository.ProductRepository;
import com.abhi.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) throws Exception {

        Branch branch = branchRepository.findById(inventoryDto.getBranchId())
                .orElseThrow(
                () -> new Exception("Branch not exsist...")
        );
        Product product = productRepository.findById(inventoryDto.getProductId())
                .orElseThrow(
                () -> new Exception("Product not exsist...")
        );

        Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch, product);
        Inventory savedInventory = inventoryRepository.save(inventory);

        return InventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryDto updateInventory(Long id,InventoryDto inventoryDto) {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Inventory not found...")
        );

        inventory.setQuantity(inventoryDto.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDto(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Inventory not found...")
        );
        inventoryRepository.delete(inventory);

    }

    @Override
    public InventoryDto getInventoryByProductIdAndBranchId(Long productId, Long branchId) {

        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public InventoryDto getInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Inventory not found...")
        );

        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> getAllInventoryByBranchId(Long branchId) {
       List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
       return inventories.stream().map(InventoryMapper::toDto).toList();
    }
}
