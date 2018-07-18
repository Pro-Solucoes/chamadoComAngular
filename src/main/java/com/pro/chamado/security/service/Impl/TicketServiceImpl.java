package com.pro.chamado.security.service.Impl;


import com.pro.chamado.entity.AlteracaoStatus;
import com.pro.chamado.entity.Ticket;
import com.pro.chamado.repository.AlteracaoStatusRepository;
import com.pro.chamado.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AlteracaoStatusRepository changeStatusRepository;

    public Ticket createOrUpdate(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    public Ticket findById(String id) {
        return this.ticketRepository.findOne(id);
    }

    public void delete(String id) {
        this.ticketRepository.delete(id);
    }

    public Page<Ticket> listTicket(int page, int count) {
        Pageable pages = new PageRequest(page, count);
        return this.ticketRepository.findAll(pages);
    }

    @Override
    public AlteracaoStatus createAlteracaoStatus(AlteracaoStatus alteracaoStatus) {
        return null;
    }

    @Override
    public Iterable<AlteracaoStatus> listAlteracaoStatus(String tiketId) {
        return null;
    }

    public Iterable<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    public Page<Ticket> findByParameterAdnAssignerdUser(int page, int count, String title, String status, String prioridade, String asssignedUser) {
        return null;
    }

    public Page<Ticket> findByCurrentUser(int page, int count, String userId) {
        Pageable pages = new PageRequest(page, count);
        return this.ticketRepository.findByUsuarioIdOrderByDateDesc(pages, userId);

    }

    public AlteracaoStatus createChangeStatus(AlteracaoStatus changeStatus) {
        return this.changeStatusRepository.save(changeStatus);
    }

    public Iterable<AlteracaoStatus> listChangeStatus(String ticketId) {
        return this.changeStatusRepository.findByTicketIdOrderByDataAlteracaoStatusDesc(ticketId);
    }

    public Page<Ticket> findByParameters(int page, int count, String title, String status, String priority) {
        Pageable pages = new PageRequest(page, count);
        return this.ticketRepository.
                findByTituloIgnoreCaseContainingAndStatusAndPrioridadeOrderByDateDesc(
                        title, status, priority, pages);    }

    public Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status,
                                                       String priority, String userId) {
        Pageable pages = new PageRequest(page, count);
        return this.ticketRepository.
                findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioIdOrderByDateDesc
                        (title, status, priority, pages);
    }

    @Override
    public Page<Ticket> findByNumero(int page, int cout, Integer numero) {
        return null;
    }

    public Page<Ticket> findByNumber(int page, int count, Integer number) {
        Pageable pages = new PageRequest(page, count);
        return this.ticketRepository.findByNumero(number, pages);
    }

    public Page<Ticket> findByParametersAndAssignedUser(int page, int count, String title, String status,
                                                        String priority, String assignedUserId) {
        Pageable pages = new PageRequest(page, count);
        return this.ticketRepository.
                findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndAtribuidoUsuarioIdOrderByDateDesc(

                        title, status, priority,  pages);
    }


}

