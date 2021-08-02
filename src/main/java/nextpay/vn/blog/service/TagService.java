package nextpay.vn.blog.service;

import nextpay.vn.blog.entity.model.Tag;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.security.UserPrincipal;

public interface TagService {

    PagedResponse<Tag> getAllTags(int page, int size);

    Tag getTag(Long id);

    Tag addTag(Tag tag, UserPrincipal currentUser);

    Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser);

    ApiResponse deleteTag(Long id, UserPrincipal currentUser);
}
