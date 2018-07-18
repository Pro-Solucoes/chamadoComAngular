package com.pro.chamado.controller;

import com.pro.chamado.dto.Resumo;
import com.pro.chamado.entity.AlteracaoStatus;
import com.pro.chamado.entity.Ticket;
import com.pro.chamado.entity.Usuario;
import com.pro.chamado.enums.PerfilEnum;
import com.pro.chamado.enums.StatusEnum;
import com.pro.chamado.response.Response;
import com.pro.chamado.security.jwt.JwtTokenUtil;
import com.pro.chamado.security.service.Impl.TicketService;
import com.pro.chamado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping()
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<Ticket>> createOrUpdate(HttpServletRequest request,
                                                           @RequestBody Ticket ticket,
                                                           BindingResult result) {
        Response<Ticket> response = new Response<Ticket>();
        try {
            validateCreateTicket(ticket, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }
            ticket.setStatus(StatusEnum.getStatus("New"));
            ticket.setUsuario(userFromRequest(request));
            ticket.setDate(new Date());
            ticket.setNumero(gerarNumero());
            Ticket tiketPesisted = (Ticket) ticketService.createOrUpdate(ticket);
            response.setData(tiketPesisted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);

    }

    private void validateCreateTicket(Ticket ticket, BindingResult result) {
        if (ticket.getTitulo() == null) {
            result.addError(new ObjectError("Ticket", "Titulo nao informado"));
            return;
        }
    }

    public Usuario userFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Autorization");
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return usuarioService.findByEmail(email);
    }

    private Integer gerarNumero() {
        Random random = new Random();
        return random.nextInt(9999);
    }

    private void validateUpdateTicket(Ticket ticket, BindingResult result) {
        if (ticket.getId() == null) {
            result.addError(new ObjectError("Ticket", "Titulo nao informado"));
            return;
        }

        if (ticket.getTitulo() == null) {
            result.addError(new ObjectError("Ticket", "Id nao informado"));
            return;
        }
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<Ticket>> update(HttpServletRequest request,
                                                   @RequestBody Ticket ticket,
                                                   BindingResult result) {
        Response<Ticket> response = new Response<Ticket>();
        try {
            validateCreateTicket(ticket, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }

            Ticket ticketCurrent = ticketService.findById(ticket.getId());
            ticket.setStatus(ticketCurrent.getStatus());
            ticket.setUsuario(ticketCurrent.getUsuario());
            ticket.setDate(ticketCurrent.getDate());
            ticket.setNumero(ticketCurrent.getNumero());

            if (ticketCurrent.getAtribuidoUsuario() != null) {
                ticket.setAtribuidoUsuario(ticketCurrent.getAtribuidoUsuario());
            }
            Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticket);
            response.setData(ticketPersisted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
    public ResponseEntity<Response<Ticket>> findByid(@PathVariable("id") String id) {
        Response<Ticket> response = new Response<Ticket>();
        Ticket ticket = ticketService.findById(id);

        if (ticket == null) {
            response.getErrors().add("Registro nao encontroado com id:" + id);
            return ResponseEntity.badRequest().body(response);
        }
        List<AlteracaoStatus> alteracaoStatusList = new ArrayList<AlteracaoStatus>();
        Iterable<AlteracaoStatus> alteracaoStatuCurrentList = ticketService.listAlteracaoStatus(ticket.getId());
        for (Iterator<AlteracaoStatus> iterator = alteracaoStatusList.iterator(); ((Iterator) iterator).hasNext(); ) {
            AlteracaoStatus alteracaoStatus = (AlteracaoStatus) ((Iterator) iterator).next();
            alteracaoStatus.setTicket(null);
            alteracaoStatusList.add(alteracaoStatus);
        }
        ticket.setAlteracao(alteracaoStatusList);
        response.setData(ticket);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
        Response<String> response = new Response<String>();
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            response.getErrors().add("Registro nao encontroado com id:" + id);
            return ResponseEntity.badRequest().body(response);
        }
        ticketService.delete(id);
        return ResponseEntity.ok(new Response<String>());
    }

    @PutMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
    public ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request,
                                                          @PathVariable("page") int page,
                                                          @PathVariable("count") int count) {
        Response<Page<Ticket>> response = new Response<Page<Ticket>>();
        Page<Ticket> tickets = null;
        Usuario usuarioRequest = userFromRequest(request);
        if (usuarioRequest.getPerfil().equals(PerfilEnum.ROLE_TECHNICIAN)) {
            tickets = ticketService.listTicket(page, count);
        } else if (usuarioRequest.getPerfil().equals(PerfilEnum.ROLE_CUSTOMER)) {
            tickets = ticketService.findByCurrentUser(page, count, usuarioRequest.getId());
        }
        response.setData(tickets);
        return ResponseEntity.ok(response);

    }

    @PutMapping(value = "{page}/{count}/{numero}/{titulo}/{status}/{prioridade}/{alteracao}")
    @PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
    public ResponseEntity<Response<Page<Ticket>>> findByParams(HttpServletRequest request,
                                                               @PathVariable("page") int page,
                                                               @PathVariable("count") int count,
                                                               @PathVariable("numero") Integer numero,
                                                               @PathVariable("titulo") String titulo,
                                                               @PathVariable("status") String status,
                                                               @PathVariable("prioridade") String prioridade,
                                                               @PathVariable("alteracao") boolean alteracao) {

        titulo = titulo.equals("uninformed") ? "" : titulo;
        status = status.equals("uninformed") ? "" : status;
        prioridade = prioridade.equals("uninformed") ? "" : prioridade;
        Response<Page<Ticket>> response = new Response<Page<Ticket>>();

        Page<Ticket> tickets = null;
        if (numero > 0) {
            tickets = ticketService.findByNumero(page, count, numero);
        } else {
            Usuario usuarioRequest = userFromRequest(request);
            if (usuarioRequest.getPerfil().equals(PerfilEnum.ROLE_TECHNICIAN)) {
                if (alteracao) {
                    tickets = ticketService.findByParametersAndCurrentUser(page, count, titulo, status, prioridade, usuarioRequest.getId());
                } else {
                    tickets = ticketService.findByParameters(page, count, titulo, status, prioridade);
                }
            } else if (usuarioRequest.getPerfil().equals(PerfilEnum.ROLE_CUSTOMER)) {
                tickets = ticketService.findByParametersAndCurrentUser(page, count, titulo, status, prioridade, usuarioRequest.getId());
            }
        }
        response.setData(tickets);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "{id}/{status}")
    @PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
    public ResponseEntity<Response<Ticket>> alterarStatus(@PathVariable("id") String id,
                                                          @PathVariable("status") String status,
                                                          HttpServletRequest request,
                                                          @RequestBody Ticket ticket,
                                                          BindingResult result) {

        Response<Ticket> response = new Response<Ticket>();
        try {
            validateAlteracaoStatus(id, status, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }
            Ticket ticketCurrent = ticketService.findById(id);
            ticketCurrent.setStatus(StatusEnum.getStatus(status));
            if (status.equals("Alteracao")) {
                ticketCurrent.setAtribuidoUsuario(userFromRequest(request));
            }

            Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticketCurrent);
            AlteracaoStatus alteracaoStatus = new AlteracaoStatus();
            alteracaoStatus.setUsuarioAlteracao(userFromRequest(request));
            alteracaoStatus.setDataAlteracaoStatus(new Date());
            alteracaoStatus.setStatus(StatusEnum.getStatus(status));
            alteracaoStatus.setTicket(ticketPersisted);
            ticketService.createAlteracaoStatus(alteracaoStatus);
            response.setData(ticketPersisted);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    private void validateAlteracaoStatus(String id, String status, BindingResult result) {
        if (id == null || id.equals("")) {
            result.addError(new ObjectError("Ticket", "Id nao informado"));
            return;
        }

        if (status == null || status.equals("")) {
            result.addError(new ObjectError("Ticket", "Status nao informado"));
            return;
        }
    }

    @GetMapping(value = "/resumo")
    public ResponseEntity<Response<Resumo>> findResumo() {
        Response<Resumo> response = new Response<Resumo>();

        Resumo resumo = new Resumo();

        int montanteNovo = 0;
        int montanteResolvido = 0;
        int montanteAprovado = 0;
        int montanteDisaprovado = 0;
        int montanteAtribuido = 0;
        int montanteFechado = 0;

        Iterable<Ticket> tickets = ticketService.findAll();
        if(tickets !=null){
            for(Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();){
                Ticket ticket = (Ticket) iterator.next();
            }
        }
        return ResponseEntity.ok(response);
    }


}
