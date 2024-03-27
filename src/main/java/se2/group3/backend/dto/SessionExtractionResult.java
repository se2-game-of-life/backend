package se2.group3.backend.dto;

import java.util.Map;

public record SessionExtractionResult(String sessionID, Map<String, Object> sessionAttributes) {}
