package com.example.readwritetable.repository;

import com.example.readwritetable.domain.WriteNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteNodeRepo extends JpaRepository<WriteNode, Long> {

}
