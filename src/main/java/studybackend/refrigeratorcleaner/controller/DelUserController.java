package studybackend.refrigeratorcleaner.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import studybackend.refrigeratorcleaner.Service.DelUserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DelUserController {
    private final DelUserService dService;
    @GetMapping(value = "board/delUser")
    public String delUser() {
        String nickName = "delTest";
        dService.deleteByNickName(nickName);
        return "home";
    }
}
