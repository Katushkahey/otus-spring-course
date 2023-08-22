package com.homework.spring.mapper;

import com.homework.spring.dto.Author;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    private ModelMapper modelMapper = getModelMapper();

    public Author toDto(com.homework.spring.entity.Author author, List<String> books) {
        Author authorDto = new Author();
        modelMapper.map(author, authorDto);
        authorDto.setBooks(books);
        return authorDto;
    }

    public com.homework.spring.entity.Author toEntity(Author author) {
        com.homework.spring.entity.Author authorEntity = new com.homework.spring.entity.Author();
        modelMapper.map(author, authorEntity);
        return authorEntity;
    }

    private ModelMapper getModelMapper() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<com.homework.spring.entity.Author, Author>() {
            @Override
            protected void configure() {
                skip().setBooks(null);
            }
        });

        return modelMapper;
    }
}
