package controller;

import java.util.List;
import model.Kendaraan;
import model.KendaraanFactory;
import model.KendaraanRepository;
import model.Penyewaan;
import model.PenyewaanRepository;
import model.WajibJaminan;
import model.WajibSIM;
import view.RentalView;
import view.StrukView;

public class RentalController {
    private final KendaraanRepository kendaraanRepo = new KendaraanRepository();
    private final PenyewaanRepository penyewaanRepo = new PenyewaanRepository();
    private final RentalView view = new RentalView();
    private final StrukView strukView = new StrukView();

    public void tambahDataAwal(Kendaraan kendaraan) {
        kendaraanRepo.tambah(kendaraan);
    }

    public void jalankan() {
        while (true) {
            String pilihan = view.tampilMenu();
            switch (pilihan) {
                case "1" -> view.tampilDaftarkendaraan(kendaraanRepo.semua());
                case "2" -> tambahKendaraan();
                case "3" -> sewaKendaraan();
                case "4" -> KembalikanKendaraan();
                case "5" -> view.tampilkanRiwayat(penyewaanRepo.semua());
                case "0" -> {
                    view.tampilPesan("Terima kasih telah menggunakan aplikasi ini");
                    return;
                }

                default -> view.tampilPesan("Pilihan tidak valid atau Menu tidak ada");
            }
        }

    }

    public void tambahKendaraan() {
        String plat = view.bacaTeks("Plat Nomor");
        if (plat.isEmpty() || kendaraanRepo.cariByPlat(plat) != null) {
            view.tampilPesan("Plat nomor kosong atau sudah terdaftar.");
            return;
        }

        String merk = view.bacaTeks("Merk");
        // [OCP] Daftar jenis diambil dari Factory, bukan ditulis manual di Controller.
        String jenis = view.bacaTeks("Jenis (" + String.join("/", KendaraanFactory.JENIS) + ")").toUpperCase();
        if (!List.of(KendaraanFactory.JENIS).contains(jenis)) {
            view.tampilPesan("Jenis tidak dikenal.");
            return;
        }

        long tarif = view.bacaAngka("Tarif perhari");
        if (tarif <= 0) {
            view.tampilPesan("Tarif harus lebih dari 0.");
            return;
        }

        kendaraanRepo.tambah(KendaraanFactory.buat(jenis, plat, merk, tarif));
        view.tampilPesan("Kendaraan berhasil ditambahkan.");
    }

    public void sewaKendaraan() {
        String plat = view.bacaTeks("Plat Nomor");
        Kendaraan kendaraan = kendaraanRepo.cariByPlat(plat);
        if (kendaraan == null || !kendaraan.isTersedia()) {
            view.tampilPesan("Kendaraan tidak ditemukan atau sedang disewa.");
            return;
        }
        String nama = view.bacaTeks("Nama Penyewa");

        if (kendaraan instanceof WajibSIM WajibSIM) {
            String sim = view.bacaTeks("Golongan SIM penyewa");
            if (!sim.equalsIgnoreCase(WajibSIM.getGolonganSIM())) {
                view.tampilPesan(kendaraan.getMerk()+"Wajib SIM " + WajibSIM.getGolonganSIM()+".");
                return;
            }
            
        }

        int hari = (int) view.bacaAngka("Lama sewa (hari)");


        if (kendaraan instanceof WajibJaminan wajibJaminan) {
            if (!view.konfirmasiUangJaminan(wajibJaminan.getUangJaminan())) {
                view.tampilPesan("Penyewaan dibatalkan");
                return ;
                
            }
            
        }

        // [LSP] Validasi memakai kontrak kelas induk, BUKAN instanceof MobilPremium.
        // Berlaku sama untuk semua jenis, termasuk jenis yang dibuat nanti.
        if (hari < kendaraan.getMinimalHari()) {
            view.tampilPesan(kendaraan.getMerk() + " minimal disewa "
                    + kendaraan.getMinimalHari() + " hari.");
            return;
        }

        // [OCP] Controller tidak tahu dan tidak perlu tahu jenis kendaraannya.
        // Polimorfisme memilih hitungBiaya() milik subclass yang tepat.
        long totalBiaya = kendaraan.hitungBiaya(hari);
        Penyewaan sewa = new Penyewaan(penyewaanRepo.buatIBaru(), nama, kendaraan, hari, totalBiaya);
        penyewaanRepo.tambah(sewa);
        kendaraan.setTersedia(false);
        strukView.cetak(sewa);
    }

    public void KembalikanKendaraan() {
        String plat = view.bacaTeks("Plat Nomor");
        Penyewaan sewa = penyewaanRepo.cariAktifByPlat(plat);
        if (sewa == null) {
            view.tampilPesan("Tidak ada penyewaan");
            return;
        }

        sewa.selesai();
        sewa.getKendaraan().setTersedia(true);
        view.tampilPesan("Kendaraan " + sewa.getKendaraan().getMerk() + " Telah dikembalikan..");
    }
}