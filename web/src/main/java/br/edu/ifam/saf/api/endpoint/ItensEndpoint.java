package br.edu.ifam.saf.api.endpoint;


import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.data.StatusItemData;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.api.dto.ItemTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.dao.ItemDAO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.exception.ValidacaoError;
import br.edu.ifam.saf.modelo.Item;

@Stateless
@Path("/itens")
public class ItensEndpoint {

    @Inject
    private ItemTransformer itemTransformer;

    @Inject
    private ItemDAO dao;

    @Inject
    private Logger log;

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response itens(@QueryParam("status") StatusItem status) {

        if(status == null){
            status = StatusItem.ATIVO;
        }
        return Response.ok().entity(new ItensResponse(
                itemTransformer.toDTOList(dao.filtrarPorStatus(status)))
        ).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/{item_id}")
    public Response consultarItem(@PathParam("item_id") Integer itemId) {

        return Response.ok().entity(itemTransformer.toDTO(dao.consultar(itemId))).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @RequerLogin(Perfil.ADMINISTRADOR)
    @Path("/")
    public Response cadastrar(ItemDTO itemDTO) {
        try {
            Item itemACadastrar = itemTransformer.toEntity(itemDTO);
            itemACadastrar.setStatus(StatusItem.ATIVO);
            dao.atualizar(itemACadastrar);

            return Respostas.criado();

        } catch (ValidacaoError ex) {
            return Respostas.badRequest(ex.getMensagemErroResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Respostas.badRequest(new MensagemErroResponse(ex.getMessage()));
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @RequerLogin(Perfil.ADMINISTRADOR)
    @Path("/{item_id}")
    public Response atualizarItem(@PathParam("item_id") Integer itemId, ItemDTO itemDTO) {
        Item item = itemTransformer.toEntity(itemDTO);

        if (item == null) {
            return Respostas.badRequest(new MensagemErroResponse("Item não encontrado"));
        }

        Item itemAtualizado = itemTransformer.toEntity(itemDTO);
        itemAtualizado.setId(itemId);

        dao.atualizar(itemAtualizado);

        return Respostas.ok(item);
    }
}