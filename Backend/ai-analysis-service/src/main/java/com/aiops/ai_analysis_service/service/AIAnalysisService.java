package com.aiops.ai_analysis_service.service;

import com.aiops.ai_analysis_service.dto.AIAnalysisResponse;
import com.aiops.ai_analysis_service.dto.IncidentEvent;

public interface AIAnalysisService {

    AIAnalysisResponse analyzeIncident(
            IncidentEvent event);
}