package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController                       // to map controller layer
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

   // public PostController(PostService postService) {
     //   this.postService = postService;
   // }

   @Autowired
    private ModelMapper model;

    //http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN')")  // Only as a ADMIN if u login,this/create will work.---------------------

    //SPRING VALIDATION-----------------------------------------------------------------------------------------
    // @Valid is used to indicate that a field or object should be validated.
    //The validation will happen in controller layer by using this annotation.----------------------------------
    @PostMapping("/test")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result,Model model) {
        if( result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().
                    getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);


        model.addAttribute("msg","Record is saved");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

//    @GetMapping       // Get all records in LIST Format -----------------------------------------------------
//    public List<PostDto> getAllPosts(){
//        List<PostDto> postDtos = postService.getAllPosts();
//        return postDtos;
//    }

    // get post/data by id ----------------------------------------------------------------------------------
    //Path parameter-- {id} &  If there's a ? mark then, it's a Query parameter-- {id}

    //http://localhost:8080/api/post/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){

            PostDto dto = postService.getPostById(id);

            //return new ResponseEntity<>(dto, HttpStatus.OK);
                            // OR...
            return ResponseEntity.ok(postService.getPostById(id));
        }

        //UPDATE Records/Post -------------------------------------------------------------------------------
        //http://localhost:8080/api/post/{id}
        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<?> updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id) {
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
        }

    //DELETE Records/Post ------------------------------------------------------------------------------------
    //http://localhost:8080/api/post/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post is Deleted", HttpStatus.OK);
    }

    //Pagination Concept -------------------------------------------------------------------------------------
    //http://localhost:8080/api/post?pageNo=0&pageSize=3&sortBy=title&sortDir=asc
    @GetMapping                            // Get all the records.
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "id",required = false) String sortDir) {

        PostResponse postResponse = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }

}

//To Join 2 tables,there should be Foreign key & Primary key in the table.
//post is map to the comment.




