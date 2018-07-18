package com.pro.chamado.repository;

import com.pro.chamado.entity.AlteracaoStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlteracaoStatusRepository extends MongoRepository<AlteracaoStatus,String> {

    Iterable<AlteracaoStatus> findByTicketIdOrderByDataAlteracaoStatusDesc(String ticketId);



}
