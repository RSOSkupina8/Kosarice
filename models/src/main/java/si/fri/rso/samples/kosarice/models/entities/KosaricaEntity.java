package si.fri.rso.samples.kosarice.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "kosarice")
@NamedQueries(value =
        {
                @NamedQuery(name = "KosaricaEntity.getAll",
                        query = "SELECT ko FROM KosaricaEntity ko")
        })
public class KosaricaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vsebina")
    private String vsebina;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVsebina() {
        return vsebina;
    }

    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

}