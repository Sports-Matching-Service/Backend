package sportsmatchingservice.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sportsmatchingservice.auth.utils.CustomAuthorityUtils;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.exceptions.exception.DuplicatedDataException;
import sportsmatchingservice.users.domain.User;
import sportsmatchingservice.users.dto.UserPostDto;
import sportsmatchingservice.users.repository.UserRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final CustomAuthorityUtils authorityUtils;


    public boolean createUser(UserPostDto userPostDto) {
        try {
            if (userPostDto == null) return false;

            User user = userPostDto.toEntity();
            user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));

            List<String> roles = authorityUtils.createRoles(user.getEmail());
            user.setRoles(roles);

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new DuplicatedDataException(ErrorCode.BAD_REQUEST, e);
        }
    }

}
