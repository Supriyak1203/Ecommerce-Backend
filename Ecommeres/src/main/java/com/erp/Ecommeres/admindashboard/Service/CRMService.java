package com.erp.Ecommeres.admindashboard.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.admindashboard.dto.CRMDTO;
import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.entity.Profile;
import com.erp.Ecommeres.profile.repo.AddressRepo;
import com.erp.Ecommeres.profile.repo.ProfileRepo;
import com.erp.Ecommeres.repo.UserRepo;

@Service
public class CRMService {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final ProfileRepo profileRepo;

    public CRMService(UserRepo userRepo,
                      AddressRepo addressRepo,
                      ProfileRepo profileRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.profileRepo = profileRepo;
    }

    public List<CRMDTO> getAllCustomers() {

        List<User> users = userRepo.findAll();
        List<CRMDTO> list = new ArrayList<>();

        for (User user : users) {

            Optional<Address> addressOpt =
                    addressRepo.findFirstByUserId(user.getId());

            Profile profile =
                    profileRepo.findByUserId(user.getId());

            CRMDTO dto = new CRMDTO();
            dto.setId(user.getId());
            dto.setUserId(user.getId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setMobile(user.getMobileNumber());

            dto.setAddress(
                addressOpt.isPresent()
                    ? addressOpt.get().getCity()
                    : null
            );

            dto.setGender(profile != null ? profile.getGender() : null);
            dto.setAge(profile != null ? profile.getAge() : null);

            list.add(dto);
        }
        return list;
    }
    
    public List<CRMDTO> searchCustomers(String keyword) {

        List<User> users =
                userRepo.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword, keyword
                );

        List<CRMDTO> list = new ArrayList<>();

        for (User user : users) {

            Optional<Address> addressOpt =
                    addressRepo.findFirstByUserId(user.getId());

            Profile profile =
                    profileRepo.findByUserId(user.getId());

            CRMDTO dto = new CRMDTO();
            dto.setId(user.getId());
            dto.setUserId(user.getId());
            dto.setFullName(user.getFullName());
            dto.setEmail(user.getEmail());
            dto.setMobile(user.getMobileNumber());

            dto.setAddress(
                    addressOpt.isPresent()
                            ? addressOpt.get().getCity()
                            : null
            );

            dto.setGender(profile != null ? profile.getGender() : null);
            dto.setAge(profile != null ? profile.getAge() : null);

            list.add(dto);
        }
        return list;
    }

}
