package sportsmatchingservice.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.exceptions.exception.DuplicatedDataException;
import sportsmatchingservice.exceptions.exception.GeneralException;
import sportsmatchingservice.users.domain.User;
import sportsmatchingservice.users.dto.UserPostDto;
import sportsmatchingservice.users.repository.UserRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(UserPostDto userPostDto) {
        try {
            if (userPostDto == null) return false;

            User user = userPostDto.toEntity();
            user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new DuplicatedDataException(ErrorCode.BAD_REQUEST, e);
        }
    }

}
