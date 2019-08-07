package fr.maileva.connect.payslip.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Emittor.
 */
@Entity
@Table(name = "emittor")
public class Emittor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Size(max = 48)
    @Pattern(regexp = "[a-zA-Z0-9 ]")
    @Column(name = "company_name", length = 48, nullable = false)
    private String companyName;

    @NotNull
    @Size(min = 14, max = 14)
    @Pattern(regexp = "[0-9]")
    @Column(name = "company_siret", length = 14, nullable = false)
    private String companySiret;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9.-_]")
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "can_emit")
    private Boolean canEmit;

    @Column(name = "can_emit_since")
    private String canEmitSince;

    @OneToOne
    @JoinColumn(unique = true)
    private Status lastStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("emittors")
    private UserApi createdBy;

    @ManyToOne
    @JsonIgnoreProperties("emittors")
    private UserApi modifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Emittor code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Emittor companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySiret() {
        return companySiret;
    }

    public Emittor companySiret(String companySiret) {
        this.companySiret = companySiret;
        return this;
    }

    public void setCompanySiret(String companySiret) {
        this.companySiret = companySiret;
    }

    public String getLogin() {
        return login;
    }

    public Emittor login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean isCanEmit() {
        return canEmit;
    }

    public Emittor canEmit(Boolean canEmit) {
        this.canEmit = canEmit;
        return this;
    }

    public void setCanEmit(Boolean canEmit) {
        this.canEmit = canEmit;
    }

    public String getCanEmitSince() {
        return canEmitSince;
    }

    public Emittor canEmitSince(String canEmitSince) {
        this.canEmitSince = canEmitSince;
        return this;
    }

    public void setCanEmitSince(String canEmitSince) {
        this.canEmitSince = canEmitSince;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public Emittor lastStatus(Status status) {
        this.lastStatus = status;
        return this;
    }

    public void setLastStatus(Status status) {
        this.lastStatus = status;
    }

    public UserApi getCreatedBy() {
        return createdBy;
    }

    public Emittor createdBy(UserApi userApi) {
        this.createdBy = userApi;
        return this;
    }

    public void setCreatedBy(UserApi userApi) {
        this.createdBy = userApi;
    }

    public UserApi getModifiedBy() {
        return modifiedBy;
    }

    public Emittor modifiedBy(UserApi userApi) {
        this.modifiedBy = userApi;
        return this;
    }

    public void setModifiedBy(UserApi userApi) {
        this.modifiedBy = userApi;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emittor)) {
            return false;
        }
        return id != null && id.equals(((Emittor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Emittor{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", companySiret='" + getCompanySiret() + "'" +
            ", login='" + getLogin() + "'" +
            ", canEmit='" + isCanEmit() + "'" +
            ", canEmitSince='" + getCanEmitSince() + "'" +
            "}";
    }
}
