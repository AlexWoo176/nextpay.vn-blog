package nextpay.vn.blog.service.impl;

import nextpay.vn.blog.entity.model.Album;
import nextpay.vn.blog.entity.role.RoleName;
import nextpay.vn.blog.entity.user.User;
import nextpay.vn.blog.exception.BlogApiException;
import nextpay.vn.blog.exception.ResourceNotFoundException;
import nextpay.vn.blog.payload.AlbumResponse;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.payload.request.AlbumRequest;
import nextpay.vn.blog.repository.AlbumRepository;
import nextpay.vn.blog.repository.UserRepository;
import nextpay.vn.blog.security.UserPrincipal;
import nextpay.vn.blog.service.AlbumService;
import nextpay.vn.blog.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static nextpay.vn.blog.utils.Constants.ID;

@Service
public class AlbumServiceImpl implements AlbumService {
    private static final String CREATED_AT = "createdAt";

    private static final String ALBUM_STR = "Album";

    private static final String USER_STR = "User";

    private static final String USERNAME_STR = "username";

    private static final String YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION = "You don't have permission to make this operation";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PagedResponse<AlbumResponse> getAllAlbums(int page, int size){
        Utils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Album> albums = albumRepository.findAll(pageable);

        if (albums.getNumberOfElements() == 0){
            return new PagedResponse<>(Collections.emptyList(), albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
        }

        List<AlbumResponse> albumResponses = Arrays.asList(modelMapper.map(albums.getContent(), AlbumResponse[].class));

        return new PagedResponse<>(albumResponses, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
    }

    @Override
    public ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser){
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new ResourceNotFoundException(USER_STR, USERNAME_STR, currentUser.getUsername()));

        Album album = new Album();

        modelMapper.map(albumRequest, album);

        album.setUser(user);
        Album newAlbum =  albumRepository.save(album);
        return new ResponseEntity<>(newAlbum, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Album> getAlbum(Long id){
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser){
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new ResourceNotFoundException(USER_STR, USERNAME_STR, currentUser.getUsername()));
        if (album.getUser().getId().equals(user.getId()) || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){
            album.setTitle(newAlbum.getTitle());
            Album updatedAlbum = albumRepository.save(album);

            AlbumResponse albumResponse = new AlbumResponse();

            modelMapper.map(updatedAlbum, albumResponse);

            return new ResponseEntity<>(albumResponse, HttpStatus.OK);
        }

        throw new BlogApiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser){
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ALBUM_STR, ID, id));
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new ResourceNotFoundException(USER_STR, USERNAME_STR, currentUser.getUsername()));
        if (album.getUser().getId().equals(user.getId()) || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){
            albumRepository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "You successfully deleted album"), HttpStatus.OK);
        }

        throw new BlogApiException(HttpStatus.UNAUTHORIZED, YOU_DON_T_HAVE_PERMISSION_TO_MAKE_THIS_OPERATION);
    }

    @Override
    public PagedResponse<Album> getUserAlbums(String username, int page, int size){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(USER_STR, USERNAME_STR, username));
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Album> albums = albumRepository.findByCreatedBy(user.getId(), pageable);

        List<Album> content = albums.getNumberOfElements() > 0 ? albums.getContent() : Collections.emptyList();

        return new PagedResponse<>(content, albums.getNumber(), albums.getSize(), albums.getTotalElements(), albums.getTotalPages(), albums.isLast());
    }
}
