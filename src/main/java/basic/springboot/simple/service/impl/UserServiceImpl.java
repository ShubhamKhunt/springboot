package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.entity.User;
import basic.springboot.simple.exception.InternalServerException;
import basic.springboot.simple.exception.NoDataFoundException;
import basic.springboot.simple.exception.UserPlanException;
import basic.springboot.simple.model.DTO.AcceptInviteDTO;
import basic.springboot.simple.model.DTO.AddUserDTO;
import basic.springboot.simple.repository.TenantRepository;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.BaseService;
import basic.springboot.simple.service.EmailService;
import basic.springboot.simple.service.UserService;
import basic.springboot.simple.util.AESCipherUtil;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User save(AddUserDTO userDTO) {

        Tenant tenant = tenantRepository.findById(baseService.getTenantId()).get();

        /** Validate Allowed users in plan
        **/
        // Get Number of users
        Integer noOfUsersPresent = userRepository.getNoOfUsersOfTenant(tenant);

        // Get Allowed users
        Integer allowedUsers = tenantRepository.getAllowedUsersOfPlan(baseService.getTenantId());

        if(noOfUsersPresent >= allowedUsers){
            throw new UserPlanException("Allowed user addition limit exhausted in your current plan, Please upgrade your plan to extend limit.");
        }

        User user = modelMapper.map(userDTO, User.class);

        user.setTenant(tenant);
        user.setInvite(0);
        user.setCreated(new Date());
        user.setModified(new Date());

        userRepository.save(user);

        try{
            JSONObject jsonData = new JSONObject();
            jsonData.put("name", user.getName());
            jsonData.put("Name", user.getName());

            String inviteToken = user.getEmail() + "-" + user.getId() + "-" + new Date().getTime();
            String cipherText = AESCipherUtil.encrypt(inviteToken);

            jsonData.put("link", "https://www.the-team-app.com/invite-user/" + cipherText);
            jsonData.put("adminname", "Team");
            jsonData.put("adminemail", "team@email.com");

            emailService.sendMail(user, jsonData, EmailServiceImpl.EventType.INVITE_USER);
        }catch (Exception ex){
            throw new InternalServerException(ex.getMessage());
        }

        return user;
    }

    @Override
    public Boolean remove(Integer userId) {

        System.out.println("baseService.getTenant() : " + baseService.getTenant().toString());

        // Validate if user exists or not
        Optional<User> user = userRepository.findByIdAndTenant(userId, baseService.getTenant());
        if(!user.isPresent()){
            throw new NoDataFoundException("User not Found");
        }

        try{
            userRepository.delete(user.get());

            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean acceptInvite(AcceptInviteDTO acceptInviteDTO) {

        // decode cipher
        try {
             String originalToken = AESCipherUtil.decrypt(acceptInviteDTO.getInviteToken());

             // Validate for null
             if(Objects.isNull(originalToken)){
                 throw new BadCredentialsException("Bad Invite Token");
             }

            // Validate for 3 sub string [1=email 2=Id 3=timestamp]
             String[] splitToken = originalToken.split("-");
             if(splitToken.length != 3){
                 throw new BadCredentialsException("Bad Invite Token");
             }

             String username = splitToken[0];
             Integer userId = Integer.parseInt(splitToken[1]);
             Long tokenTimestamp = Long.parseLong(splitToken[2]);

             // validate token time for 48 hr
             if(new Date().getTime() > (tokenTimestamp + (2*24*60*60*1000))){
                 throw new BadCredentialsException("Invite Token has been expired");
             }

             // Validate if user exists
             Optional<User> findUser = userRepository.findById(userId);
             if(!findUser.isPresent()){
                 throw new NoDataFoundException("User not found");
             }

             // Update user details
             User user = findUser.get();
             user.setInvite(0);
             user.setIsActive(1);
             user.setPassword(passwordEncoder.encode(acceptInviteDTO.getPassword()));

             userRepository.save(user);

             return true;
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsers(Integer pageNo, String sortBy, String sortDir) {
        Optional<Tenant> tenant = tenantRepository.findById(baseService.getTenantId());

        if(!tenant.isPresent()){
            throw new NoDataFoundException("Invalid Request");
        }

        Sort sort = Sort.by((sortDir.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC), sortBy);
        Pageable page = PageRequest.of(pageNo, 10, sort);

        List<User> users = userRepository.getUsersByTenant(tenant.get(), page).get();

        return users;
    }
}
