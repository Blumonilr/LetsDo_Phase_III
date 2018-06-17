package YingYingMonster.LetsDo_Phase_III;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class csHandler {


    public void post(String path,String content) throws Exception{
        URL url=new URL (path);
        HttpURLConnection conn=null;
        try {
            conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();


            OutputStream output=conn.getOutputStream();
            output.write(content.getBytes());
            output.flush();
            output.close();
            if (conn.getResponseCode()==200) {
                try(BufferedReader reader = new BufferedReader((new InputStreamReader(conn.getInputStream(),"UTF-8")))){
                    StringBuilder builder=new StringBuilder();
                    String tmp;
                    while((tmp=reader.readLine())!=null){
                        builder.append(tmp);
                        builder.append(System.lineSeparator());
                    }
                    System.out.println(builder.toString());
                }
            }else{
                System.out.println("failed");
            }
        }finally {
            if (conn!=null)
                conn.disconnect();
        }
    }

    public  void request(String path)throws Exception{
        URL url=new URL (path);
        HttpURLConnection conn=null;
        try {
            conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode()==200){
                try(BufferedReader reader = new BufferedReader((new InputStreamReader(conn.getInputStream(),"UTF-8")))){
                    StringBuilder builder=new StringBuilder();
                    String tmp;
                    while((tmp=reader.readLine())!=null){
                        builder.append(tmp);
                        builder.append(System.lineSeparator());
                    }
                    System.out.println(builder.toString());
                }
            }else{
                System.out.println("failed");
            }
        }finally {
            if (conn!=null)
                conn.disconnect();
        }
    }
}
