package com.eledevo.integration.auditing;


import com.eledevo.integration.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    /**
     * Lấy thông tin về người dùng hiện tại đang xác thực trong ứng dụng.
     *
     * @return Optional chứa mã nhận dạng (ID) của người dùng nếu đang xác thực,
     * ngược lại trả về Optional.empty().
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        // Nếu không có thông tin xác thực hoặc là xác thực ẩn danh
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        // Chuyển đổi thông tin xác thực về đối tượng User
        User userPrincipal = (User) authentication.getPrincipal();

        // Trả về mã nhận dạng (ID) của người dùng
        return Optional.ofNullable(userPrincipal.getId());
    }
}
