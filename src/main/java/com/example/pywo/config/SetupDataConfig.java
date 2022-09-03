package com.example.pywo.config;


import com.example.pywo.model.Privilege;
import com.example.pywo.model.User;
import com.example.pywo.model.UserRole;
import com.example.pywo.repository.PrivilegeRepository;
import com.example.pywo.repository.UserRepository;
import com.example.pywo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Component
public class SetupDataConfig implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    private UserRoleRepository userRoleRepository;

    private PrivilegeRepository privilegeRepository;

    private UserRepository userRepository;


    @Autowired
    public SetupDataConfig(UserRoleRepository userRoleRepository, PrivilegeRepository privilegeRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.privilegeRepository = privilegeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;


        Privilege readPrivilege
                = createPrivilegeOrReturnExisting("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeOrReturnExisting("WRITE_PRIVILEGE");

        Set<Privilege> adminPrivileges = new HashSet<>();
        adminPrivileges.add(readPrivilege);
        adminPrivileges.add(writePrivilege);

        Set<Privilege> userPrivileges = new HashSet<>();
        userPrivileges.add(readPrivilege);


        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", userPrivileges);

        User user;

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(createRoleIfNotFound("ROLE_USER", userPrivileges));
        //ovo ces mozda mijenjati
        //staviti da se role ne predaje u constructoru nego nekako drugacije vjv automatski prilikom registracije
        userRepository.save(
                user =  new User("Daniel","Škrlac","email@email.com","daniel274","daniel1234",userRoles)
        );

        alreadySetup = true;

    }
    Privilege createPrivilegeOrReturnExisting(String name) {
        Privilege privilege;

        if(privilegeRepository.findByName(name).isPresent())
            return privilege = privilegeRepository.findByName(name).get();
        else return createAndSavePrivilegeInstance(name);

        /*return privilege = privilegeRepository.findByName(name)
                .orElse(createAndSavePrivilegeInstance(name));*/
    }
    private Privilege createAndSavePrivilegeInstance(String name){
        Privilege privilege = new Privilege(name);
        return privilegeRepository.save(privilege);
    }
    UserRole createRoleIfNotFound(String userRoleType, Set<Privilege> privileges) {
        UserRole role;
        if(userRoleRepository.findByUserRoleType(userRoleType).isPresent())
            return role = userRoleRepository.findByUserRoleType(userRoleType).get();
        else return createAndSaveRoleInstance(userRoleType, privileges);

    }
    private UserRole createAndSaveRoleInstance(String userRoleType, Set<Privilege> privileges) {
        UserRole role = new UserRole(userRoleType);
        role.setPrivileges(privileges);
        return userRoleRepository.save(role);
    }

}
