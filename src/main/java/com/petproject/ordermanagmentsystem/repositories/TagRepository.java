package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
