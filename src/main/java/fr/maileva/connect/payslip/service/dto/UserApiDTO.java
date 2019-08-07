package fr.maileva.connect.payslip.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.maileva.connect.payslip.domain.UserApi} entity.
 */
public class UserApiDTO implements Serializable {

    private Long id;

    private String userId;

    private String userLogin;

    private String date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserApiDTO userApiDTO = (UserApiDTO) o;
        if (userApiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userApiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserApiDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
