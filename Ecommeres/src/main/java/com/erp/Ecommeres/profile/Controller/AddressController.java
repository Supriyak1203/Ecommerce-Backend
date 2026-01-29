package com.erp.Ecommeres.profile.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.Ecommeres.profile.dto.AddressResponseDTO;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.service.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin("*")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public AddressResponseDTO saveAddress(
            @RequestHeader("userId") Long userId,
            @RequestBody Address address) {

        return addressService.saveAndFetchAddress(userId, address);
    }
    // GET USER ADDRESSES
    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddresses(userId));
    }

    // DELETE ADDRESS
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Address deleted successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @RequestBody Address address) {

        return ResponseEntity.ok(addressService.updateAddress(id, address));
    }

}
