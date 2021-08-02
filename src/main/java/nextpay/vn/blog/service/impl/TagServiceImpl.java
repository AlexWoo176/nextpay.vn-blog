package nextpay.vn.blog.service.impl;

import nextpay.vn.blog.entity.model.Tag;
import nextpay.vn.blog.entity.role.RoleName;
import nextpay.vn.blog.exception.ResourceNotFoundException;
import nextpay.vn.blog.exception.UnauthorizedException;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.repository.TagRepository;
import nextpay.vn.blog.security.UserPrincipal;
import nextpay.vn.blog.service.TagService;
import nextpay.vn.blog.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public PagedResponse<Tag> getAllTags(int page, int size){
        Utils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Tag> tags = tagRepository.findAll(pageable);

        List<Tag> content = tags.getNumberOfElements() == 0 ? Collections.emptyList() : tags.getContent();

        return new PagedResponse<Tag>(content, tags.getNumber(), tags.getSize(), tags.getTotalElements(), tags.getTotalPages(), tags.isLast());
    }

    @Override
    public Tag getTag(Long id){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        return tag;
    }

    @Override
    public Tag addTag(Tag tag, UserPrincipal currentUser){
        Tag newTag =  tagRepository.save(tag);
        return newTag;
    }

    @Override
    public Tag updateTag(Long id, Tag newTag, UserPrincipal currentUser){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        if (tag.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){
            tag.setName(newTag.getName());
            Tag updatedTag = tagRepository.save(tag);
            return updatedTag;
        }
        ApiResponse apiResponse =new ApiResponse(Boolean.FALSE, "You don't have permission to edit this tag");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteTag(Long id, UserPrincipal currentUser){
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        if (tag.getCreatedBy().equals(currentUser.getId()) || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){
            tagRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted tag");
        }

        ApiResponse apiResponse =new ApiResponse(Boolean.FALSE, "You don't have permission to delete this tag");

        throw new UnauthorizedException(apiResponse);
    }
}

