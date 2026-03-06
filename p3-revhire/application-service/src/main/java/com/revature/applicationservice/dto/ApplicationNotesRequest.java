package com.revature.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ApplicationNotesRequest {

    @NotBlank(message = "Notes are required")
    @Size(max = 2000, message = "Notes cannot exceed 2000 characters")
    private String notes;

    public ApplicationNotesRequest() {
    }

    public ApplicationNotesRequest(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationNotesRequest that = (ApplicationNotesRequest) o;
        return Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notes);
    }

    @Override
    public String toString() {
        return "ApplicationNotesRequest{" +
                "notes='" + notes + '\'' +
                '}';
    }
}
