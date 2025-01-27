package org.softuni.university.service;

import org.modelmapper.ModelMapper;
import org.softuni.university.domain.entities.User;
import org.softuni.university.domain.models.service.UserServiceModel;
import org.softuni.university.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleService roleService,
            ModelMapper modelMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) throws Exception {
        this.roleService.seedRolesInDb();
        userSetAuthorities(userServiceModel);

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("Username not found!"));

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        user.setPassword(userServiceModel.getPassword() != null ?
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()) :
                user.getPassword());

        user.setEmail(userServiceModel.getEmail());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) throws Exception {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect id!"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "student":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_STUDENT"));
                break;
            case "public":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_STUDENT"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_PUBLIC_RELATIONS"));
                break;
            case "chair":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_STUDENT"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_PUBLIC_RELATIONS"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_CHAIR_OF_A_DEPARTMENT"));
                break;
            case "dean":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_STUDENT"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_PUBLIC_RELATIONS"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_CHAIR_OF_A_DEPARTMENT"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_DEAN"));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    private void userSetAuthorities(UserServiceModel userServiceModel) throws Exception {
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_STUDENT"));
        }
    }
}
