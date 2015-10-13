/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gesture_control_droid;
import jssc.*;

import com.googlecode.javacpp.*;
import com.googlecode.javacv.cpp.opencv_core.CvArr;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvOr;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvScalar;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_ANY;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_WIDTH;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvCreateCameraCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvQueryFrame;
import static com.googlecode.javacv.cpp.opencv_highgui.cvReleaseCapture;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSetCaptureProperty;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import com.googlecode.javacv.cpp.opencv_imgproc.CvMoments;


/**
 *
 * @author Kunwar Gaurav S R
 */
public class main_frame extends javax.swing.JFrame {
static String port;
    /**
     * Creates new form main_frame
     */
    public main_frame() {
        this.setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cb = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        cam = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("GESTURE CONTROLLED DROID");

        jLabel2.setText("Select device port");

        jButton1.setText("Connect");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

        cam.setText("Camera");
        cam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                camActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cb, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jButton1)
                        .addGap(42, 42, 42)
                        .addComponent(cam))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(cam))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(568, 440));
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
 if(cb.getSelectedItem()!=null)
 {
         port = cb.getSelectedItem().toString();
 SerialPort serialPort = new SerialPort(port);
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            serialPort.writeString("a");//Write data to port
            txt.append("--> Device Connected! \n");
  //cam.setEnabled(true);
            serialPort.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            txt.append("--> Device not connected! \n");
            System.out.println(ex);
        }
        }
 else 
 {txt.append("--> No Serial Port Selected! \n");
 }

    }                                        

    private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  

        try{
      String ports[] = SerialPortList.getPortNames();
     if(ports.length!=0)
     {
      for(int i=0;i<ports.length;i++)
        cb.addItem(ports[i]);
      txt.append("--> Serial Ports Listed ! \n"); 
     }
     else txt.append("--> No Serial Ports found! \n");
     }
        catch(NullPointerException e)
        {System.out.println(e);
            txt.setText("--> Error Occured!");
        }        // TODO add your handling code here:
    }                                 

    private void camActionPerformed(java.awt.event.ActionEvent evt) {                                    
        IplImage img1,imgbinB,imgbinG;
        IplImage imghsv;
        CvScalar Bminc = cvScalar(95,150,75,0),Bmaxc = cvScalar(145,255,255,0);
        CvScalar Gminc = cvScalar(40,50,60,0),Gmaxc = cvScalar(80,255,255,0);
        CvArr mask;
        int w =320,h=240;
        imghsv = cvCreateImage(cvSize(w,h),8,3);
        imgbinG = cvCreateImage(cvSize(w,h),8,1);
        imgbinB = cvCreateImage(cvSize(w,h),8,1);
        IplImage imgc = cvCreateImage(cvSize(w,h),8,1);
        CvSeq contour1 =new CvSeq();
        CvSeq contour2 = null;
        CvMemStorage storage = CvMemStorage.create();
        CvMoments moments = new CvMoments(Loader.sizeof(CvMoments.class));
        CvCapture capture1 = cvCreateCameraCapture(CV_CAP_ANY);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_WIDTH,w);
        cvSetCaptureProperty(capture1,CV_CAP_PROP_FRAME_HEIGHT,h);
        while(true)
        {
            try {
                img1 = cvQueryFrame(capture1);
                if(img1==null)
                {
                    System.err.println("NO IMAGE");
                txt.append("--> No Image from Camera! \n");
                    break;
                }
                imgbinB = ccmFilter.Filter (img1,imghsv,imgbinB,Bmaxc,Bminc,contour1,contour2,storage,moments,1,0);
                imgbinG = ccmFilter.Filter(img1,imghsv,imgbinG,Gmaxc,Gminc,contour1,contour2,storage,moments,0,1);
                
                cvOr(imgbinB,imgbinG,imgc,mask=null);
                cvShowImage("colour_detect",imgc);
                cvShowImage("REAL",img1);
                
                char c = (char)cvWaitKey(15);
                if(c==27)
                    break;
            } catch (Exception ex) {
               // Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println(ex);
            }
        }
        
      cvReleaseImage(imghsv);
      cvReleaseImage(imgbinG);
      cvReleaseImage(imgbinB);
      cvReleaseImage(imghsv);
      cvReleaseMemStorage(storage);
      cvReleaseCapture(capture1);
              // TODO add your handling code here:
    }                                   

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main_frame().setVisible(true);
               
            }
        });
        
    
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton cam;
    private javax.swing.JComboBox cb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea txt;
    // End of variables declaration                   
}