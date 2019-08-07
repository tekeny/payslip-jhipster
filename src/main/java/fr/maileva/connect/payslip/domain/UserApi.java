package fr.maileva.connect.payslip.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserApi.
 */
@Entity
@Table(name = "user_api")
public class UserApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "date")
    private String date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserApi userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public UserApi userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getDate() {
        return date;
    }

    public UserApi date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApi)) {
            return false;
        }
        return id != null && id.equals(((UserApi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserApi{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
