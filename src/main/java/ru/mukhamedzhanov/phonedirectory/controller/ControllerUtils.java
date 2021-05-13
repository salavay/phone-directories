package ru.mukhamedzhanov.phonedirectory.controller;

public abstract class ControllerUtils {
    public static Long getLong(final String stringViewNumber) {
        final long number;
        try {
            number = Long.parseLong(stringViewNumber);
        } catch (final NumberFormatException ignored) {
            return null;
        }
        return number;
    }
}
