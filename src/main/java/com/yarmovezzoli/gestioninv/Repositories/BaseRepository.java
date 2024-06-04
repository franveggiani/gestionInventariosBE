package com.yarmovezzoli.gestioninv.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.yarmovezzoli.gestioninv.Entities.Base;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository <E extends Base, ID extends Serializable> extends JpaRepository<E, ID> {

}
