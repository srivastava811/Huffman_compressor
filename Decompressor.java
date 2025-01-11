import java.util.*;
import java.io.*;

public class Decompressor 
{
    public static void decompress(String src, String dst)
    {
        try
        {
            FileInputStream inStream=new FileInputStream(src);
            ObjectInputStream objectInStream=new ObjectInputStream(inStream);
            byte[] huffmanBytes=(byte[]) objectInStream.readObject();
            @SuppressWarnings("unchecked")
            Map<Byte, String> huffmanCodes=(Map<Byte, String>) objectInStream.readObject();
            byte[] bytes=decomp(huffmanCodes,huffmanBytes);
            OutputStream outStream=new FileOutputStream(dst);
            outStream.write(bytes);
            inStream.close();
            objectInStream.close();
            outStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] decomp(Map<Byte, String> huffCodes, byte[] huffmanBytes)
    {
        StringBuilder sb1=new StringBuilder();
        for(int i=0;i<huffmanBytes.length;i++)
        {
            byte b=huffmanBytes[i];
            boolean flag=(i==huffmanBytes.length-1);
            sb1.append(convertbyteInBit(!flag,b));
        }

        Map<String,Byte> map=new HashMap<>();
        for(Map.Entry<Byte, String> entry:huffCodes.entrySet())
        {
            map.put(entry.getValue(),entry.getKey());
        }

        List<Byte> list=new ArrayList<>();
        for(int i=0;i<sb1.length();)
        {
            int count=1;
            boolean flag=true;
            Byte b=null;
            while(flag)
            {
                String key=sb1.substring(i,i+count);
                b=map.get(key);
                if(b==null)
                count++;
                else
                flag=false;
            }
            list.add(b);
            i+=count;
        }
        byte b[]=new byte[list.size()];
        for(int i=0;i<b.length;i++)
        b[i]=list.get(i);
        return b;
    }

    private static String convertbyteInBit(boolean flag,byte b)
    {
        int curByte=b;
        if(flag) 
        curByte=b & 0xFF;
        String str8=Integer.toBinaryString(curByte);
        if(flag || curByte<0)
        return str8.substring(str8.length()-8);
        else return str8;
    }
}
