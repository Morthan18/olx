package com.snokant.projekt.Repository;

import com.snokant.projekt.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
