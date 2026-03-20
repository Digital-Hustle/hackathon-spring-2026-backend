package ru.core.profilems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import ru.core.profilems.dto.PhotoMetaInfoDto;

import java.util.UUID;

@Tag(name = "Profile API", description = "Profile endpoints")
@RequestMapping("api/v1/profile/{profileId}/photo")
public interface ProfilePhotoController {

    @Operation(
            summary = "Get profile photo by profile ID",
            description = "Returns photo metadata and URL for the specified profile"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved photo"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Profile not found or photo does not exist"
            )
    })
    @GetMapping
    PhotoMetaInfoDto getByProfileId(@PathVariable UUID profileId);

    @Operation(
            summary = "Upload photo for profile"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Photo successfully uploaded"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid photo format or size"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Profile not found"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PhotoMetaInfoDto uploadPhoto(@PathVariable UUID profileId, MultipartFile photo);

    @Operation(
            summary = "Update profile photo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Photo successfully updated"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid photo format or size"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Profile not found or photo does not exist"
            )
    })
    @PutMapping
    PhotoMetaInfoDto updatePhoto(@PathVariable UUID profileId, MultipartFile photo);

    @Operation(
            summary = "Delete profile photo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Photo successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Profile not found or photo does not exist"
            )
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhoto(@PathVariable UUID profileId);
}
