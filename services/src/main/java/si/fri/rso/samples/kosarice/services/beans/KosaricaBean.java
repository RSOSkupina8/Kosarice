package si.fri.rso.samples.kosarice.services.beans;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.samples.kosarice.lib.Kosarica;
import si.fri.rso.samples.kosarice.models.converters.KosaricaConverter;
import si.fri.rso.samples.kosarice.models.entities.KosaricaEntity;

public class KosaricaBean {

    private Logger log = Logger.getLogger(KosaricaBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Kosarica> getKosarice() {

        TypedQuery<KosaricaEntity> query = em.createNamedQuery(
                "KosaricaEntity.getAll", KosaricaEntity.class);

        List<KosaricaEntity> resultList = query.getResultList();

        return resultList.stream().map(KosaricaConverter::toDto).collect(Collectors.toList());

    }

    @Timed
    public List<Kosarica> getKosaricaFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, KosaricaEntity.class, queryParameters).stream()
                .map(KosaricaConverter::toDto).collect(Collectors.toList());
    }

    public Kosarica getKosarica(Integer id) {

        KosaricaEntity kosaricaEntity = em.find(KosaricaEntity.class, id);

        if (kosaricaEntity == null) {
            throw new NotFoundException();
        }

        Kosarica kosarica = KosaricaConverter.toDto(kosaricaEntity);

        return kosarica;
    }

    public Kosarica createKosarica(Kosarica kosarica) {

        KosaricaEntity kosaricaEntity = KosaricaConverter.toEntity(kosarica);

        try {
            beginTx();
            em.persist(kosaricaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (kosaricaEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return KosaricaConverter.toDto(kosaricaEntity);
    }

    public Kosarica putKosarica(Integer id, Kosarica kosarica) {

        KosaricaEntity c = em.find(KosaricaEntity.class, id);

        if (c == null) {
            return null;
        }

        KosaricaEntity updatedKosaricaEntity = KosaricaConverter.toEntity(kosarica);

        try {
            beginTx();
            updatedKosaricaEntity.setId(c.getId());
            updatedKosaricaEntity = em.merge(updatedKosaricaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return KosaricaConverter.toDto(updatedKosaricaEntity);
    }

    public boolean deleteKosarica(Integer id) {

        KosaricaEntity kosarica = em.find(KosaricaEntity.class, id);

        if (kosarica != null) {
            try {
                beginTx();
                em.remove(kosarica);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
