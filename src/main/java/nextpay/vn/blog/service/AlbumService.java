package nextpay.vn.blog.service;

import nextpay.vn.blog.entity.model.Album;
import nextpay.vn.blog.payload.AlbumResponse;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.payload.request.AlbumRequest;
import nextpay.vn.blog.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface AlbumService {

    PagedResponse<AlbumResponse> getAllAlbums(int page, int size);

    ResponseEntity<Album> addAlbum(AlbumRequest albumRequest, UserPrincipal currentUser);

    ResponseEntity<Album> getAlbum(Long id);

    ResponseEntity<AlbumResponse> updateAlbum(Long id, AlbumRequest newAlbum, UserPrincipal currentUser);

    ResponseEntity<ApiResponse> deleteAlbum(Long id, UserPrincipal currentUser);

    PagedResponse<Album> getUserAlbums(String username, int page, int size);

}
