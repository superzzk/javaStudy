package com.zzk.study.library.mapstruct;

import lombok.Data;
import org.junit.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class Demo {

    @Test
    public void demo() {
        Person entity = new Person();
        entity.setId("1");
        entity.setName("John");
        entity.setSex("male");

        PersonDTO dto = PersonMapper.INSTANCE.personToPersonDTO(entity);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        // ignore
        assertNull(dto.getSex());

        // change property name
        dto = PersonMapperWithPropertyName.INSTANCE.personToPersonDTO(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getNickName(), entity.getName());

        // defaultExpression
        entity.setId(null);
        dto = PersonMapper.INSTANCE.personToPersonDTO(entity);
        System.out.println(dto);
    }

    /**
     * 类嵌套
     */
    @Test
    public void givenArticle_whenMapper_thenProducesCorrectDto() {
        Article entity  = new Article();
        entity.setId(1);
        entity.setName("Mapstruct Mapping");
        Person author = new Person();
        author.setId("1");
        author.setName("John");
        entity.setAuthor(author);

        ArticleDTO articleDTO = ArticleMapper.INSTANCE.articleToArticleDto(entity);

        assertEquals(articleDTO.getId(), entity.getId());
        assertEquals(articleDTO.getName(), entity.getName());
        assertEquals(articleDTO.getAuthor().getName(), entity.getAuthor().getName());
    }

    @Test
    public void givenArticle_whenMapperWithUses_thenProducesCorrectDto() {

        Article entity  = new Article();
        entity.setId(1);
        entity.setName("Mapstruct Mapping");
        Person author = new Person();
        author.setId("1");
        author.setName("John");
        entity.setAuthor(author);

        ArticleDTO articleDTO = ArticleUsingPersonMapper.INSTANCE.articleToArticleDto(entity);

        assertEquals(articleDTO.getId(), entity.getId());
        assertEquals(articleDTO.getName(), entity.getName());
        assertEquals(articleDTO.getAuthor().getName(), entity.getAuthor().getName());
    }

    @Data
    public static class Article {
        private int id;
        private String name;
        private Person author;
    }

    @Data
    public static class ArticleDTO {
        private int id;
        private String name;
        private PersonDTO author;
    }

    @Data
    public static class Person {
        private String id;
        private String name;
        private String sex;
    }

    @Data
    public static class PersonDTO {
        private String id;
        private String name;
        private String nickName;
        private String sex;
    }

    @Mapper
    public interface PersonMapper {
        PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
        @Mapping(target = "id", source = "person.id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
        @Mapping(target = "sex", ignore = true)
        PersonDTO personToPersonDTO(Person person);
    }

    @Mapper
    public interface PersonMapperWithPropertyName {
        PersonMapperWithPropertyName INSTANCE = Mappers.getMapper(PersonMapperWithPropertyName.class);

        @Mapping(target = "nickName", source = "name")
        PersonDTO personToPersonDTO(Person person);
    }

    @Mapper
    public interface ArticleMapper {
        ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);
        ArticleDTO articleToArticleDto(Article article);
        default PersonDTO personToPersonDto(Person person) {
            return Mappers.getMapper(PersonMapper.class).personToPersonDTO(person);
        }
    }

    @Mapper(uses = PersonMapper.class)
    public interface ArticleUsingPersonMapper {
        ArticleUsingPersonMapper INSTANCE = Mappers.getMapper(ArticleUsingPersonMapper.class);
        ArticleDTO articleToArticleDto(Article article);
    }

}
