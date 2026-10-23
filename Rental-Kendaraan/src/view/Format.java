package view;

import java.util.Locale;

//[SRP] satu satunya tanggung jawab: memformat angka menjadi teks rupiah

public class Format {
    public static String rupiah(long nilai){
        return String.format( Locale.US, "Rp%,d", nilai).replace(",", ".");
    }
}
