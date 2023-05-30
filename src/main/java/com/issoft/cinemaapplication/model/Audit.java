package com.issoft.cinemaapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "audits")
public class Audit extends BaseEntity {
    @Column(name = "method_name")
    private String methodName;
    private String arguments;
    @Column(name = "return_value")
    private String returnValue;
    private String exception;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Audit that = (Audit) o;
        return Objects.equals(this.methodName, that.methodName) && Objects.equals(this.arguments, that.arguments) && Objects.equals(this.returnValue, that.returnValue) && Objects.equals(this.exception, that.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.methodName, this.arguments, this.returnValue, this.exception);
    }

    @Override
    public String toString() {
        return "AuditEntity{" +
                "methodName='" + this.methodName + '\'' +
                ", arguments='" + this.arguments + '\'' +
                ", returnValue='" + this.returnValue + '\'' +
                ", exception='" + this.exception + '\'' +
                '}';
    }
}
