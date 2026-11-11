package uz.pdp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dto.PostDTO;
import uz.pdp.entity.AuthUser;
import uz.pdp.entity.Post;
import uz.pdp.repository.AuthUserRepository;
import uz.pdp.repository.PostRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;
    private final AuthUserRepository authUserRepository;

    public PostController(PostRepository postRepository, AuthUserRepository authUserRepository) {
        this.postRepository = postRepository;
        this.authUserRepository = authUserRepository;
    }


    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post yaratildi")
    })
    public ResponseEntity<Void> createPost(@RequestBody PostDTO dto, Principal principal) {

        Optional<AuthUser> optional = authUserRepository.findAuthUserByUsername(principal.getName());

        Post post = Post.builder()
                .title(dto.title())
                .content(dto.content())
                .userId(optional.get().getId())
                .build();
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/my")
    public ResponseEntity<List<Post>> getUsersPosts(Principal principal) {
        AuthUser authUser = authUserRepository.findAuthUserByUsername(principal.getName()).get();
        List<Post> posts = postRepository.findPostsByUserId(authUser.getId());
        return ResponseEntity.ok(posts);
    }


    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postRepository.findAll());
    }


    @GetMapping("/{id}")
    @Operation(summary = "Post by id", description = "Bu API id orqali postni qaytaradi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marxamat post ni koring", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Post.class))
            })
            ,
            @ApiResponse(responseCode = "404", description = "Post Not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RuntimeException.class))
            })
    })
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        Optional<Post> optional = postRepository.findById(id);
        Post post = optional.orElseThrow(() -> new RuntimeException("Post with id " + id + " not found."));
        return ResponseEntity.ok(post);
    }



}
