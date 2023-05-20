package com.example.demo;

import java.util.ArrayList;
public class Hamming
{
    private String data;
    private Parity parity;
    public static enum Parity {
        ODD,EVEN
    }
    //Constructor
    public Hamming(String data,Parity parity){
        this.data=data;
        this.parity=parity;
    }
    public static boolean isValid(String data){
        if(data.length()%8==0)
        {
            return true;
        }
        return false;
    }
    public static boolean isValidRecievedData(String recievedData)
    {
        try
        {
            Integer.parseInt(recievedData,2);
        }
        catch(NumberFormatException numberFormatException)
        {
            return false;
        }
        int checkBitsNo = recievedData.length()%8;
        int data = (int)Math.pow(2,checkBitsNo-1);
        if(data+checkBitsNo==recievedData.length())
            return true;
        return false;
    }
    public int GetCeckBitNum(){
        return (int)(Math.log(data.length())/Math.log(2)+1);
    }
    public double GetPerceOfTheCorrectErorSingnnnal(){
        return (double)GetCeckBitNum()/data.length()*100;
    }
    public double GetPercOffffCorrrrrErrrrorDoouubbleee(){
        return (double)(GetCeckBitNum()+1)/data.length()*100;
    }
    public String GetTranssssDataaaaaWithhhhhUnnnnnknows(){
         ArrayList<Character> transmittedDataList = new ArrayList<Character>();
        for(int i=0;i<data.length();i++)
            transmittedDataList.add(data.charAt(i));
         for(int i=0;i<GetCeckBitNum();i++)
            transmittedDataList.add((int)Math.pow(2,i)-1,'?');

         String TranDataWithUnknow = "";

        for(int i=0;i<transmittedDataList.size();i++)
            TranDataWithUnknow += transmittedDataList.get(i);
        return TranDataWithUnknow;
    }
    public static int countOnes(String setOfData){
        int countOnes=0;
        for(int i=0;i<setOfData.length();i++)
            if(setOfData.charAt(i)=='1')
                countOnes++;
        return countOnes;
    }
    public Character addedBit(String setOfData){
        if(parity==Parity.ODD)
        {
            if(countOnes(setOfData)%2==1)
                return '0';
            else
                return '1';
        }
        else if(parity==Parity.EVEN)
        {
            if(countOnes(setOfData)%2==1)
                return '1';
            else
                return '0';
        }
        return '?';
    }
    public ArrayList<String> getPs(){
        ArrayList<String> Ps = new ArrayList<String>();
        String TranDataWithUnknow=GetTranssssDataaaaaWithhhhhUnnnnnknows();

        for(int i=0;i<GetCeckBitNum();i++)
        {

            String subString = "";
            int displacement = (int)(Math.pow(2,i));
            for(int j=displacement-1;j<TranDataWithUnknow.length();j+=(displacement*2))
            {
                if(displacement<=TranDataWithUnknow.length()-j)
                    subString += TranDataWithUnknow.substring(j,j+displacement);
                else
                    subString += TranDataWithUnknow.substring(j);
            }
            Ps.add(subString);
        }
        return Ps;
    }
    public String GetTransData()
    {
        String TranDataWithUnknow=GetTranssssDataaaaaWithhhhhUnnnnnknows();
         ArrayList<Character> TranDataWithUnknowLis = new ArrayList<Character>();
        for(int i=0;i<TranDataWithUnknow.length();i++)
            TranDataWithUnknowLis.add(TranDataWithUnknow.charAt(i));

        for(int i=0;i<GetCeckBitNum();i++)
        {
            TranDataWithUnknowLis.remove((int)(Math.pow(2,i)-1));
            TranDataWithUnknowLis.add((int)(Math.pow(2,i)-1),addedBit(getPs().get(i)));
            System.out.println("iam here see me"+getPs().get(i));
        }

         String transmittedData = "";
        for(int i=0;i<TranDataWithUnknowLis.size();i++)
            transmittedData+=TranDataWithUnknowLis.get(i);
        return transmittedData;
    }
    public static int getErrorBit(String recievedData,Parity parity)
    {
        StringBuilder errorBit=new StringBuilder();
         for(int i=0;i<recievedData.length()%8;i++)
        {
            String subString = "";
            int displacement = (int)(Math.pow(2,i));
            for(int j=displacement-1;j<recievedData.length();j+=(displacement*2))
            {
                if(displacement<=recievedData.length()-j)
                    subString += recievedData.substring(j,j+displacement);
                else
                    subString += recievedData.substring(j);
            }
            if(parity==Parity.ODD)
            {
                if(countOnes(subString)%2==1)
                    errorBit.append('0');
                else
                    errorBit.append('1');
            }
            else if(parity==Parity.EVEN)
            {
                if(countOnes(subString)%2==1)
                    errorBit.append('1');
                else
                    errorBit.append('0');
            }
        }
        return Integer.parseInt(errorBit.reverse().toString(),2);
    }


    public void setData(String data){
        this.data=data;
    }
    public void setParity(Parity parity){
        this.parity=parity;
    }
    public String getData(){
        return data;
    }
    public Parity getParity(){
        return parity;
    }
}