package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {


    List<Post> findPostsByUserId(Integer userId);


}