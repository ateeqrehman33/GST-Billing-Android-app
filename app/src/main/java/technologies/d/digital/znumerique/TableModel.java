package technologies.d.digital.znumerique;

public class TableModel {



    private String slno;
    private String part;
    private String basic;
    private String qty;
    private String disc;
    private String gst;
    private String tamt;


    public TableModel(String slno,String part, String basic, String qty, String disc, String gst, String tamt ) {
        this.slno=slno;
        this.part=part;
        this.basic=basic;
        this.qty=qty;
        this.disc=disc;
        this.gst= gst;
        this.tamt=tamt;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic
        ;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTamt() {
        return tamt;
    }

    public void setTamt(String tamt) {
        this.tamt = tamt;
    }


}
