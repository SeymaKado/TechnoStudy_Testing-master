package Utility;

public class Fonksiyon {
    public static void bekle(int sn){
        try{
            Thread.sleep(sn* 1000L);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
