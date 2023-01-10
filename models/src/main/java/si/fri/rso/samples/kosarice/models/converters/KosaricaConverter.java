package si.fri.rso.samples.kosarice.models.converters;

import si.fri.rso.samples.kosarice.lib.Kosarica;
import si.fri.rso.samples.kosarice.models.entities.KosaricaEntity;

public class KosaricaConverter {

    public static Kosarica toDto(KosaricaEntity entity) {

        Kosarica dto = new Kosarica();
        dto.setKosaricaId(entity.getId());
        dto.setCena(entity.getCena());
        return dto;

    }

    public static KosaricaEntity toEntity(Kosarica dto) {

        KosaricaEntity entity = new KosaricaEntity();
        entity.setCena(dto.getCena());

        return entity;

    }

}
