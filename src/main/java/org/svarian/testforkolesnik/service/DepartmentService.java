package org.svarian.testforkolesnik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.svarian.testforkolesnik.entity.Department;
import org.svarian.testforkolesnik.exception.EntityNotFoundException;
import org.svarian.testforkolesnik.repository.DepartmentRepository;


import org.svarian.testforkolesnik.mapper.DepartmentMapper;
import org.svarian.testforkolesnik.web.rest.dto.DepartmentDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public DepartmentDTO findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        return departmentMapper.toDTO(department);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public void save(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        departmentRepository.save(department);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
}
