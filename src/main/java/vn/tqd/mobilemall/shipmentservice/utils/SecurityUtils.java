package vn.tqd.mobilemall.shipmentservice.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import vn.tqd.mobilemall.shipmentservice.exception.ResourceNotFoundException;


public class SecurityUtils {

    /**
     * Lấy ID hoặc Username của user đang đăng nhập.
     * Lưu ý: Tùy vào cách bạn cấu hình UserDetails trong module User,
     * hàm này sẽ trả về username (email) hoặc userId.
     */
    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResourceNotFoundException("Người dùng chưa đăng nhập hoặc phiên làm việc hết hạn");
        }

        Object principal = authentication.getPrincipal();

        // Trường hợp 1: Principal là UserDetails (Thường gặp)
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        // Trường hợp 2: Principal là String (Username/Principal name)
        if (principal instanceof String) {
            return (String) principal;
        }

        throw new ResourceNotFoundException("Không xác định được thông tin người dùng");
    }
}