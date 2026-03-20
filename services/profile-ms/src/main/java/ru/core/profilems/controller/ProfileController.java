package ru.core.profilems.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.core.profilems.dto.ProfileDto;
import ru.core.profilems.dto.request.SearchParametersRq;
import ru.core.profilems.dto.response.PageRs;
import ru.core.profilems.validation.OnCreate;
import ru.core.profilems.validation.OnUpdate;

import java.util.UUID;

@Tag(name = "Profile API", description = "Profile endpoints")
@RequestMapping("api/v1/profile")
public interface ProfileController {

    @Operation(summary = "Receive all profiles")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile")
    )
    @GetMapping
    ResponseEntity<PageRs<ProfileDto>> getProfiles(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size
    );

    @Operation(summary = "Receive profiles by query")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile")
    )
    @GetMapping("/search")
    ResponseEntity<PageRs<ProfileDto>> searchProfiles(@Valid SearchParametersRq searchParametersRq);

    @Operation(summary = "Receive profile and its Tasks by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @GetMapping("/{profileId}")
    ResponseEntity<ProfileDto> getProfileById(@PathVariable UUID profileId);

    @Operation(summary = "Create new profile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created profile"),
            @ApiResponse(responseCode = "400", description = "ID should not be specified"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ProfileDto> createProfile(@RequestBody @Validated(OnCreate.class) ProfileDto profileDto);

    @Operation(summary = "Update profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated profile"),
            @ApiResponse(responseCode = "400", description = "ID mismatch"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PutMapping("/{profileId}")
    ResponseEntity<ProfileDto> updateProfile(
            @PathVariable UUID profileId,
            @RequestBody @Validated(OnUpdate.class) ProfileDto profileDto
    );

    @Operation(summary = "Update profile")
    @ApiResponses(
            @ApiResponse(responseCode = "204", description = "Successfully deleted profile")
    )
    @DeleteMapping("/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProfile(@PathVariable UUID profileId);
}
