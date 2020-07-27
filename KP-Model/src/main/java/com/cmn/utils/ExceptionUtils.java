package com.cmn.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
	public static String exceptionToString(Exception e){
		String result = "";
		StringWriter errors = new StringWriter();
        PrintWriter printwriter = null;
        try{
            printwriter = new PrintWriter(errors);
            e.printStackTrace(printwriter);
            result = errors.toString();
        } catch(Exception ex){
            throw ex;
        } finally {
            try{
                if(null != printwriter){
                    printwriter.close();
                }
            } catch(Exception ex2){}
            try{
                if(null != errors){
                    errors.close();
                }
            } catch(Exception ex2){}
        }
        return result;
	}
}
