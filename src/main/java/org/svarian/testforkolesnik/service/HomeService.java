package org.svarian.testforkolesnik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.svarian.testforkolesnik.entity.Role;
import org.svarian.testforkolesnik.web.rest.dto.HomePageDTO;
import org.svarian.testforkolesnik.web.rest.dto.UserDTO;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserService userService;
    private final DepartmentService departmentService;

    public HomePageDTO buildHomePage(String username) {
        UserDTO user = userService.findByUsername(username);
        List<String> roles = extractRoles(user);

        HomePageDTO homePageDTO = new HomePageDTO();
        homePageDTO.setUser(user);
        homePageDTO.setUserRoles(roles);

        populateColleagues(homePageDTO, user);
        populateAdminData(homePageDTO, user);
        populateManagerData(homePageDTO, user);

        return homePageDTO;
    }

    private List<String> extractRoles(UserDTO user) {
        return user.getRoles().stream()
                .map(Enum::toString)
                .toList();
    }

    private void populateColleagues(HomePageDTO dto, UserDTO user) {
        if (user.getDepartmentId() != null) {
            List<UserDTO> colleagues = userService.findAllByDepartmentId(user.getDepartmentId());
            dto.setColleagues(colleagues);
        } else {
            dto.setColleagues(Collections.emptyList());
        }
    }

    private void populateAdminData(HomePageDTO dto, UserDTO user) {
        if (user.getRoles().contains(Role.ADMIN)) {
            dto.setDepartments(departmentService.findAll());
            dto.setAllEmployees(userService.getAllUsers());
        }
    }

    private void populateManagerData(HomePageDTO dto, UserDTO user) {
        if (user.getRoles().contains(Role.MANAGER)) {
            dto.setDepartment(departmentService.findById(user.getDepartmentId()));
            dto.setDepartmentEmployees(user.getDepartmentId() != null
                    ? userService.findAllByDepartmentId(user.getDepartmentId())
                    : Collections.emptyList());
        }
    }
}
