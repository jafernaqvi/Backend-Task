package net.grandcentrix.assessment.smartenergy.api.user;

import net.grandcentrix.assessment.smartenergy.domain.model.AppUser;

import java.util.Objects;
import java.util.UUID;

public class GetUserResponse {

    private UUID id;
    private String name;

    public static GetUserResponse of(AppUser user) {
        return new GetUserResponse(user.getId(), user.getUsername());
    }

    public GetUserResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetUserResponse that = (GetUserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
