package com.image.liteApi.domain.repository;

import com.image.liteApi.domain.entity.Image;
import com.image.liteApi.domain.enums.ImageExtension;
import com.image.liteApi.domain.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.image.liteApi.domain.repository.specs.GenericSpecs.conjunction;
import static com.image.liteApi.domain.repository.specs.ImageSpecs.extensionEqual;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

public interface ImageRespository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    // TODO: 25/01/2024 JPASpecification...

    /**
     * @param extension
     * @param query
     * @return SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND (NAME LIKE 'QUERY OR TAGS LIKE 'QUERY')
     */

    default List<Image> findByExtensionAndNameOrTagsLike
    (ImageExtension extension, String query) {

//        SELECT * FROM IMAGE WHERE 1 = 1
        Specification<Image> spec = where(conjunction());

        if (extension != null) {
//            AND EXTENSION = 'PNG'
            spec = spec.and(extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
//            AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
//            RIVER => %RI%
            spec = spec.and(anyOf(ImageSpecs.nameLike(query), ImageSpecs.tagsLike(query)));
        }

        return findAll(spec);
    }
}
