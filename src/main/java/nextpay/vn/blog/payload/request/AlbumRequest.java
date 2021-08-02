package nextpay.vn.blog.payload.request;

import nextpay.vn.blog.entity.model.Photo;
import nextpay.vn.blog.entity.user.User;
import nextpay.vn.blog.payload.UserDateAuditPayload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumRequest extends UserDateAuditPayload {

    private Long id;

    private String title;

    private User user;

    private List<Photo> photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Photo> getPhoto() {

        return photo == null ? null : new ArrayList<>(photo);
    }

    public void setPhoto(List<Photo> photo) {

        if (photo == null) {
            this.photo = null;
        } else {
            this.photo = Collections.unmodifiableList(photo);
        }
    }
}
