package com.erp.Ecommeres.profile.service;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.profile.dto.AddressResponseDTO;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.repo.AddressRepo;
import com.erp.Ecommeres.repo.UserRepo;

@Service
public class AddressService {

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressService(AddressRepo addressRepo, UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    public AddressResponseDTO saveAndFetchAddress(Long userId, Address address) {

        // save address
        address.setUserId(userId);
        Address savedAddress = addressRepo.save(address);

        // fetch user
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // build response
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setFullName(user.getFullName());
        dto.setMobileNumber(user.getMobileNumber());

        dto.setAddressLine(savedAddress.getAddressLine());
        dto.setCity(savedAddress.getCity());
        dto.setState(savedAddress.getState());
        dto.setPincode(savedAddress.getPincode());

        return dto;
    }
}
