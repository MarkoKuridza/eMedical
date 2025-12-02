package org.emedical.service;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Admin;

public interface AdminService {
    List<Admin> findAll();
    Admin findById(Integer id) throws NotFoundException;
    Admin saveAdmin(Admin admin);
    Admin editAdmin(Admin admin);
    void setActive(Integer id);
    void setInactive(Integer id);
}
