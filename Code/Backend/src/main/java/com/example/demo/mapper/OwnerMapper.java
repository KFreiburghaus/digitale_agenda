package com.example.demo.mapper;

import com.example.demo.dto.OwnerDto;
import com.example.demo.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerMapper INSTANCE = Mappers.getMapper(OwnerMapper.class);

    @Mapping(source = "vorname", target = "vorname")
    @Mapping(source = "nachname", target = "nachname")
    @Mapping(source = "firma", target = "firma")
    @Mapping(source = "email", target = "email")
    OwnerDto ownerToOwnerDto(Owner owner);
}
