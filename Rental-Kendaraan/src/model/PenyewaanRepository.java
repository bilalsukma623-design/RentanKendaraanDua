package model;
import java.util.ArrayList;
import java.util.List;

//[SRP] satu satunya tanggung jawab: menyimpan dan mencari data penyewaan di ArrayList

public class PenyewaanRepository {
    private final List<Penyewaan> data = new ArrayList<>();

    public String buatIBaru(){
        return String.format("SW-%03d", data.size() + 1);
    }

    public void tambah(Penyewaan penyewaan){
        data.add(penyewaan);
    }

    public List<Penyewaan> semua(){
        return new ArrayList<>(data);
    }

    public Penyewaan cariAktifByPlat(String platNomor){
        for (Penyewaan p : data) {
            if(p.isAktif() && p.getKendaraan().getPlatNomor().equalsIgnoreCase(platNomor)){
                return p;
            }
        }
        return null;
    }
}
