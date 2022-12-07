package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Tenant;
import basic.springboot.simple.entity.User;
import basic.springboot.simple.exception.AlreadyExistsException;
import basic.springboot.simple.exception.InternalServerException;
import basic.springboot.simple.model.DTO.TenantDTO;
import basic.springboot.simple.repository.TenantRepository;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.EmailService;
import basic.springboot.simple.service.TenantService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TenantServiceImpl implements TenantService {

    // Autowire Tenant Repository
    private TenantRepository tenantRepository;

    // Autowire User Repository
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Tenant register(TenantDTO tenantDto) {

        // Check if user already exists
        if(tenantRepository.findByEmail(tenantDto.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email address already exists");
        }

        try{
            Tenant tenant= modelMapper.map(tenantDto, Tenant.class);
            tenant.setCreated(new Date());
            tenant.setModified(new Date());

            Tenant savedTenant = tenantRepository.save(tenant);

            // Add Tenant User in user
            User user = new User();
            user.setName(tenant.getFirstName() + " " + tenant.getLastName());
            user.setEmail(tenant.getEmail());
            user.setPassword(passwordEncoder.encode(tenantDto.getPassword()));
            user.setIsActive(1);
            user.setInvite(1);
            user.setRoleId(1);
            user.setLastLogin(null);
            user.setCreated(new Date());
            user.setModified(new Date());
            user.setTenant(savedTenant);

            userRepository.save(user);

            JSONObject jsonData = new JSONObject();
            jsonData.put("name", tenant.getFirstName() + " " + tenant.getLastName());
            jsonData.put("Name", tenant.getFirstName() + " " + tenant.getLastName());
            // emailService.sendMail(user, jsonData, "Onboard User");
            emailService.sendMail(user, jsonData, EmailServiceImpl.EventType.ONBOARD_USER);

            return savedTenant;
        } catch (Exception ex){
            throw new InternalServerException("Something Went Wrong!");
        }
    }
}
