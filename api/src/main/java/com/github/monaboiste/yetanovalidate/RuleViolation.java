package com.github.monaboiste.yetanovalidate;

import lombok.Builder;

@Builder
public record RuleViolation(String code, String field, String message) {
}