package org.emedical.service;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Admin;

public interface AdminService {
    List<Admin> findAll();
    Admin findById(Integer id) throws NotFoundException;
    
}
