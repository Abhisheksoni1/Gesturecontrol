/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gesture_control_droid;
import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_FILLED;
import static com.googlecode.javacv.cpp.opencv_core.CV_RGB;
import static com.googlecode.javacv.cpp.opencv_core.CV_WHOLE_SEQ;
import static com.googlecode.javacv.cpp.opencv_core.cvDrawContours;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import com.googlecode.javacv.cpp.opencv_imgproc;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_LINK_RUNS;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_RETR_LIST;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvContourArea;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvFindContours;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetCentralMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvGetSpatialMoment;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvMoments;
import java.awt.AWTException;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Kunwar Gaurav S R
 */
public class ccmFilter {
    public static int t; 
               
          
   public static int xpos ,ypos, res_posx=0,res_posy=0;  
    public static opencv_core.IplImage Filter(opencv_core.IplImage img, opencv_core.IplImage imghsv,opencv_core.IplImage imgbin, opencv_core.CvScalar maxc,opencv_core.CvScalar minc,opencv_core.CvSeq contour1, opencv_core.CvSeq contour2,opencv_core.CvMemStorage storage, opencv_imgproc.CvMoments moments, int b, int g ) throws AWTException
    {
      double moment10,moment01, areamax,areac=0, m_area;
      int posx=0, posy=0;
      float pos0=0;
      
      cvCvtColor(img,imghsv,CV_BGR2HSV);
      cvInRangeS(imghsv,minc,maxc,imgbin);
      
      areamax = 1000;
      
      cvFindContours(imgbin,storage,contour1,Loader.sizeof(opencv_core.CvContour.class),CV_RETR_LIST,CV_LINK_RUNS,cvPoint(0,0));
      
        contour2 = contour1;
         while(contour1 !=null && !contour1.isNull())
         {
             areac = cvContourArea(contour1,CV_WHOLE_SEQ,1);
             if(areac>areamax)
             {
                 areamax = areac;
             }   
                 contour1 = contour1.h_next();
         }
        while(contour2 !=null && !contour2.isNull())
        {
          areac = cvContourArea(contour2,CV_WHOLE_SEQ,1);
          
          if(areac<areamax) 
          {
            cvDrawContours(imgbin,contour2,CV_RGB(0,0,0),CV_RGB(0,0,0),0,CV_FILLED,8,cvPoint(0,0));
            
          }
          contour2=contour2.h_next();
          
        }
        
            cvMoments(imgbin,moments,1);
            
            moment10 = cvGetSpatialMoment(moments,1,0);
            moment01 = cvGetSpatialMoment(moments,0,1);
            m_area = cvGetCentralMoment(moments,0,0);
            pos0 =(float) (m_area/m_area); 
            posx= (int) (moment10/m_area);
            posy= (int) (moment01/m_area);
           res_posx = posx - xpos;
           res_posy = posy - ypos;
         //  Display x = new Display();
           if(b==1)
           { 
             if(posx>0 && posy>0)
             {
                   if(res_posx<100)
                  {  
                 
                      System.out.println("RIGHT MOVED");
                     send_data("r");
                  }
                 if(res_posx>=220  )
                 {  
                     System.out.println("MOVE LEFT ");
                     send_data("l");
       
                  }
         }
           }
        if(g==1)
           { 
             if(posx>0 && posy>0)
                   
             { 
                 if(res_posy<(80))
                 {   
                   System.out.println("FORWARD MOVED");
              send_data("f");
                 }
             
           
             if(res_posy>=150)
               { 
                     System.out.println("BACKWARD");
                                 send_data("b");
       
                   
               }
             }
           } 
            
                  
        else  
        {
                            send_data("bs");
       
           
            
        }  
        xpos = posx;
        ypos = posy;
        
        
        return imgbin; 
           
        
   
    
    }
    
    
    static void send_data(String message)
    {
       if(main_frame.port!=null)
       { 
    SerialPort serialPort = new SerialPort(main_frame.port);
     try {
         serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            serialPort.writeBytes(message.getBytes());//Write data to port
            serialPort.closePort();//Close serial port
         System.out.println("complete");
         
     }
     catch (SerialPortException ex) {
         System.out.println(ex);
         
     }
     
       }
      // else txt.append("--> No Serial Port! \n");
         
     
    }
    
    
    
}
