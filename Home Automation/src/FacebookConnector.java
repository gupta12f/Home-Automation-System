
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sarthak
 */
public class FacebookConnector {

    /**
     * @param args the command line arguments
     */
    public static BufferedReader input;
    public static OutputStream output;

    public static void main(String[] args) {
        // TODO code application logic here
        String accessToken = "EAACEdEose0cBAHHFfAKodTevZAOfyOQxSVRN3gZACmewuRStq0oNSOZBQIsQVJ7d5rnz4karWdwqaOKzVretiBIjHzCQIRsD5xaiYP9ARZADGBIH08Qge8m2XFzg9xZCMKzHqHTXtc70bTvUriPhZAuginMDmN7FZBKOzv6gCPnMQZDZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        Date previous = new Date();
        try {
            SerialClass obj = new SerialClass();
            StringProcessor stringProcessor = new StringProcessor();
            obj.initialize();
            input = SerialClass.input;
            output = SerialClass.output;
            InputStreamReader Ir = new InputStreamReader(System.in);
            BufferedReader Br = new BufferedReader(Ir);
            

            for (;;) {
                //System.out.println("in loop");
                Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
                //System.out.println("Created result");
                for (List<Post> page : result) {
                    for (Post aPost : page) {

                        Date current = aPost.getCreatedTime();
                        //System.out.println(aPost.getAdminCreator().getName());

                        if (current.getTime() > previous.getTime()) {
                            if (aPost.getMessage().equals("STOP")) {
                                System.out.println("Home : Exiting. Thank you for using the product");
                                obj.close();
                                System.exit(0);
                            } else {
                                
                                System.out.println("Sarthak : " + aPost.getMessage());
                                Integer messageCode = stringProcessor.processString(aPost.getMessage());
                                if(messageCode >=0 && messageCode < 100){
                                    //query
                                    SerialClass.writeData(messageCode.toString()); 
                                    previous = current;
                                }
                                else if(messageCode >99 && messageCode < 200){
                                    //instruction
                                    SerialClass.writeData(messageCode.toString());
                                    previous = current;
                                }
                                else if(messageCode > 199 && messageCode < 300){
                                    //activation
                                    fbClient.publish("me/feed", FacebookType.class, Parameter.with("message",  "Hi Sarthak! Hope you are having a great day!"));
                                    previous = new Date();
                                }
                                else if(messageCode == -1){
                                    System.out.println("Sorry, could not decipher the request");
                                }
                                //if(aPost.getMessage().equals("Please turn ON lights")){
                                   
                                //}
                                //fbClient.publish("me/feed", FacebookType.class, Parameter.with("message",  "Instruction Received, wait for acknowledgement before issuing new instruction"));
                                //System.out.println("Home : " + "Instruction Received, wait for acknowledgement before issuing new instruction");
                                //previous = current;
                            }
                        }
                        /*else{
                            System.out.println("no");
                        }*/
                    }
                }
            }
            
        } catch (Exception e) {

        }

    }

}
