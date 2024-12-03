package org.svarian.testforkolesnik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.svarian.testforkolesnik.entity.Department;
import org.svarian.testforkolesnik.entity.Role;
import org.svarian.testforkolesnik.entity.User;
import org.svarian.testforkolesnik.exception.EntityNotFoundException;
import org.svarian.testforkolesnik.mapper.UserMapper;
import org.svarian.testforkolesnik.repository.DepartmentRepository;
import org.svarian.testforkolesnik.repository.UserRepository;
import org.svarian.testforkolesnik.web.rest.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User с " + id + " не найден"));
        return userMapper.toDTO(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void createUser(UserDTO userDTO) {
        User user = userMapper.fromDTO(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Department department = departmentRepository.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid department ID"));
        user.setDepartment(department);

        userRepository.save(user);
    }

    public void updateUser(Long id, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        User updatedUser = userMapper.fromDTO(updatedUserDTO);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setRoles(updatedUser.getRoles());
        Department department = departmentRepository.findById(updatedUserDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid department ID"));

        existingUser.setDepartment(department);

        userRepository.save(existingUser);
    }

    public List<Role> getAllRoles() {
        return List.of(Role.EMPLOYEE,Role.MANAGER,Role.ADMIN);
    }



    public List<UserDTO> findAllByDepartmentId(Long departmentId) {
        List<User> users = userRepository.findAllByDepartmentId(departmentId);
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
        UserDTO userDTO = userMapper.toDTO(user);
        userDTO.setDepartmentId(user.getDepartment().getId());
        return userDTO;
    }
}
