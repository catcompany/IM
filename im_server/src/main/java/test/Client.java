package test;

import com.imorning.im_server.bean.TranObject;
import com.imorning.im_server.bean.TranObjectType;
import com.imorning.im_server.bean.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    //http://imserver.gz2vip.idcfengye.com
    public static void main(String[] args) {
        try {
            Socket s = new Socket("imserver.gz2vip.idcfengye.com", 80);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            TranObject tran = new TranObject();
            User user = new User();
            user.setAccount("123456");
            user.setPassword("123456");
            tran.setObject(user);
            tran.setTranType(TranObjectType.LOGIN);
            out.writeObject(tran);
            //new ObjectInputStream(s.getInputStream());
            while (true) {
                System.out.println(System.currentTimeMillis());
                Thread.sleep(3000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
