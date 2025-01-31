package com.example.demo.repos;

import com.example.demo.models.ChannelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<ChannelModel, Integer> {
}
