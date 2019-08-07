package fr.maileva.connect.payslip.domain;

import javax.persistence.*;

import java.io.Serializable;

import fr.maileva.connect.payslip.domain.enumeration.StatusEnum;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private StatusEnum code;

    @Column(name = "date")
    private String date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getCode() {
        return code;
    }

    public Status code(StatusEnum code) {
        this.code = code;
        return this;
    }

    public void setCode(StatusEnum code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public Status date(String date) {
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
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
