package com.zain_discoveries.com.zain_discoveries.clothing_website.repository;

import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Category findByName(String name);

    @Query("SELECT c from category c WHERE c.name=:name AND c.parentCategory.name=:parentCategoryName")
    public Category findByNameAndParant(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}
