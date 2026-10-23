package model;

public class MobilPremium extends Mobil {
        private static final long ASURANSI_PREMIUM = 100000;
        private static final int MINIMAL_HARI = 3;
        private static final long UANG_JAMINAN_PREMIUM = 2000000;

        public MobilPremium(String platNomor, String merk, long tarifPerHari) {
                super(platNomor, merk, tarifPerHari);
        }

        @Override 
        public long getUangJaminan() {
                return UANG_JAMINAN_PREMIUM;
        }

        @Override
        public String getJenis() {
                return "PREMIUM";
        }

        @Override
        public int getMinimalHari() {
                return MINIMAL_HARI;
        }

        //[LSP] hasil selalu biaya > 0 sesuai kontrak induk.
        @Override
        public long hitungBiaya(int hari) {
                return super.hitungBiaya(hari) + ASURANSI_PREMIUM;
        }
}