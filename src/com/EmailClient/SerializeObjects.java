package com.EmailClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

class SerializeObjects {
    public void objectSerialization(Object inObject) throws IOException {
        String fileSer = "file.ser";
        File f = new File(fileSer);

        if (!f.exists()){
            ObjectOutputStream os_1 = new ObjectOutputStream(new FileOutputStream(fileSer));
            os_1.writeObject(inObject);
            os_1.close();
        }else{
            try
            {
                FileOutputStream file_serial = new FileOutputStream(fileSer, true);
                ObjectOutputStream oos_obj = new ObjectOutputStream(file_serial) {
                    protected void writeStreamHeader() throws IOException {
                        reset();
                    }
                };

                oos_obj.writeObject(inObject);

                oos_obj.close();
                file_serial.close();


            }

            catch(IOException e)
            {
                e.printStackTrace();
            }
        }



    }


}
