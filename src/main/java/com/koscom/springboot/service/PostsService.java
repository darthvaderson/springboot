package com.koscom.springboot.service;

import com.koscom.springboot.domain.posts.Posts;
import com.koscom.springboot.domain.posts.PostsRepository;
import com.koscom.springboot.web.dto.posts.PostsListResponseDto;
import com.koscom.springboot.web.dto.posts.PostsResponseDto;
import com.koscom.springboot.web.dto.posts.PostsSaveRequestDto;
import com.koscom.springboot.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final로 선언된 필드들은 생성자 항목으로 자동 포함시켜서 생성자 생성 -> autowired 도 시켜줌
@Service // spring bean 등록 & service 클래스 선언
public class PostsService {

    private final PostsRepository postsRepository;

    //등록
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 없습니다. id=" +id));
        entity.update(dto.getTitle(), dto.getContent());

        //DB에 저장하는 코드가 따로 없음. Transactional 어노테이션 있으면 알아서 저장해줌. : Dirty Checking
        // entity 처음 값과 트랜잭션 끝나는 시점의 값이 다르면 DB에 업데이트 쿼리 날린다.
        // Object Relational Mapping 기술의 핵심

        return entity.getId();
    }

    //조회
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 없습니다. id=" +id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" +id));
        postsRepository.delete(posts);
    }
}
