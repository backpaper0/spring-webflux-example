package com.example.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Hello {

    private final String message;

    public Hello(@JsonProperty("message") final String message) {
        this.message = Objects.requireNonNull(message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass() != getClass()) {
            return false;
        }
        final Hello other = getClass().cast(obj);
        return message.equals(other.message);
    }
}