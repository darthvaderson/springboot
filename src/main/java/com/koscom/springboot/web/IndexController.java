package com.koscom.springboot.web;

import com.koscom.springboot.config.auth.dto.SessionUser;
import com.koscom.springboot.config.auth.login.LoginUser;
import com.koscom.springboot.domain.user.User;
import com.koscom.springboot.service.PostsService;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;


//   아래의 어노테이션 기반 코드로 개선 이전 코드
//    @GetMapping("/")
//    public String index(Model model) {  // ModelAndView
//        postsService.save(new PostsSaveRequestDto("test","test","test"));
//        model.addAttribute("posts",postsService.findAllDesc());
//
//        SessionUser user = (SessionUser)httpSession.getAttribute("user");
//        if(user != null){
//            model.addAttribute("user",user.getName());
//        }
//        return "index";
//    }

//    private final HttpSession httpSession;

    // 컨트롤러에서 리턴에 index라고 하면 자동으로 템플릿에 있는 index을 찾는다. - thymeleaf냐 mustache냐 : starter에 넣는것에 따라 달라짐
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {  // ModelAndView
        postsService.save(new PostsSaveRequestDto("test","test","test"));
        model.addAttribute("posts",postsService.findAllDesc());

//         SessionUser user = (SessionUser)httpSession.getAttribute("user");
        if(user != null){
            model.addAttribute("user",user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {

        return "posts-save";
    }

    // localhost:8080/posts/update/1 <<< 1번글의 수정 화면으로 이동하게 됩니다.
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
