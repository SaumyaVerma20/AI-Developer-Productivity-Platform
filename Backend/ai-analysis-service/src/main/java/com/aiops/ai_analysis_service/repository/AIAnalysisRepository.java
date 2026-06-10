package com.aiops.ai_analysis_service.repository;

import com.aiops.ai_analysis_service.entity.AIAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIAnalysisRepository
        extends JpaRepository<AIAnalysis, Long> {

}