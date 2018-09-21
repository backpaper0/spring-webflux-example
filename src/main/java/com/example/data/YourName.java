package com.example.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class YourName {

    private final String name;

    public YourName(@JsonProperty("name") final String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
        final YourName other = getClass().cast(obj);
        return name.equals(other.name);
    }
}