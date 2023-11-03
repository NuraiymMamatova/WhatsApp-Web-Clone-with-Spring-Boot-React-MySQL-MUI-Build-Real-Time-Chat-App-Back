package com.whatsappyoutube.repositories;

import com.whatsappyoutube.entities.Chat;
import com.whatsappyoutube.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("select c from Chat c join c.users u where u.id = :userId ")
    List<Chat> findAllChatByUserId(@Param("userId") Integer userId);

    @Query("select c from Chat c where c.isGroup = false and :user member of c.users and :reqUser member of c.users")
    Chat findSingleChatByUserIds(@Param("user") User user, @Param("reqUser") User reqUser);

}