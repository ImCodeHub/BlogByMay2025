package com.example.BlogByMay.Repository;

import com.example.BlogByMay.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommnetRepository extends JpaRepository<Comment, Long> {
}
