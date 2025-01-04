// Compression of file using huffman compression.
import java.util.*;
import java.io.*;

public class Compressor
{
    private static StringBuilder sb=new StringBuilder();
    private static Map<Byte, String> huffmap=new HashMap<>();

    //This function is responsible for compression of files.
    //src means source 
    //dest means destination
    public static void compress(String src,String dest)
    {
        try
        {
            //Input the source file using the input file stream
            FileInputStream inStream=new FileInputStream(src);

            //Create an array that contains the available bytes in the given file
            //The type og array will be of type=bytes
            byte[] b=new byte[inStream.available()];

            //read all the bytes in the byte array b
            inStream.read(b);

            //calling the function createZip
            //passing the original byte array
            byte[] huffmanBytes=createZip(b);

            //creating the output stream
            OutputStream outStream=new FileOutputStream(dest);
            ObjectOutputStream objectOutStream=new ObjectOutputStream(outStream);
            objectOutStream.writeObject(huffmanBytes);
            objectOutStream.writeObject(huffmap);
            inStream.close();
            objectOutStream.close();
            outStream.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //This function creates the minimum priority queue
    public static byte[] createZip(byte bytes[]) throws Exception
    {
        MinPriorityQueue<ByteNode> nodes=getByteNodes(bytes);
        ByteNode root=createHuffmanTree(nodes);
        Map<Byte, String>huffmanCodes=getHuffCodes(root);
        byte[] huffmanCodeBytes=zipBytesWithCodes(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    //This function create a hashmap by creating a min priority queue out of original bytes
    private static MinPriorityQueue<ByteNode> getByteNodes(byte[] bytes)
    {
        MinPriorityQueue<ByteNode> nodes=new MinPriorityQueue<ByteNode>();
        Map<Byte, Integer> tempMap=new HashMap<>();
        for(byte b:bytes)
        {
            Integer value=tempMap.get(b);
            if(value==null)
            tempMap.put(b,1);
            else
            tempMap.put(b,value+1);
        }
        for(Map.Entry<Byte, Integer>entry:tempMap.entrySet())
        nodes.insert(new ByteNode(entry.getKey(),entry.getValue()));
        return nodes;
    }

    private static ByteNode createHuffmanTree(MinPriorityQueue<ByteNode> nodes) throws Exception
    {
        while(nodes.size()>1)
        {
            ByteNode left=nodes.poll();
            ByteNode right=nodes.poll();

            ByteNode parent=new ByteNode(null,left.frequency+right.frequency);
            parent.left=left;
            parent.right=right;

            nodes.insert(parent);
        }
        return nodes.poll();
    }

    private static Map<Byte, String> getHuffCodes(ByteNode root)
    {
        if(root==null)
        return null;

        getHuffCodes(root.left,"0",sb);
        getHuffCodes(root.right,"1",sb);

        return huffmap;
    }

    

    private static void getHuffCodes(ByteNode node, String code, StringBuilder sb1)
    {
        StringBuilder sb2=new StringBuilder(sb1);
        sb2.append(code);
        if(node!=null)
        {
            if(node.data==null)
            {
                getHuffCodes(node.left,"0",sb2);
                getHuffCodes(node.right,"1",sb2);
            }
            else
            huffmap.put(node.data,sb2.toString());
        }
    }

    private static byte[] zipBytesWithCodes(byte[] bytes,Map<Byte, String> huffCodes)
    {
        StringBuilder strBuilder=new StringBuilder();
        for(byte b:bytes)
        strBuilder.append(huffCodes.get(b));

        int length=(strBuilder.length()+7)/8;
        byte[] huffCodeBytes=new byte[length];
        int idx=0;
        for(int i=0;i<strBuilder.length();i+=8)
        {
            String strByte;
            if(i+8>strBuilder.length())
            strByte=strBuilder.substring(i);
            else
            strByte=strBuilder.substring(i,i+8);
            huffCodeBytes[idx]=(byte)Integer.parseInt(strByte,2);
            idx++;
        }
        return huffCodeBytes;
    }
}
