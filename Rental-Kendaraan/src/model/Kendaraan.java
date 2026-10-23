package model;

//[SRP] + [OCP]:
//Kelas dibuat abstract secara tertutup untuk modifikasi (jadi tidak lagi blok if-else untuk menentukan jenis objeknya),
//dengan abstract sifatnya jadi terbuka untuk di extend, jika ada jenis baru maka subclass baru.
public abstract class Kendaraan {
    private final String platNomor;
    private final String merk;
    private final long tarifPerHari;
    private boolean tersedia = true;

    public Kendaraan(String platNomor, String merk, long tarifPerHari) {
        this.platNomor = platNomor;
        this.merk = merk;
        this.tarifPerHari = tarifPerHari;
    }

    // [OCP] Setiap subclass menentukan sendiri nama jenis & cara hitung biayanya.
    public abstract String getJenis();

    // [LSP] Kontrak yang wajib dipatuhi semua subclass:
    // Prasyarat: hari >= getMinimalHari()
    // Hasil : biaya > 0, tanpa melempar exception
    // Subclass boleh menghitung dengan cara berbeda.
    public abstract long hitungBiaya(int hari);

    // [LSP] Aturan minimal sewa dijadikan bagian dari kontrak kelas induk.
    // Default 1 hari; subclass boleh mengubah nilainya, dan pemanggil
    // (Controller) cukup bertanya ke sini tanpa perlu tahu jenis kendaraannya.
    public int getMinimalHari() {
        return 1;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public String getMerk() {
        return merk;
    }

    public long getTarifPerHari() {
        return tarifPerHari;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }
}