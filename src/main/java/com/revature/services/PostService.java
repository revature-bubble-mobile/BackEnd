package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> getById(int psid);
    Post addPost(Post post);

    List<Post> getAllPostsPaginated(int pageNumber);
    List<Post> getAllPosts();


    Profile likePost(Profile profile, Post post);
    int likeDelete(Profile profile, Post post);
    int likeGet(Post post);
    Profile likeFindByID(Profile profile, Post post);
}
