package test;

import com.imorning.im.bean.TranObject;
import com.imorning.im.bean.TranObjectType;
import com.imorning.im.bean.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    //http://imserver.gz2vip.idcfengye.com
    public static void main(String[] args) {
        try {
            Socket s = new Socket("192.168.1.107", 1124);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            TranObject tran = new TranObject();
            User user = new User();
            user.setAccount("123456");
            user.setPassword("123456");
            tran.setObject("user");
            tran.setTranType(TranObjectType.REGISTER_ACCOUNT);
            out.writeObject(tran);
            ObjectInputStream r = new ObjectInputStream(s.getInputStream());
            System.out.println(r.toString());
            while (true) {
                System.out.println("Running.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
