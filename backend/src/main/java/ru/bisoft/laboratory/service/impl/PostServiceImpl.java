package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Post;
import ru.bisoft.laboratory.repository.PostRepository;
import ru.bisoft.laboratory.service.PostService;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('POST_ADMIN')")
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @PreAuthorize("hasAuthority('POST_WRITE') or hasRole('POST_ADMIN')")
    public Post create() {
        log.info("Создаем должность");
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('POST_READ') or hasRole('POST_ADMIN')")
    public Post findById(Integer id) {
        log.info("Извлекаем должность по id {}", id);
        return postRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('POST_READ') or hasRole('POST_ADMIN')")
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('POST_WRITE') or hasRole('POST_ADMIN')")
    public Post save(Post entity) {
        return postRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('POST_WRITE') or hasRole('POST_ADMIN')")
    public void delete(Post entity) {
        postRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('POST_WRITE') or hasRole('POST_ADMIN')")
    public Iterable<Post> saveAll(Iterable<Post> entities) {
        return postRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('POST_READ') or hasRole('POST_ADMIN')")
    public Page<Post> findByString(String value, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }
}
