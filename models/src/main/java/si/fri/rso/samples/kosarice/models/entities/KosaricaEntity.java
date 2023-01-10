package si.fri.rso.samples.kosarice.models.entities;

import javax.persistence.*;

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

    @Column(name = "cena")
    private Float cena;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

}