package view;
import java.util.List;
import java.util.Scanner;
import model.Kendaraan;
import model.Penyewaan;

public class RentalView {
    public final Scanner input = new Scanner(System.in);
    public String tampilMenu(){
        System.out.println("================ Aplikasi Rental Kendaraan ================");
        System.out.println("1. Lihat daftar kendaraan");
        System.out.println("2. Tambah Kendaraan");
        System.out.println("3. Sewa Kendaraan");
        System.out.println("4. Kembalikan Kendaraan");
        System.out.println("5. Riwayat Pesanan");
        System.out.println("0. Keluar");
        return bacaTeks("Pilih menu> ");
    }

    public void tampilDaftarkendaraan(List<Kendaraan> daftar){
        System.out.println("=========== DAFTAR KENDARAAN ===========");
        if(daftar.isEmpty()){
            System.out.println("Belum ada data kendaraan");
            return;
        }

        for (Kendaraan k : daftar) {
            System.out.println(k.getPlatNomor()+" | "+k.getMerk()+" ("+k.getJenis()+")" +
             " | "+Format.rupiah(k.getTarifPerHari())+" | " + k.getMinimalHari() +
             " | "+(k.isTersedia() ? "Tersedia" : "Disewa"));
        }

    }

    public void tampilkanRiwayat(List<Penyewaan> daftar){
        System.out.println("=========== RIWAYAT PENYEWAAN ===========");
        if(daftar.isEmpty()){
            System.out.println("Belum ada Penyewaan");
            return;
        }

        for (Penyewaan p : daftar) {
            System.out.println(
                p.getId()+" | "+p.getNamaPenyewa()+" | "+p.getKendaraan().getMerk()
                + " | "+ p.getLamaHari()+ " | "+Format.rupiah(p.getTotalBiaya())
                + " | "+ (p.isAktif() ? "Berjalan" : "Selesai")
            );
        }

    }

    public String bacaTeks(String label){
        System.out.print(label +"> ");
        return input.nextLine().trim();
    }

    //mengulangi sampek pengguna memasukkan angka yang valid
    public long bacaAngka(String label){
        while (true){
            String teks = bacaTeks(label);
            try{
                return Long.parseLong(teks);
            } catch (NumberFormatException e){
                tampilPesan("Input harus angka, silahkan ulangi!!");
            }
        }
        
    }

    public void tampilPesan(String pesan){
        System.out.println(">> "+pesan);
    }

    public boolean konfirmasiUangJaminan(long nominan){
        tampilPesan("Kendaraan ini memerlukan uang jaminan" + Format.rupiah(nominan)+" (digunakan saat kendaraan dikembalikan).");
        return bacaTeks("Setuju? (y/n)").equalsIgnoreCase("y");
    }
}