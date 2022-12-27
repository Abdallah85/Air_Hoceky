/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
//import Textures.TextureReader;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import java.awt.BorderLayout;
import javax.media.opengl.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.BitSet;
import com.sun.opengl.util.j2d.TextRenderer;
import Textures.AnimListener;
import Textures.TextureReader;                        ////////////////////////////
import java.io.IOException;
import javax.media.opengl.glu.GLU;


public class HardGamePlayer extends JFrame implements MouseMotionListener, MouseListener {
     air_ listener = new air_();
    GLCanvas glcanvas;
  static Animator anim;

    public static void main(String[] args) {

      HardGamePlayer app = new HardGamePlayer();
        
    }

    public HardGamePlayer() {
        super("Air_Hocky");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(this);
        glcanvas.addMouseMotionListener(this);
        glcanvas.addKeyListener(listener);
        anim =new FPSAnimator(glcanvas,100);
        anim.start();
        setResizable(false);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 900);
        setLocationRelativeTo(null);
        setVisible(true);
                   
    
     
    }

 
    @Override
    public void mouseClicked(MouseEvent e) {
   
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        throw new UnsupportedOperationException("hhh"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
   
    }
        class air_
    implements GLEventListener , KeyListener{
        GLUT g=new GLUT();
    int xPosition = 0;     // x1for player1
    int yPosition =-225;  // y1 for player1
   

    int xPosition2=0;      //x1 for computer player 
    int yPosition2=220;   // y1 for computer player
    double cx,cy;  // vertices for draw cycle
    final double ONE_DEGREE = (Math.PI / 180);
    final double THREE_SIXTY = 2 * Math.PI;
    double radius1 =20;   // big cycle
    double radius2 =10;   // small cycle && ball
    double radius3 =100;  // ground cycle
    float a = 0;         //x0 axis
    float b = 0;        //y0 axis
    float slope =0;    // slope betwen ball and playr
    float x= a;       //holds the new 'x' position of ball
    float y = b;     //holds the new 'y' position
    boolean movingRight= true;  // check ball x1 increase or decrease
    boolean movingUp= true;    // check ball will crash up or down
    boolean verticle =false;  // check slope not define is vertical if (x1-x0 =0) 
    boolean up=false;        // if slope not define do it if y1>y0
    boolean down=false;     // if slope not define do it if y1<y0
// if slope not define do it if y1<y0
    boolean play=false; //who is 2player computer or player
        boolean computer_player=true; //who is 2player computer or player 

     //  begin play if one player touch ball & to be false if game finished
    int scoreplayer1=0;
    int scoreplayer2=0;
    
    
            String textureNames[] = {"Ball.png","Rpaddle.png","Bpaddle.png", "Stadium.png"};////////////////////////
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    //
    
//    TextRenderer renderer;
      TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 10));
   
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
       // GLU glu = new GLU();
        gl.glViewport(-250, -250, 250, 250);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
         gl.glOrtho(-250.0, 250.0, -250.0, 250.0,1.0,-1.0);
         
         
           gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);	
        gl.glGenTextures(textureNames.length, textures, 0);
        
        for(int i = 0; i < textureNames.length; i++){
            try {
                texture[i] = TextureReader.readTexture(textureNames[i] , true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA, // Internal Texel Format,
                    texture[i].getWidth(), texture[i].getHeight(),
                    GL.GL_RGBA, // External format from image,
                    GL.GL_UNSIGNED_BYTE,
                    texture[i].getPixels() // Imagedata
                    );
            } catch( IOException e ) {
              System.out.println(e);
              e.printStackTrace();
            }
        }
       
    }

    
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);         
        
        DrawBackground(gl);
        draw_ground(gl,1,1,1);
        
         drawplayer(gl,1,0,0,0,0,1,xPosition,yPosition);//player1
          ///////////////////////////
         drawplayer(gl,0,0,1,1,0,0,xPosition2,yPosition2); //player2
          ////////////////////////////
       //      drawplayer(gl,0,0,1,1,0,0,xPosition,220);  // computer player
         //   computer_control_easy(); 
                         //computer_control_medium(); 
                                   computer_control_hard(); 


          winner(gl);
            handleKeyPress(); 

 
///////////////////////////
          winner(gl);

    
//        
    }      
    
     public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);	
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);
        // Turn Blending On

        gl.glPushMatrix();
            gl.glBegin(GL.GL_QUADS);
            // Front Face
                gl.glTexCoord2f(0.0f, 0.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(1.0f, 0.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glTexCoord2f(1.0f, 1.0f);
                gl.glVertex3f(1.0f, 1.0f, -1.0f);
                gl.glTexCoord2f(0.0f, 1.0f);
                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
            gl.glEnd();
        gl.glPopMatrix();
        
        gl.glDisable(GL.GL_BLEND);
        
        gl.glColor3f(126, 200, 80);
        gl.glBegin(GL.GL_QUADS);
        gl.glVertex2f(-1.0f, -1.0f);
        gl.glVertex2f(1.0f, -1.0f);
        gl.glVertex2f(1.0f, 1.0f);
        gl.glVertex2f(-1.0f, 1.0f);
        gl.glEnd();
    }
    
    public void reshape(
            GLDrawable drawable,
            int x,
            int y,
            int width,
            int height
    ) {
    }

    public void displayChanged(
            GLDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged
    ) {
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }

    @Override
    public void displayChanged(GLAutoDrawable glad, boolean bln, boolean bln1) {
    }
    
    public void drawball(GL gl){
          // player 1
         if((int)Math.sqrt(Math.pow(x-xPosition,2)+Math.pow(y-yPosition,2))<=30){
             a=xPosition;
             b=yPosition;
             play=true;
            verticle=(x-xPosition==0);
             if(verticle){
                if(b>y){
                  down=true;
                }else{
                 up=true;   
                }
             }else{down=up=false;}
             slope=(y-yPosition)/(x-xPosition);
             if(yPosition>y&&slope<0){
                 movingUp=false;
                 movingRight=true;
                x+=10;
             }
             if(yPosition>y&&slope>0){
                movingUp=false;
                movingRight=false;
                x-=10;
             } 
             
             if(yPosition<y&&slope<0){
                 movingUp=true;
                 movingRight=false;
                x-=10;
             }
             
             if(yPosition<y&&slope>0){
                 movingUp=true;
                 movingRight=true;
                x+=10;
             }
         }
         
          // computer player
           if(computer_player){
               
              if((int)Math.sqrt(Math.pow(x-xPosition2,2)+Math.pow(y-yPosition2,2))<=30){
                    a=xPosition2;
                    b=yPosition2;
                    play=true;
                   verticle=(x-xPosition2==0);
                    if(verticle){
                       if(b>y){
                         down=true;
                       }else{
                        up=true;   
                       }
                    }else{down=up=false;}
                    slope=(y-yPosition2)/(x-xPosition2);
                    if(yPosition2>y&&slope<0){
                        movingUp=false;
                        movingRight=true;
                       x+=10;
                    }
                    if(yPosition2>y&&slope>0){
                       movingUp=false;
                       movingRight=false;
                       x-=10;
                    } 

                    if(yPosition2<y&&slope<0){
                        movingUp=true;
                        movingRight=false;
                       x-=10;
                    }

                    if(yPosition2<y&&slope>0){
                        movingUp=true;
                        movingRight=true;
                       x+=10;
                    }
               } 
           }
          // player2
       
//          
//            if((int)Math.sqrt(Math.pow(x-xPosition2,2)+Math.pow(y-yPosition2,2))<=30){
//                    a=xPosition2;
//                    b=yPosition2;
//                    play=true;
//                   verticle=(x-xPosition2==0);
//                    if(verticle){
//                       if(b>y){
//                         down=true;
//                       }else{
//                        up=true;   
//                       }
//                    }else{down=up=false;}
//                    slope=(y-yPosition2)/(x-xPosition2);
//                    if(yPosition2>y&&slope<0){
//                        movingUp=false;
//                        movingRight=true;
//                       x+=10;
//                    }
//                    if(yPosition2>y&&slope>0){
//                       movingUp=false;
//                       movingRight=false;
//                       x-=10;
//                    } 
//
//                    if(yPosition2<y&&slope<0){
//                        movingUp=true;
//                        movingRight=false;
//                       x-=10;
//                    }
//
//                    if(yPosition2<y&&slope>0){
//                        movingUp=true;
//                        movingRight=true;
//                       x+=10;
//                    }
//           } 
          
         /////
        if(play){ 
            if(!verticle){
              y = (slope * (x - a) + b);
            }

           if (movingRight) {
               if (x < 235) {
                   x += 1;
               } else {
                   movingRight = false;
                   slope *= -1;
                   a = x;
                   b = y;
               }
           }
           if (!movingRight) {
               if (x >-235) {
                   x -= 1;
               } else {
                   movingRight = true;
                   slope *= -1;
                   a = x;
                   b = y;
               }
           }

           if (movingUp) {
               if (!(y < 235)) {
                   slope *= -1;
                   a = x;
                   b =y;
                   movingUp = false;
               }
           }
           if (!movingUp) {
               if (!(y > -235)) {
                   slope *= -1;
                   a = x;
                   b = y;
                   movingUp = true;
               }
           }

           if(down){
            y--;
            if(y<=-235)
              up=true;
              down=false;
           }

           if(up){
             y++;
             if(y>=235)
              down=true;
             up=false;
           }
        }   
        gl.glColor3f(255,255,0);
        gl.glBegin(GL.GL_POLYGON);
        for (double i = 0; i < THREE_SIXTY; i += ONE_DEGREE){
               cx = radius2 * (Math.cos(i))+x;
               cy= radius2 * (Math.sin(i))+y;
               gl.glVertex2d(cx, cy);
        }
        gl.glEnd(); 
       
    }  

    public void drawplayer(GL gl,float red1,float green1,float blue1,float red2,float green2,float blue2,double x,double y){
           // draw big circle for player  
               gl.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_FILL);
               gl.glColor3f(red1, green1, blue1);
               gl.glBegin(GL.GL_POLYGON);
               for (double aa = 0; aa < THREE_SIXTY; aa += ONE_DEGREE){
                     cx = radius1 * (Math.cos(aa))+x;
                     cy = radius1 * (Math.sin(aa))+y;
                     gl.glVertex2d(cx,cy);
               }
               gl.glEnd();
               // draw small circle for player 
//               gl.glColor3f(red2, green2, blue2);
//               gl.glBegin(GL.GL_POLYGON);
//               for (double aa = 0; aa < THREE_SIXTY; aa += ONE_DEGREE){
//                     cx = radius2 * (Math.cos(aa))+x;
//                     cy = radius2 * (Math.sin(aa))+y;
//                     gl.glVertex2d(cx, cy);
//               }
//               gl.glEnd();
    }
    
//    public void draw_ground(GL gl,float red,float green,float blue){
//          
//           
//          
//    }
//    
    public void winner(GL gl){
      if((x>-90&&x<90)&&y<=-235&&play){
        
           Again();
           scoreplayer2++;
      }
      if((x>-90&&x<90)&&y>=235&&play){
         
          Again();
          scoreplayer1++;
      } 
           drawball(gl);
   
    }
    
    public void Again(){
     xPosition = 0;     
     yPosition =-225;  
    xPosition2=0;        
    yPosition2=220;     
       
     a = 0;         
     b = 0;        
    slope =0;    
    x= a;       
    y = b;     
    movingRight= true;  
    movingUp= true;    
    verticle =false;   
    up=false;        
    down=false;   
    play=false; 
    }
    
//    public void computer_control_easy(){
//
//    
//         if(Math.pow(yPosition2-y,2)>=10*10){
//            if(y>yPosition2)
//              if(yPosition2<225)
//              yPosition2+=1;
//           if(y<yPosition2)
//             if(yPosition2>20)
//             yPosition2+=-1;
//         }
//        if(y>225&&x>0){
//          xPosition2+=-1;
//        }
//         if(y>225&&x<0){
//            xPosition2+=1;
//         }
//         if((x>225||x<-225)&&y>10)
//             xPosition2+=1;
//         if(Math.pow(xPosition2-x,2)>=10*10){
//            if(x>xPosition2)
//              if(xPosition2<200)
//              xPosition2+=1;
//           if(x<xPosition2)
//             if(xPosition2>-200)
//             xPosition2+=-1;
//       
////        
//}
////    
//    }
//        public void computer_control_medium(){
//            
//  if(Math.pow(yPosition2-y,2)>=10*10){
//            if(y>yPosition2)
//              if(yPosition2<225)
//              yPosition2+=2;
//           if(y<yPosition2)
//             if(yPosition2>20)
//             yPosition2+=-2;
//         }
//        if(y>225&&x>0){
//          xPosition2+=-2;
//        }
//         if(y>225&&x<0){
//            xPosition2+=2;
//         }
//         if((x>225||x<-225)&&y>10)
//             xPosition2+=2;
//         if(Math.pow(xPosition2-x,2)>=10*10){
//            if(x>xPosition2)
//              if(xPosition2<200)
//              xPosition2+=2;
//           if(x<xPosition2)
//             if(xPosition2>-200)
//             xPosition2+=-2;
//       
//        }
//        }
                public void computer_control_hard(){
                    
                 if(Math.pow(yPosition2-y,2)>=10*10){
            if(y>yPosition2)
              if(yPosition2<225)
              yPosition2+=3;
           if(y<yPosition2)
             if(yPosition2>20)
             yPosition2+=-3;
         }
        if(y>225&&x>0){
          xPosition2+=-3;
        }
         if(y>225&&x<0){
            xPosition2+=3;
         }
         if((x>225||x<-225)&&y>10)
             xPosition2+=3;
         if(Math.pow(xPosition2-x,2)>=10*10){
            if(x>xPosition2)
              if(xPosition2<200)
              xPosition2+=3;
           if(x<xPosition2)
             if(xPosition2>-200)
             xPosition2+=-3;
         }
                
                }
                

     public void handleKeyPress() {

       
       // control player1
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xPosition > -220) {
                xPosition+=-3; 
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xPosition < 220) {
                xPosition+=3; 
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (yPosition > -220) {
                yPosition+=-3; 
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (yPosition < -20) {
                yPosition+=3; 
            }
        } if (isKeyPressed(KeyEvent.VK_ENTER)) {
            System.exit(0);
        }
        
       
        
    }

    public BitSet keyBits = new BitSet(256);
 
 
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    } 
 

    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    } 
  
    public void keyTyped(final KeyEvent event) {
        // don't care 
    } 
 
    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

  public void draw_ground(GL gl,float red,float green,float blue){
            gl.glLineWidth(5f);
            //draw ground
            gl.glColor3f(red, green, blue);
            gl.glBegin(GL.GL_LINES);
            gl.glVertex2f(245,-245);
            gl.glVertex2f(245,245);
            
            gl.glVertex2f(-245,-245);
            gl.glVertex2f(-245,245);
            
            gl.glVertex2f(245,245);
            gl.glVertex2f(100,245);
            
            gl.glVertex2f(-100,245);
            gl.glVertex2f(-245,245);
            
            gl.glVertex2f(-100,-245);
            gl.glVertex2f(-245,-245);
            
            gl.glVertex2f(100,-245);
            gl.glVertex2f(245,-245); 
            
            gl.glVertex2f(-245,0);
            gl.glVertex2f(245,0);
            
             gl.glVertex2f(100,245);
            gl.glVertex2f(100,200); 
            
             gl.glVertex2f(-100,245);
            gl.glVertex2f(-100,200);
            
             gl.glVertex2f(-100,200);
            gl.glVertex2f(100,200);
            
              gl.glVertex2f(-100,-245);
            gl.glVertex2f(-100,-200);
            
             gl.glVertex2f(-100,-200);
            gl.glVertex2f(100,-200);
            
            gl.glVertex2f(100,-245);
            gl.glVertex2f(100,-200);
            
            gl.glEnd();
            
            gl.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_LINE);
             gl.glBegin(GL.GL_POLYGON);
            for (double aa = 0; aa < THREE_SIXTY; aa += ONE_DEGREE){
                  cx = radius3 * (Math.cos(aa));
                  cy = radius3 * (Math.sin(aa));
                  gl.glVertex2d(cx, cy);
            }
            gl.glEnd();
    }

}
}
