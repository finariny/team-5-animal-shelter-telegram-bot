package com.example.team5animalsheltertelegrambot.timer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@RequiredArgsConstructor
public enum ProbationType {
    SUCCESS(0),
    DEADLINE_30(30),
    DEADLINE_44(44),
    DEADLINE_60(60);

    private final Integer id;

    @Nullable
    public static ProbationType fromId(Integer id) {
        for (ProbationType at : ProbationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}