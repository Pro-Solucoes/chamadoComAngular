package com.pro.chamado.repository;


import com.pro.chamado.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket,String> {


    Page<Ticket> findByUsuarioIdOrderByDateDesc(Pageable pageable,String usuarioId);


    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeOrderByDateDesc(
            String titulo,String status,String prioridade, Pageable pageable
    );

    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioIdOrderByDateDesc(
            String titulo,String status,String prioridade, Pageable pageable
    );


    Page<Ticket> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndAtribuidoUsuarioIdOrderByDateDesc(
            String titulo,String status,String prioridade, Pageable pageable

    );
    Page<Ticket>findByNumero(Integer numero,Pageable pageable);










}
