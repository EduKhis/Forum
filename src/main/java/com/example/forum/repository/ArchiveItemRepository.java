package com.example.forum.repository;

import com.example.forum.model.ArchiveItem;
import com.example.forum.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveItemRepository extends JpaRepository<ArchiveItem, Long> {

    boolean existsByMessage (Message message);
}
