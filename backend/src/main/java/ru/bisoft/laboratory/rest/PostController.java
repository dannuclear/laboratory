package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Post;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{post}")
    public ResponseEntity<Post> findOne(@PathVariable Post post) {
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Post>> findAll(Pageable pageable) {
        Page<Post> page = postService.findAll(pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody Post newPost) {
        newPost.setId(null);
        postService.save(newPost);
    }

    @PutMapping("/{post}")
    public void save(@PathVariable Post post, @RequestBody Post newPost) {
        postService.save(newPost);
    }

    @DeleteMapping("/{post}")
    public void delete(@PathVariable Post post) {
        postService.delete(post);
    }
}
