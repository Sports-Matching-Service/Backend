package sportsmatchingservice.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.exceptions.exception.DuplicatedDataException;
import sportsmatchingservice.exceptions.exception.GeneralException;
import sportsmatchingservice.users.dto.UserPostDto;
import sportsmatchingservice.users.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(UserPostDto userPostDto) {
        try {
            if (userPostDto == null) return false;
            userRepository.save(userPostDto.toEntity());
            return true;
        } catch (Exception e) {
            throw new DuplicatedDataException(ErrorCode.BAD_REQUEST, e);
        }
    }

}
