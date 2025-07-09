package com.abhi.smergersclone.mapper;
import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
import com.abhi.smergersclone.entity.MemberProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberProfileMapper {

    MemberProfileMapper INSTANCE = Mappers.getMapper(MemberProfileMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "companyLogoPath", ignore = true)
    @Mapping(target = "corporateProfilePath", ignore = true)
    @Mapping(target = "proofOfBusinessPath", ignore = true)
    @Mapping(target = "introductionCredits", ignore = true)
    MemberProfile toEntity(MemberProfileRequestDTO dto);

    MemberProfileResponseDTO toResponseDTO(MemberProfile entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(MemberProfileRequestDTO dto, @MappingTarget MemberProfile entity);
}