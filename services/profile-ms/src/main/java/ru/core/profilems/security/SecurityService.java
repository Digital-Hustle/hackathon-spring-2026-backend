package ru.core.profilems.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.core.profilems.exception.exception.AccessDeniedException;
import ru.core.profilems.repository.ProfileRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {

    private final ProfileRepository profileRepository;

    public UUID getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof UserAuthentication userAuthentication)) {
            throw new AccessDeniedException("No authentication found");
        }

        return userAuthentication.getUserId();
    }

    public String getCurrentUserRole() {
        UserAuthentication auth = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getRole() : null;
    }

    public boolean isProfileOwner(UUID profileId) {
        UUID currentUserId = getCurrentUserId();

        if (currentUserId == null) {
            return false;
        }

        return currentUserId.equals(profileId);
    }

    public boolean isAdmin() {
        UserAuthentication auth = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return auth != null && "ADMIN".equals(auth.getRole());
    }

    public boolean canAccessProfile(UUID profileId) {
        return isProfileOwner(profileId) || isAdmin();
    }

    public boolean hasAnyRole(String... roles) {
        String currentRole = getCurrentUserRole();

        if (currentRole == null) {
            return false;
        }

        for (String role : roles) {
            if (currentRole.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
