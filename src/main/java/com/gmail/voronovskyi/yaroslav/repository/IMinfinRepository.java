package com.gmail.voronovskyi.yaroslav.repository;

import com.gmail.voronovskyi.yaroslav.model.Minfin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMinfinRepository extends JpaRepository<Minfin, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM minfin WHERE date = ?")
    List<Minfin> search(@Param("date") String date);
}
