package nextpay.vn.blog.controller;

import nextpay.vn.blog.entity.model.Category;
import nextpay.vn.blog.exception.UnauthorizedException;
import nextpay.vn.blog.payload.ApiResponse;
import nextpay.vn.blog.payload.PagedResponse;
import nextpay.vn.blog.security.CurrentUser;
import nextpay.vn.blog.security.UserPrincipal;
import nextpay.vn.blog.service.CategoryService;
import nextpay.vn.blog.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PagedResponse<Category> getAllCategories(
            @RequestParam(name = "page", required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE) Integer size) {
        return categoryService.getAllCategories(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category,
                                                @CurrentUser UserPrincipal currentUser) {

        return categoryService.addCategory(category, currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(name = "id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable(name = "id") Long id,
                                                   @Valid @RequestBody Category category, @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return categoryService.updateCategory(id, category, currentUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser) throws UnauthorizedException {
        return categoryService.deleteCategory(id, currentUser);
    }

}
