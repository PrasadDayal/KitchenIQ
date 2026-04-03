package com.KitchenIQ.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KitchenIQ.model.Vendor;
import com.KitchenIQ.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    
    private final VendorService vendorService;

    public VendorController(VendorService vendorService){
        this.vendorService = vendorService;
    }

    @PostMapping
    public Vendor addVendor(@RequestBody Vendor vendor){
        return vendorService.saveVendor(vendor);
    }

    @GetMapping
    public List<Vendor> getAllVendors(){
        return vendorService.getAllVendors();
    }
}
