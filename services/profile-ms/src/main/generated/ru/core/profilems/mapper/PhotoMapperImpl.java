package ru.core.profilems.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.core.profilems.dto.PhotoMetaInfoDto;
import ru.core.profilems.model.PhotoWithUrl;
import ru.core.profilems.model.entity.PhotoMetaInfo;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-15T20:19:59+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PhotoMapperImpl implements PhotoMapper {

    @Override
    public PhotoMetaInfoDto convert(PhotoMetaInfo source) {
        if ( source == null ) {
            return null;
        }

        PhotoMetaInfoDto.PhotoMetaInfoDtoBuilder photoMetaInfoDto = PhotoMetaInfoDto.builder();

        photoMetaInfoDto.id( source.getId() );
        photoMetaInfoDto.originalFileName( source.getOriginalFileName() );
        photoMetaInfoDto.extension( source.getExtension() );
        photoMetaInfoDto.fileSize( source.getFileSize() );
        photoMetaInfoDto.contentType( source.getContentType() );

        return photoMetaInfoDto.build();
    }

    @Override
    public PhotoMetaInfoDto convert(PhotoWithUrl source) {
        if ( source == null ) {
            return null;
        }

        PhotoMetaInfoDto.PhotoMetaInfoDtoBuilder photoMetaInfoDto = PhotoMetaInfoDto.builder();

        photoMetaInfoDto.id( source.id() );
        photoMetaInfoDto.originalFileName( source.originalFileName() );
        photoMetaInfoDto.url( source.url() );

        return photoMetaInfoDto.build();
    }

    @Override
    public List<PhotoMetaInfoDto> convert(List<PhotoWithUrl> source) {
        if ( source == null ) {
            return null;
        }

        List<PhotoMetaInfoDto> list = new ArrayList<PhotoMetaInfoDto>( source.size() );
        for ( PhotoWithUrl photoWithUrl : source ) {
            list.add( convert( photoWithUrl ) );
        }

        return list;
    }
}
