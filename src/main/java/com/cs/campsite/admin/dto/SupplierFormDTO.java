package com.cs.campsite.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class SupplierFormDTO {

    private Integer supplier_id;
    private String supplier_name;
    private String supplier_contact_person;
    private String supplier_contact_phone;
    private String supplier_phone;
    private String supplier_email;
    private String supplier_address;
    private String supplier_type; 
    private String supplier_status; 
    private String supplier_bank_account; 

    private String action;
    
    
    public void setSupplier_contact_phone(String supplier_contact_phone) {
        if (supplier_contact_phone != null && !supplier_contact_phone.trim().isEmpty()) {
            supplier_contact_phone = supplier_contact_phone.trim().replaceAll("-", "");

            if (supplier_contact_phone.length() == 11) {
                this.supplier_contact_phone = supplier_contact_phone.substring(0, 3) + "-" + supplier_contact_phone.substring(3, 7) + "-" + supplier_contact_phone.substring(7);
            } else if (supplier_contact_phone.length() == 10) {
                if (supplier_contact_phone.startsWith("02")) {
                    this.supplier_contact_phone = supplier_contact_phone.substring(0, 2) + "-" + supplier_contact_phone.substring(2, 6) + "-" + supplier_contact_phone.substring(6);
                } else {
                    this.supplier_contact_phone = supplier_contact_phone.substring(0, 3) + "-" + supplier_contact_phone.substring(3, 6) + "-" + supplier_contact_phone.substring(6);
                }
            } 
        } else {
            this.supplier_contact_phone = "-";
        }
    }
}