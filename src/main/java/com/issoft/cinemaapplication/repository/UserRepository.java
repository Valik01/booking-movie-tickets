package com.issoft.cinemaapplication.repository;

import com.issoft.cinemaapplication.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    @Query(value = "select t.user from Ticket as t " +
            "inner join User as u on u.id = t.user.id " +
            "inner join Session as s on s.id = t.session.id " +
            "inner join Hall as h on h.id = s.hall.id and h.id = :hallId " +
            "where t.dateTimePurchase between :startDate and :endDate " +
            "group by t.user, h.id " +
            "order by count(t.user) desc")
    List<User> findMostActiveInHallForLastMonth(@Param("hallId") Long hallId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    Optional<User> findByLogin(final String login);
}
