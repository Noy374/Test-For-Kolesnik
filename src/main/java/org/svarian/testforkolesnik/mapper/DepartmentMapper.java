package org.svarian.testforkolesnik.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.svarian.testforkolesnik.entity.Department;
import org.svarian.testforkolesnik.web.rest.dto.DepartmentDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {
    DepartmentDTO toDTO(Department department);
    Department toEntity(DepartmentDTO departmentDTO);
}
