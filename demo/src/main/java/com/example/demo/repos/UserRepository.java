package com.example.demo.repos;

import com.example.demo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    List<UserModel> findByIsDeleted(int isDeleted);
    UserModel findByIdAndIsDeleted(int id, int isDeleted);

    UserModel findByUsername(String username);
}
