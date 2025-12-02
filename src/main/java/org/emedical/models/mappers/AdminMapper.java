package org.emedical.models.mappers;

import org.emedical.models.dto.Admin;
import org.emedical.models.entities.AdminEntity;

public class AdminMapper {
    
    public static Admin toDto(AdminEntity e) {
        Admin a = new Admin();

        a.setId(e.getId());
        a.setFirst_name(e.getFirst_name());
        a.setLast_name(e.getLast_name());
        a.setUsername(e.getUsername());
        a.setPassword(e.getPassword());
        a.setRole(e.getRole());
        a.setIsActive(e.getIsActive());
        
        return a;
    }

    public static AdminEntity toEntity(Admin a) {
        AdminEntity e = new AdminEntity();
        e.setId(a.getId());
        e.setFirst_name(a.getFirst_name());
        e.setLast_name(a.getLast_name());
        e.setUsername(a.getUsername());
        e.setPassword(a.getPassword());
        e.setRole(a.getRole());
        e.setIsActive(a.getIsActive());
        return e;
    }

    public static void updateEntity(AdminEntity entity, Admin admin) {

        if(admin.getFirst_name() != null && !admin.getFirst_name().isEmpty()) {
            entity.setFirst_name(admin.getFirst_name());
        }

        if(admin.getLast_name() != null && !admin.getLast_name().isEmpty()) {
            entity.setLast_name(admin.getLast_name());
        }

        if(admin.getUsername() != null && !admin.getUsername().isEmpty()) {
            entity.setUsername(admin.getUsername());
        }

        if(admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            entity.setPassword(admin.getPassword());
        }

        if(admin.getIsActive() != null) {
            entity.setIsActive(admin.getIsActive());
        }
    }
}
