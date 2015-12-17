package com.megha.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class MyIOUnit {

    protected List<String> tokens = new ArrayList<String>();

    public void read(InputStream input) throws IOException {
        StringBuilder builder = new StringBuilder();

        int data = input.read();
        while(data != -1){
            if(((char)data) != ','){
                builder.append((char) data);
            } else {
                this.tokens.add(builder.toString().trim());
                builder.delete(0, builder.length());
            }

            data = input.read();
        }
    }
    
    public void write(OutputStream output) throws IOException {
            for(int i=0; i<tokens.size(); i++){
                if(i>0){
                    output.write(',');
                }
                output.write(tokens.get(i).getBytes());
           }
        }
        
    public static void main(String[] args){
    	MyIOUnit unit = new MyIOUnit();
    	
    	//Test input stream components
    	byte[] data = "Megha Iyer,9425 Meyrick Park Trl, Austin,TX, 78717, Ph-6033206468,".getBytes();
    	InputStream input = new ByteArrayInputStream(data);
    	try {
			unit.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(unit.tokens.get(0));
    	System.out.println(unit.tokens.get(1));
    	System.out.println(unit.tokens.get(2));
    	System.out.println(unit.tokens.get(3));
    	System.out.println(unit.tokens.get(4));
    	System.out.println(unit.tokens.get(5));
    	
    	//Test output stream components
    	
    	OutputStream output = new ByteArrayOutputStream();
    	
    	unit.tokens.add("core java");
    	unit.tokens.add("servlets");
    	unit.tokens.add("jsp");
    	unit.tokens.add("hibernate");
    	unit.tokens.add("junit");
    	
    	try {
			unit.write(output);
			System.out.println(output.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    
}