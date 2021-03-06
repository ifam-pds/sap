package br.edu.ifam.saf.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.modelo.Item;

@Stateless
public class ItemDAO {

    @PersistenceContext
    EntityManager em;

    private GenericDAO<Item> dao;

    @PostConstruct
    public void init() {
        dao = new GenericDAO<>(em, Item.class);
    }

    public Item consultar(Integer id) {
        return dao.consultar(id);
    }

    public List<Item> filtrarPorStatus(StatusItem status) {
        TypedQuery<Item> query = em.createQuery("select i from Item i where i.status = :status", Item.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    public void remover(Item entidade) {
        dao.remover(entidade);
    }

    public Item atualizar(Item entidade) {
        return dao.atualizar(entidade);
    }
}
