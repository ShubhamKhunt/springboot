package basic.springboot.simple.service;

import basic.springboot.simple.entity.User;
import basic.springboot.simple.model.DTO.AcceptInviteDTO;
import basic.springboot.simple.model.DTO.AddUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> findByEmail(String email);

    public User save(AddUserDTO userDTO);

    public Boolean remove(Integer userId);

    public Boolean acceptInvite(AcceptInviteDTO acceptInviteDTO);

    public List<User> getUsers(Integer pageNo, String sortBy, String sortDir);
}
