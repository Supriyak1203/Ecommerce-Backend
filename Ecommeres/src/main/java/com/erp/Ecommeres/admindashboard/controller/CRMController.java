package com.erp.Ecommeres.admindashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.Ecommeres.admindashboard.Service.CRMService;
import com.erp.Ecommeres.admindashboard.dto.CRMDTO;

@RestController
@RequestMapping("/api/admin/crm")
@CrossOrigin("*")
public class CRMController {

    private final CRMService crmService;

    public CRMController(CRMService crmService) {
        this.crmService = crmService;
    }

    @GetMapping("/customers")
    public List<CRMDTO> getCustomers() {
        return crmService.getAllCustomers();
    }
    
    @GetMapping("/search")
    public List<CRMDTO> searchCustomers(@RequestParam String keyword) {
        return crmService.searchCustomers(keyword);
    }
}
