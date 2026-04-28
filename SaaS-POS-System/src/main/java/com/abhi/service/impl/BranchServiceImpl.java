package com.abhi.service.impl;

import com.abhi.exceptions.UserException;
import com.abhi.mapper.BranchMapper;
import com.abhi.model.Branch;
import com.abhi.model.Store;
import com.abhi.model.User;
import com.abhi.payload.dto.BranchDto;
import com.abhi.repository.BranchRepository;
import com.abhi.repository.StoreRepository;
import com.abhi.repository.UserRepository;
import com.abhi.service.BranchService;
import com.abhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDto createBranch(BranchDto branchDto) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDto, store);
        Branch savedBranch = branchRepository.save(branch);

        return BranchMapper.toDto(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {

        Branch exiting = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch not exsist...")
        );
        exiting.setName(branchDto.getName());
        exiting.setAddress(branchDto.getAddress());
        exiting.setPhone(branchDto.getPhone());
        exiting.setEmail(branchDto.getEmail());
        exiting.setOpenTime(branchDto.getOpenTime());
        exiting.setCloseTime(branchDto.getCloseTime());
        exiting.setWorkingDays(branchDto.getWorkingDays());
        exiting.setUpdatedAt(LocalDateTime.now());

        Branch updatedBranch = branchRepository.save(exiting);
        return BranchMapper.toDto(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) {
        Branch exiting = branchRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Branch not exsist...")
        );
        branchRepository.delete(exiting);
    }

    @Override
    public List<BranchDto> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public BranchDto getBranchById(Long id) {
        Branch exiting = branchRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Branch not exsist...")
        );
        return BranchMapper.toDto(exiting);
    }
}
