package ua.javarush.encoder.Exception;

public class NotFoundException  extends RuntimeException  {
    public  NotFoundException (String message){
        super(message);
        System.out.println(message);
    }
}
