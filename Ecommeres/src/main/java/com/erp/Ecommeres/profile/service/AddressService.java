package com.erp.Ecommeres.profile.service;

import java.util.List;

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
    
 // GET USER ADDRESSES
    public List<Address> getAddresses(Long userId) {
        return addressRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // DELETE ADDRESS
    public void deleteAddress(Long id) {
        addressRepo.deleteById(id);
    }
    public AddressResponseDTO updateAddress(Long id, Address updated) {

        Address existing = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existing.setAddressLine(updated.getAddressLine());
        existing.setCity(updated.getCity());
        existing.setState(updated.getState());
        existing.setPincode(updated.getPincode());
       

        addressRepo.save(existing);

        return new AddressResponseDTO();
    }

}
