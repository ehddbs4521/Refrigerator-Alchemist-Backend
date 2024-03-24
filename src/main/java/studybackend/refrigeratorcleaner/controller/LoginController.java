package studybackend.refrigeratorcleaner.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import studybackend.refrigeratorcleaner.Form.LoginForm;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    @GetMapping(value = "/")
    public  String loginPage(Model model) {
      model.addAttribute("LoginForm", new LoginForm());

      //List<user_info> userInfo = service.getUserData("조승빈");

        return "loginPage";
    }
    @PostMapping(value = "/")
    public void postLoginPage(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음
        if (session != null) {
            session.invalidate(); // 세션 종료
        }
    }
}
