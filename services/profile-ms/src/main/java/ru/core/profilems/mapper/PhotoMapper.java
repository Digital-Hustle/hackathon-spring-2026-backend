package ru.core.profilems.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.core.profilems.config.BaseMapperConfig;
import ru.core.profilems.dto.PhotoMetaInfoDto;
import ru.core.profilems.model.PhotoWithUrl;
import ru.core.profilems.model.entity.PhotoMetaInfo;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface PhotoMapper {

    @Mapping(target = "url", ignore = true)
    PhotoMetaInfoDto convert(PhotoMetaInfo source);

    @Mapping(target = "extension", ignore = true)
    @Mapping(target = "fileSize", ignore = true)
    @Mapping(target = "contentType", ignore = true)
    PhotoMetaInfoDto convert(PhotoWithUrl source);

    List<PhotoMetaInfoDto> convert(List<PhotoWithUrl> source);

    default Page<PhotoMetaInfoDto> convert(Page<PhotoWithUrl> source) {
        return source.map(this::convert);
    }
}
