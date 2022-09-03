package com.example.pywo.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
   /* private UsersRepository usersRepository;

    private UserCredentialsRepository userCredentialsRepository;

    private UserRoleRepository userRoleRepository;

    @Autowired
    public MyUserDetailsService(UsersRepository usersRepository, UserCredentialsRepository userCredentialsRepository, UserRoleRepository userRoleRepository) {
        this.usersRepository = usersRepository;
        this.userCredentialsRepository = userCredentialsRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      Users user = userCredentialsRepository.findUserByUsername(s).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserCredentials().getUsername(), user.getUserCredentials().getPassword(), true, true, true,
                true, getAuthorities(user.getUserRoles()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    private List<String> getPrivileges(Collection<UserRole> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> privilegeList = new ArrayList<>();

        for (UserRole role : roles) {
            privileges.add(role.getUserRoleType());
            privilegeList.addAll(role.getPrivileges());
        }
        for (Privilege privilege : privilegeList) {
            privileges.add(privilege.getName());
        }
        return privileges;
    }
*/
}
