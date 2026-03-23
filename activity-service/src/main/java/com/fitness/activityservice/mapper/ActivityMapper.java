package com.fitness.activityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;


@Mapper(componentModel = "Spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityMapper {
		
		@Mapping(target = "id", ignore =  true)
		@Mapping(target = "createdAt", ignore = true)
		@Mapping(target =  "updatedAt", ignore = true)
		Activity requestDtoToEntity(ActivityRequest activityRequest);
		
		
		ActivityResponse entitytoResponseDto (Activity activity);
		
		@Mapping(target = "id", ignore =  true)
		@Mapping(target = "createdAt", ignore = true)
		@Mapping(target = "updatedAt", ignore = true)		 
		Activity updateEntity (@MappingTarget Activity fetchedActivity, ActivityRequest activityRequest);

}
