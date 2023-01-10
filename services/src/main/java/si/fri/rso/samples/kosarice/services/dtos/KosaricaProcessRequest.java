package si.fri.rso.samples.kosarice.services.dtos;

public class KosaricaProcessRequest {
    public KosaricaProcessRequest() {
    }

    public KosaricaProcessRequest(String kosaricaId, String imageLocation) {
        this.kosaricaId = kosaricaId;
        this.imageLocation = imageLocation;
    }

    private String kosaricaId;
    private String imageLocation;

    public String getKosaricaId() {
        return kosaricaId;
    }

    public void setKosaricaId(String kosaricaId) {
        this.kosaricaId = kosaricaId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }
}
