package com.issoft.cinemaapplication.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String devMessage;
    private final String userMessage;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(this.devMessage, that.devMessage) && Objects.equals(this.userMessage, that.userMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.devMessage, this.userMessage);
    }
}
