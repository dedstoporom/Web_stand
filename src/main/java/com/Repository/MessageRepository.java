package com.Repository;

import com.Models.Message;
import com.Models.User;
import com.Models.dto.MessageDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message,Long>
{
    @Query("select new com.Models.dto.MessageDto(m,count(ml),sum(case when ml=:user then 1 else 0 end) > 0) " +
            "from Message m  left  join m.likes ml where m.tag=:tag group by m ")
    List<MessageDto> findByTag(@Param("tag") String tag,@Param("user") User user);

    @Query("select new com.Models.dto.MessageDto(m,count(ml),sum(case when ml=:user then 1 else 0 end) > 0) " +
            "from Message m  left  join m.likes ml  group by m ")
    List<MessageDto> findAll(@Param("user") User user);
}
