package com.cs.campsite.admin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class WarehousesFormDTO {

    private Integer warehouse_id;
    private String warehouse_name;
    private String warehouse_location;
    private String warehouse_phone;
    private String warehouse_email;
    private String warehouse_created_at;
    private String warehouse_updated_at;

    private String action;
    
    

}