package com.koscom.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;


    @AfterEach // test 시작 후에 수행되는 것
    void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    void 게시글저장_불러오기1() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";
        
        // 저장
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();
        
        //불러와서 auto increment 여부 확인
        System.out.println(result.get(0).getId());
        System.out.println(result.get(0).getTitle());

        assertThat(result.get(0).getTitle()).isEqualTo(title);
        System.out.println("-------------------");
        assertThat(result.get(0).getContent()).isEqualTo(content);


    }

    @Test
    void 게시글저장_불러오기2() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";

        // 저장
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();

        //불러와서 auto increment 여부 확인
        System.out.println(result.size());
        assertThat(result).hasSize(1);


    }

    @Test
    void 게시글저장_불러오기3() {
        String title = "테스트 타이틀";
        String content = "테스트 본문";

        // 저장
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());

        List<Posts> result = postsRepository.findAll();

        //불러와서 auto increment 여부 확인
        System.out.println(result.size());
        assertThat(result).hasSize(1);


    }

    // LocalDate : 일자 (2021.10.24)
    // LocalDateTime : 일시까지 (2021.10.24. 10:30:011.9999)

    @Test
    public void 등록시간_수정시간이_저장된다(){

        LocalDateTime now = LocalDateTime.of(2020, 10, 24, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        // 지금 등록한 일시와 수정일시는 최소한 2020년 10월보다는 뒤에 있을 것이다

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>> createDate = " + posts.getCreatedDate()+", modifiedDate = " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }
}
