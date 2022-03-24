package com.krizhanovsky.okitter.repository;


import antlr.debug.MessageAdapter;
import com.krizhanovsky.okitter.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message,Integer>{
    List<Message> findByTag(String tag);
}
