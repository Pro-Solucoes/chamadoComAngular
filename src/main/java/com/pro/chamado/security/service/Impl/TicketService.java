package com.pro.chamado.security.service.Impl;

import com.pro.chamado.entity.AlteracaoStatus;
import com.pro.chamado.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public interface TicketService {

    Ticket createOrUpdate(Ticket ticket);

    Ticket findById(String id);

    void delete(String id);

    Page<Ticket> listTicket(int page, int cout);

    AlteracaoStatus createAlteracaoStatus(AlteracaoStatus alteracaoStatus);

    Iterable<AlteracaoStatus> listAlteracaoStatus(String tiketId);

    Page<Ticket> findByCurrentUser(int page, int count,String userId);

    Page<Ticket> findByParameters(int page, int count,String title, String status, String prioridade);
    Page<Ticket> findByParametersAndCurrentUser(int page, int count,String title, String status, String prioridade,String usuarioId);
    Page<Ticket>findByNumero(int page, int cout, Integer numero);
    Iterable<Ticket>findAll();
    Page<Ticket>findByParameterAdnAssignerdUser(int page, int count, String title,String status, String prioridade,String asssignedUser);

}
