package pl.kurs.finaltesttest.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER("user"),
    PATIENT("patient"),
    ADMIN("admin"),
    DOCTOR("doctor");

    public final String label;
}
