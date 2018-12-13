package ejb;

import entity.Request;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RequestEJB {

    @PersistenceContext(unitName = "itp212")
    private EntityManager em;

    public void createRequest(Request request) {
        request.setTitle(request.getTitle());
        request.setDetail(request.getDetail());
        request.setLocation(request.getLocation());
        request.setDate(request.getDate());
        request.setTime(request.getTime());
        request.setPrice(request.getPrice());
        request.setDuration(request.getDuration());
        request.setUser(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        em.persist(request);
    }
}