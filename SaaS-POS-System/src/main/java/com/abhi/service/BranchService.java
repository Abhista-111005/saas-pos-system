package com.abhi.service;

import com.abhi.exceptions.UserException;
import com.abhi.model.User;
import com.abhi.payload.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto createBranch(BranchDto branchDto) throws UserException;
    BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception;
    void deleteBranch(Long id);
    List<BranchDto> getAllBranchesByStoreId(Long storeId);
    BranchDto getBranchById(Long id);

}
