package com.example.javaproject;

public class Facture {
    private  int IdFac;
    private String DetailsFac;
    Facture(){}
    Facture(int id , String de){
        this.IdFac=id;
        this.DetailsFac=de;
    }

    public String getDetailsFac() {
        return DetailsFac;
    }

    public void setDetailsFac(String detailsFac) {
        DetailsFac = detailsFac;
    }

    public int getIdFac() {
        return IdFac;
    }

    public void setIdFac(int idFac) {
        IdFac = idFac;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "IdFac=" + IdFac +
                ", DetailsFac='" + DetailsFac + '\'' +
                '}';
    }
    public void AfficherF(){
        System.out.println(toString());
    }


}
