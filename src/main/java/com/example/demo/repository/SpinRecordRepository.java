package com.example.demo.repository;

import com.example.demo.entity.SpinRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpinRecordRepository extends JpaRepository<SpinRecord, Integer> {

    // 依 userId 查出該玩家的所有旋轉紀錄（給「查歷史紀錄」API 用）
    // 方法名稱照 Spring Data 的命名規則寫，Spring 會自動幫你產生 SQL
    List<SpinRecord> findByUserId(int userId);
}
