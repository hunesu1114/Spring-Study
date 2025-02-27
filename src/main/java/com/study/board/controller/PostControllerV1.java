package com.study.board.controller;

import com.study.board.entity.Post;
import com.study.board.repository.PostRepositoryV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

//@Controller
@RequestMapping("/board")
public class PostControllerV1 {

    private PostRepositoryV1 postRepositoryV1;

    @Autowired
    public PostControllerV1(PostRepositoryV1 postRepositoryV1) {
        this.postRepositoryV1 = postRepositoryV1;
    }

    //게시판 메인
    @GetMapping("/main")
    public String posts(Model model) {
        List<Post> posts = postRepositoryV1.findAll();
        model.addAttribute("posts", posts);
        return "/board/main";
    }

    //단일 게시글
    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model) {
        Post post = postRepositoryV1.findById(id);
        model.addAttribute("post", post);
        return "/board/post";
    }

    //게시글 등록
    @GetMapping("/addPost")
    public String addPost(Model model) {
/*        th:object 를 적용하려면 먼저 해당 오브젝트 정보를 넘겨주어야 한다.
        등록 폼이기 때문에 데이터가 비어있는 빈 오브젝트를 만들어서 뷰에 전달하자.
        ->빈 생성자 만들어줘야함
        */
        model.addAttribute("post", new Post());
        return "/board/addPost";
    }

    //게시글 등록버튼 누를시 단일게시글 페이지로 redirect -> 새로고침시 게시글 중복등록 막기위함
    @PostMapping("/addPost")    //dto적용할것
    public String addPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        Post savedPost = postRepositoryV1.save(post);
        redirectAttributes.addAttribute("id", savedPost.getPostId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/post/{id}";
    }

    //게시글 수정
    @GetMapping("/post/{id}/editPost")
    public String editPost(@PathVariable Long id,Model model) {
        Post post = postRepositoryV1.findById(id);
        model.addAttribute("post", post);
        return "/board/editPost";
    }

    //게시글 수정버튼 누를 시 단일 게시글 페이지로 이동
    @PostMapping("/post/{id}/editPost")     //dto적용할것
    public String editPost(@PathVariable Long id, @ModelAttribute Post updateParam) {
        Post pastPost = postRepositoryV1.findById(updateParam.getPostId());
        postRepositoryV1.update(pastPost,updateParam);
        return "redirect:/board/post/{id}";
    }


    //테스트용(초기 데이터)
    @PostConstruct
    public void init() {
        postRepositoryV1.save(new Post("김현수", "테스트제목1", "테스트내용1"));
        postRepositoryV1.save(new Post("김현승", "테스트제목2", "테스트내용2"));
    }

}
