package view;

import model.Penyewaan;
//[SRP] satu satunya tanggung jawab: format tampilkan struk sewa.
public class StrukView {
    public void cetak(Penyewaan p){
        System.out.println("=========== STRUK SEWA ===========");
        System.out.println("No. Sewa     : "+p.getId());
        System.out.println("Nama Penyewa : "+p.getNamaPenyewa());
        System.out.println("Kendaraan    : "+p.getKendaraan().getMerk()+" ("+p.getKendaraan().getPlatNomor()+")");
        System.out.println("Jenis        : "+p.getKendaraan().getJenis());
        System.out.println("Lama Sewa    : "+p.getLamaHari()+" hari");
        System.out.println("Total Biaya  : "+Format.rupiah(p.getTotalBiaya()));
        System.out.println("=====================================");
    }

}
