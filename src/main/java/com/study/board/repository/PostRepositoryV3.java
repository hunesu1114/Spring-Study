package com.study.board.repository;

import com.study.board.dto.PostDto;
import com.study.board.entity.Post;
import com.study.board.paging.Pagination;

import java.util.List;

public interface PostRepositoryV3 {

    public Long save(PostDto dto);

    public Post findById(Long id);

    public List<Post> findAll();

    public List<Post> pagedFindAll(Pagination pagination);

    public int postCnt();

    public Post update(Post post, PostDto updateParam);

    public void delete(Long id);

    public void clear();


}
