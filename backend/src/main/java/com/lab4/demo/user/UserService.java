package com.lab4.demo.user;

import com.lab4.demo.consultation.ConsultationRepository;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConsultationRepository consultationRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;


    private User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    private User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::toDtoUserMinimal)
                .collect(toList());
    }

    public List<UserDTO> allUsers() {
        List<UserDTO> collect = userRepository.findAll()
                .stream().map(userMapper::toUserDTO)
                .collect(toList());
        return collect;
    }


    public void delete(Long id) {
        userRepository.deleteById((id));
    }

    public UserDTO create(UserDTO userDTO, String pickedRole) {

        User user = userMapper.fromUserDTO(userDTO);
        Set<Role> set = new HashSet<>();
        Role role = Role.builder().name(ERole.valueOf(pickedRole)).build();
        set.add(role);
        user.setRoles(set);
        user.setPassword(encoder.encode(user.getPassword()));

        return userMapper.toUserDTO(userRepository
                .save(user));
    }

    public UserDTO edit(Long id, UserDTO userDTO) {
        User user = findById(id);
        user.setUsername(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        if(!userDTO.getPassword().equals(""))
            user.setPassword(encoder.encode(userDTO.getPassword()));

        return userMapper.toUserDTO(userRepository.save(user));
    }

    public List<UserDTO> findAvailableDoctors(long timeSlot){
        List<UserDTO> availableDoctors = new ArrayList<>();

        Role role = Role.builder().name(ERole.valueOf("doctor")).build();
        for(User user: userRepository.findAllByRolesContaining(role))
            if(consultationRepository.findByDoctorIdAndTimeOfConsultation(user.getId(), timeSlot).isEmpty())
                availableDoctors.add(userMapper.toUserDTO(user));


        return availableDoctors;
    }
}
