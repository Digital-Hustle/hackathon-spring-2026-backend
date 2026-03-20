package ru.core.profilems.mapper;

import org.mapstruct.Mapper;
import ru.core.profilems.config.BaseMapperConfig;
import ru.core.profilems.dto.request.SearchParametersRq;
import ru.core.profilems.model.SearchFilter;

@Mapper(config = BaseMapperConfig.class)
public interface RequestParamsMapper {
    SearchFilter convert(SearchParametersRq eventsFilter);
}
