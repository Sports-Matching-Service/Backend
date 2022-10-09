package sportsmatchingservice.auth.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sportsmatchingservice.auth.utils.CustomAuthorityUtils;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.exceptions.exception.GeneralException;
import sportsmatchingservice.users.domain.User;
import sportsmatchingservice.users.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User findUser = optionalUser.orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND, new UsernameNotFoundException("USER_NOT_FOUND")));
        return new PrincipalDetails(findUser);
    }

    private final class PrincipalDetails extends User implements UserDetails {

        PrincipalDetails(User user) {
            setEmail(user.getEmail());
            setNickname(user.getNickname());
            setPassword(user.getPassword());
            setRoles(user.getRoles());
        }


        @Override

        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }


        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
