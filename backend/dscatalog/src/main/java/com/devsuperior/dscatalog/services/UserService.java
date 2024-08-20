package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTOs.RoleDTO;
import com.devsuperior.dscatalog.DTOs.UserDTO;
import com.devsuperior.dscatalog.DTOs.UserInsertDTO;
import com.devsuperior.dscatalog.DTOs.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DataBaseExcepetion;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable){
        Page<User> UserList = userRepository.findAll(pageable);
        return UserList.map(UserDTO::new);
//        return UserList.stream().map(User -> new UserDTO(User)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> entityUserOpt = userRepository.findById(id);
        User entity = entityUserOpt.orElseThrow(()-> new ResourceNotFoundException("User not found")); /*Estanciando uma execeção para tratar erros*/
        return new UserDTO(entity);
    }

    @Transactional(readOnly = true)
    public UserDTO createUser(UserInsertDTO userDTO) {
        User newEntity = new User();
        copyDtoToEntity(userDTO, newEntity);
        newEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User entity = userRepository.save(newEntity);
        return new UserDTO(entity);
    }

    @Transactional // é uma forma de garantir a atomicidade, consistência, isolamento e durabilidade (ACID) das operações em um banco de dados.
    public UserDTO updateUser(Long id, UserUpdateDTO userDTO) {
//        OBS: getReferenceById= é criado uma estância em memória do objeto sem "bater" no banco de dados, somente quando salvar é que de fato a applicação chama o banco de dados
        try {
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(userDTO, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

    public void deleteUser (Long id){
        try {
            User User = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseExcepetion("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity){
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.getRoles().clear();

        for (RoleDTO roleDto : dto.getRole()){
            Optional<Role> role = roleRepository.findById(roleDto.getId()); //poderia usar o getone porém não funciona
            Role roleEntity = role.get();
            entity.getRoles().add(roleEntity);
        }
    }
}