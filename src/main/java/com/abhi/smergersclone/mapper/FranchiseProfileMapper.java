package com.abhi.smergersclone.mapper;
import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;
import com.abhi.smergersclone.entity.FranchiseProfile;
import jakarta.persistence.Id;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

//@Mapper(componentModel = "spring")
//public interface FranchiseProfileMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "brandLogoPath", ignore = true)
//    @Mapping(target = "businessPhotosPaths", ignore = true)
//    @Mapping(target = "brochuresDocumentsPaths", ignore = true)
//    @Mapping(target = "proofOfBusinessPath", ignore = true)
//    @Mapping(target = "franchiseFormats", source = "franchiseFormats", qualifiedByName = "mapFormats")
//    FranchiseProfile toEntity(FranchiseProfileRequestDTO dto);
//
//    @Mapping(target = "franchiseFormats", source = "franchiseFormats", qualifiedByName = "mapFormatResponses")
//    FranchiseProfileResponseDTO toResponseDTO(FranchiseProfile entity);
//
//    // Format mapping methods
//    @Named("mapFormats")
//    default List<FranchiseProfile.FranchiseFormat> mapFormats(List<FranchiseProfileRequestDTO.FranchiseFormatDTO> dtos) {
//        if (dtos == null) return null;
//        return dtos.stream().map(this::toFormatEntity).toList();
//    }
//
//    @Named("mapFormatResponses")
//    default List<FranchiseProfileResponseDTO.FranchiseFormatDTO> mapFormatResponses(List<FranchiseProfile.FranchiseFormat> formats) {
//        if (formats == null) return null;
//        return formats.stream().map(this::toFormatResponseDTO).toList();
//    }
//
//    FranchiseProfile.FranchiseFormat toFormatEntity(FranchiseProfileRequestDTO.FranchiseFormatDTO dto);
//
//    FranchiseProfileResponseDTO.FranchiseFormatDTO toFormatResponseDTO(FranchiseProfile.FranchiseFormat entity);
//}

@Mapper(componentModel = "spring")
public interface FranchiseProfileMapper {


    @Mapping(target = "id", ignore = true) // Explicitly ignore ID
    @Mapping(target = "brandLogoPath", ignore = true)
    @Mapping(target = "businessPhotosPaths", ignore = true)
    @Mapping(target = "brochuresDocumentsPaths", ignore = true)
    @Mapping(target = "proofOfBusinessPath", ignore = true)
    FranchiseProfile toEntity(FranchiseProfileRequestDTO dto);
    // Explicitly declare the mapping methods
    default List<FranchiseProfile.FranchiseFormat> mapFranchiseFormats(List<FranchiseProfileRequestDTO.FranchiseFormatDTO> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(dto -> {
                    FranchiseProfile.FranchiseFormat format = new FranchiseProfile.FranchiseFormat();
                    format.setFormatName(dto.getFormatName());
                    format.setMinSqFt(dto.getMinSqFt());
                    format.setMaxSqFt(dto.getMaxSqFt());
                    // Map all other properties
                    return format;
                })
                .toList();
    }

    @Mapping(target = "franchiseFormats", source = "entity.franchiseFormats")
    FranchiseProfileResponseDTO toResponseDTO(FranchiseProfile entity);
}