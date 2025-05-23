package org.synergym.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.synergym.backend.dto.UserDTO;
import org.synergym.backend.entity.User;
import org.synergym.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return entityToDto(user);  // toDTO 메서드를 사용
    }

    @Transactional
    @Override
    public Integer addUser(UserDTO userDTO) {
        User user = dtoToEntity(userDTO);
        user = userRepository.save(user);
        return user.getId();  // 저장 후 DTO로 반환
    }

    @Transactional
    @Override
    public void updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.changeUsername(userDTO.getUsername());
        user.changePassword(userDTO.getPassword());
        user.changeEmail(userDTO.getEmail());
        user.changeBirthDate(userDTO.getBirthDate());
        user.changeGender(userDTO.getGender());
        user.changeWeight(userDTO.getWeight());
        user.changeHeight(userDTO.getHeight());
        user.changeFitnessLevel(userDTO.getFitnessLevel());

        userRepository.save(user); // 업데이트된 Entity 저장
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::entityToDto)  // Entity를 DTO로 변환
                .collect(Collectors.toList());
    }
}
