package com.issoft.cinemaapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Integer raw;
    private Integer seat;
    private Integer price;
    @Column(name = "date_time_purchase")
    private LocalDateTime dateTimePurchase;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Ticket that = (Ticket) o;
        return Objects.equals(this.session, that.session) && Objects.equals(this.user, that.user) && Objects.equals(this.raw, that.raw) && Objects.equals(this.seat, that.seat) && Objects.equals(this.price, that.price) && Objects.equals(this.dateTimePurchase, that.dateTimePurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.session, this.user, this.raw, this.seat, this.price, this.dateTimePurchase);
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "id=" + this.getId() +
                ", session=" + this.session +
                ", user=" + this.user +
                ", raw=" + this.raw +
                ", seat=" + this.seat +
                ", price=" + this.price +
                ", dateTimePurchase=" + this.dateTimePurchase +
                '}';
    }
}
