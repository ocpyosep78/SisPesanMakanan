/* Nama berkas: ClsAdmin.java
 * Fungsi: menangani otentifikasi admin
 * Pemrogram: I Made Ariana 
 * Hari,tanggal,jam: Minggu, 01 Maret 2015, 21.56 WIB
 * E-mail: i.made43@ui.ac.id / made.ariana@mail.ugm.ac.id
 */

package pilar.cls;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import org.w3c.dom.DOMException;

public class ClsPelanggan {
    
    /* ################################################
     * fHalamanAdmin: validasi halaman admin 
     * Parameter Masukan:
     * 1) vFSesi (HttpSession): data sesi.
     * Keluaran:
     * 1) vKeluaran (boolean): true-> nilai sesi valid, false-> nilai sesi tidak valid.
       ################################################ */
    public boolean fHalamanPelanggan(HttpSession vFSesi) {
        boolean vKeluaran = false;
        
        try{
            String vID = vFSesi.getAttribute("sesIDPub").toString();
            String vSandi = vFSesi.getAttribute("sesSandiPUb").toString();
            String vEmailDB = "";
            String vSandiDB = "";

            //String vKeluaran = "fHalamanAdmin -> ID: " + vID + " & sandi: " + vSandi;
            /* mencocokkan data session dengan basisdata */
            /* mengambil data nama dan sandi */
            ClsOperasiBasisdataOri oOpsBasisdata = new ClsOperasiBasisdataOri();

            /* 
                String vFNamaBerkasKonf,
                String vFNamaBd,
                String vFNamaTabel,
                String[] vFArrNamaKolom,
                String vFKolomUrut,
                String vFJenisUrut,
                String[] vFOffset
            */

            ResultSet vHasil = oOpsBasisdata.fArrAmbilDataDbKondisi("","", 
                    "tb_pelanggan", 
                    new String[]{"email","sandi"}, 
                    new String[]{"email"},
                    "nomor", 
                    "", new String[]{"0","1"},"=");

            /* keluaran permintaan */
            while(vHasil.next()){
                vEmailDB = vHasil.getString("email");
                vSandiDB = vHasil.getString("sandi");
            }			
            
            System.out.println(vID + "#" + vEmailDB + "#" + "#" + vSandi + "#" + vSandiDB);
             /* mencocokkan data */
            if (vID != null && vEmailDB !=null && vSandi != null && vSandiDB != null) {
                
                /* sebelum divalidasi */
                /* apabila data POST sesuai dgn data dalam tabel basisdata: lupa password jadi di NOT */
                if(vID.equals(vEmailDB) && vSandi.equals(vSandiDB)){
                    vKeluaran = true;
                }
            }
        }catch(SQLException | DOMException e){
            /* pencatatan sistem */
            if(ClsKonf.vKonfCatatSistem == true){
                String vNamaKelas = "ClsPelanggan.java";
                String vNamaMetode = "fHalamanPelanggan";
                String vCatatan = vNamaKelas + "#" + vNamaMetode + "#" + e.toString();
                /* obyek catat */
                ClsCatat oCatat = new ClsCatat();
                oCatat.fCatatSistem(ClsKonf.vKonfCatatKeOutput, 
                        ClsKonf.vKonfCatatKeBD, 
                        ClsKonf.vKonfCatatKeBerkas, 
                        vCatatan);
            }
        }
        
        /* debug nilai id dan password */
        //System.out.println("Belum divalidasi : " + vID + " = " + vNamaDB + " & " + vSandi + " = " + vSandiDB);
        /* nilai keluaran */
        return vKeluaran;
    }
    
}
