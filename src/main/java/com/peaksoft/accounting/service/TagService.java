package com.peaksoft.accounting.service;

import com.peaksoft.accounting.api.payload.TagRequest;
import com.peaksoft.accounting.api.payload.TagResponse;
import com.peaksoft.accounting.db.entity.CompanyEntity;
import com.peaksoft.accounting.db.entity.TagEntity;
import com.peaksoft.accounting.db.repository.TagRepository;
import com.peaksoft.accounting.validation.exception.ValidationException;
import com.peaksoft.accounting.validation.exception.ValidationExceptionType;
import com.peaksoft.accounting.validation.validator.TagRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagRequestValidator tagRequestValidator;

    public TagResponse create(TagEntity tag, TagRequest tagRequest, CompanyEntity company) {
        tagRequestValidator.validate(tag, tagRequest);
        TagEntity tagEntity = mapToEntity(tagRequest);
        tagEntity.setCompany(company);
        tagRepository.save(tagEntity);
        return mapToResponse(tagEntity);
    }

    public TagResponse update(TagRequest tagRequest, long id,CompanyEntity company) {
        Optional<TagEntity> tag = tagRepository.findById(id);
        if (tag.isEmpty()) {
            throw new ValidationException(ValidationExceptionType.BAD_REQUEST);
        }
        mapToUpdate(tag.get(), tagRequest);
        tag.get().setCompany(company);
        return mapToResponse(tagRepository.save(tag.get()));
    }

    public TagResponse deleteById(long id) {
        TagEntity tag = tagRepository.findById(id).get();
        tagRepository.deleteById(id);
        return mapToResponse(tag);
    }

    public TagResponse findById(long id) {
        Optional<TagEntity> tag = tagRepository.findById(id);
        if (tag.isEmpty()){
            throw new ValidationException(ValidationExceptionType.TAG_NOT_FOUND);
        }
        return mapToResponse(
                tagRepository.findById(id).get());
    }

    public List<TagResponse> getAllTags(Long companyId){
        return map(tagRepository.findAllByCompany(companyId));
    }
    public List<TagResponse> getAll(Long companyId) {
        return map(tagRepository.getAll(companyId));
    }
    public List<TagResponse> searchByName(String tagName,Long companyId){
        return map(tagRepository.searchAllByNameTag(tagName,companyId));
    }
    //Request tag
    public TagEntity mapToEntity(TagRequest tagRequest) {
        TagEntity tag = new TagEntity();
        tag.setNameTag(tagRequest.getNameTag());
        tag.setDescription(tagRequest.getDescription());
        return tag;
    }

    //update tag
    public TagEntity mapToUpdate(TagEntity tag, TagRequest tagRequest) {
        tag.setNameTag(tagRequest.getNameTag());
        tag.setDescription(tagRequest.getDescription());
        tagRequestValidator.validate(tag,tagRequest);
        return tag;
    }
     //Response tag
    public TagResponse mapToResponse(TagEntity tag) {

        if (tag == null) {
            return null;
        }
        TagResponse tagResponse = new TagResponse();
        if (tag.getTag_id() != null) {
            tagResponse.setId(String.valueOf(tag.getTag_id()));
        }
        tagResponse.setNameTag(tag.getNameTag());
        tagResponse.setDescription(tag.getDescription());
        return tagResponse;
    }

    public List<TagResponse> map(List<TagEntity> allTags) {
        List<TagResponse> tagResponses = new ArrayList<>();
        for (TagEntity tag : allTags) {
           tagResponses.add(mapToResponse(tag));
        }
        return tagResponses;
    }
}
