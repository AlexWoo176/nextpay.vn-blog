package nextpay.vn.blog.service;

import nextpay.vn.blog.entity.model.Comment;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.CommentRequest;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.security.UserPrincipal;

public interface CommentService {

    PagedResponse<Comment> getAllComments(Long postId, int page, int size);

    Comment addComment(CommentRequest commentRequest, Long postId, UserPrincipal currentUser);

    Comment getComment(Long postId, Long id);

    Comment updateComment(Long postId, Long id, CommentRequest commentRequest, UserPrincipal currentUser);

    ApiResponse deleteComment(Long postId, Long id, UserPrincipal currentUser);

}
