package com.abhi.controller;

import com.abhi.exceptions.UserException;
import com.abhi.model.Branch;
import com.abhi.payload.dto.BranchDto;
import com.abhi.payload.response.ApiResponse;
import com.abhi.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) throws Exception {

        BranchDto createdBranch = branchService.createBranch(branchDto);

        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Long id) throws Exception {

        BranchDto createdBranch = branchService.getBranchById(id);

        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDto>> getAllBranchesByStoreId(@PathVariable Long storeId)
            throws UserException {

        List<BranchDto> createdBranch = branchService.getAllBranchesByStoreId(storeId);

        return ResponseEntity.ok(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Long id,
                                                  @RequestBody BranchDto branchDto) throws Exception {

        BranchDto createdBranch = branchService.updateBranch(id,branchDto);

        return ResponseEntity.ok(createdBranch);
    }

    @DeleteMapping("/store/{storeId}")
    public ResponseEntity<ApiResponse> deleteBranchById(@PathVariable Long id)
            throws UserException {

         branchService.deleteBranch(id);
         ApiResponse apiResponse = new ApiResponse();

         apiResponse.setMessage("Branch deleted successfully");

         return ResponseEntity.ok(apiResponse);

    }

}
