package com.gmail.voronovskyi.yaroslav.currencyexchange.repository;

import com.gmail.voronovskyi.yaroslav.currencyexchange.model.MonoBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonoBankRepository extends JpaRepository<MonoBank, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM monos WHERE date = ?")
    List<MonoBank> search(@Param("date") String date);
}
