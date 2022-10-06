package sportsmatchingservice.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.users.dto.UserPostDto;
import sportsmatchingservice.users.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public boolean signup(@RequestBody UserPostDto userPostDto) {
        System.out.println(userPostDto);
        boolean result = userService.createUser(userPostDto);
        return result;
    }

}
