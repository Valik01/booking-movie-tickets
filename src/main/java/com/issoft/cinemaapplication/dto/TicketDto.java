package com.issoft.cinemaapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    @Min(1)
    private Long sessionId;
    @Min(1)
    private Long userId;
    @Min(1)
    private Integer raw;
    @Min(1)
    private Integer seat;
    @Min(0)
    private Integer price;
    private LocalDateTime dateTimePurchase;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final TicketDto ticketDto = (TicketDto) o;
        return Objects.equals(this.sessionId, ticketDto.sessionId) && Objects.equals(this.userId, ticketDto.userId) && Objects.equals(this.raw, ticketDto.raw) && Objects.equals(this.seat, ticketDto.seat) && Objects.equals(this.price, ticketDto.price) && Objects.equals(this.dateTimePurchase, ticketDto.dateTimePurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sessionId, this.userId, this.raw, this.seat, this.price, this.dateTimePurchase);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "id=" + this.id +
                ", sessionId=" + this.sessionId +
                ", userId=" + this.userId +
                ", raw=" + this.raw +
                ", seat=" + this.seat +
                ", price=" + this.price +
                ", dateTimePurchase=" + this.dateTimePurchase +
                '}';
    }
}

