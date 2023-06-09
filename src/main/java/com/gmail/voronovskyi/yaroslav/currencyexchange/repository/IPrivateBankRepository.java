package com.gmail.voronovskyi.yaroslav.currencyexchange.repository;

import com.gmail.voronovskyi.yaroslav.currencyexchange.model.PrivatBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPrivateBankRepository extends JpaRepository<PrivatBank, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM privats WHERE date = ?")
    List<PrivatBank> search(@Param("date") String date);
}
