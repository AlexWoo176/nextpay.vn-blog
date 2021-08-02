package nextpay.vn.blog.utils;

import nextpay.vn.blog.exception.BlogApiException;
import org.springframework.http.HttpStatus;

public class Utils {
    public static void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
        }

        if(size < 0) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
        }

        if(size > Constants.MAX_PAGE_SIZE) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + Constants.MAX_PAGE_SIZE);
        }
    }
}
