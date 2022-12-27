package game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


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

public class twoplayerMode extends JFrame implements MouseMotionListener, MouseListener {//, KeyListener {


    air_1 listener = new air_1();
    GLCanvas glcanvas;
    static Animator anim;

    public static void main(String[] args) {

        twoplayerMode app = new twoplayerMode();

    }

    public twoplayerMode() {
        super("2-player-mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(this);
        glcanvas.addMouseMotionListener(this);
        glcanvas.addKeyListener(listener);
        anim =new FPSAnimator(glcanvas,200);
        anim.start();
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(800, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
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
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}


class air_1 implements GLEventListener , KeyListener{
    GLUT g=new GLUT();
    int xp1 = 0;     // x1 for player1
    int yp1 =-225;  // y1 for player1


    int xp2=0;      //x1 for computer player
    int yp2=220;   // y1 for computer player
    double cx,cy;  // vertices for draw cycle
    final double ONE_DEGREE = (Math.PI / 180);
    final double THREE_SIXTY = 2 * Math.PI;
    double radius1 =20;   // big cycle
    double radius2 =10;   // small cycle && ball
    double radius3 =100;  // ground cycle
    float a = 0;         //x0 axis
    float b = 0;        //y0 axis
    float slope =0;    // slpe betwen ball and playr
    float x= a;       //holds the new 'x' position of ball
    float y = b;     //holds the new 'y' position
    boolean movingRight= true;  // check ball x1 increase or decrease
    boolean movingUp= true;
    boolean verticle =false;
    boolean up=false;
    boolean down=false;
    boolean play=false;

    int scoreplayer1=0;
    int scoreplayer2=0;
    
    String massege;
    
        // pictures
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
//        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 40));
        
        //pictures
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
//        gl.glLoadIdentity();

        DrawBackground(gl);
        draw_ground(gl,1,1,1);

        
        drawplayer(gl,1,0,0,0,0,1,xp1,yp1,1);//player1
        ///////////////////////////

        drawplayer(gl,0,0,1,1,0,0,xp2,yp2,2); //player2

        ////////////////////////////
        handleKeyPress();

        gl.glColor3f(255, 255, 0);
///////////////////////////
        winner(gl);
        // show the score on the screen
        gl.glRasterPos2i(-200, 10);
        g.glutBitmapString(5,Integer.toString(scoreplayer2));
        gl.glRasterPos2i(-200, -20);
        g.glutBitmapString(5,Integer.toString(scoreplayer1));

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
        if((int)Math.sqrt(Math.pow(x-xp1,2)+Math.pow(y-yp1,2))<=30){
            a=xp1;
            b=yp1;
            play=true;
            verticle=(x-xp1==0);
            if(verticle){
                if(b>y){
                    down=true;
                }else{
                    up=true;
                }
            }else{down=up=false;}
            slope=(y-yp1)/(x-xp1);
            if(yp1>y&&slope<0){
                movingUp=false;
                movingRight=true;
                x+=10;
            }
            if(yp1>y&&slope>0){
                movingUp=false;
                movingRight=false;
                x-=10;
            }

            if(yp1<y&&slope<0){
                movingUp=true;
                movingRight=false;
                x-=10;
            }

            if(yp1<y&&slope>0){
                movingUp=true;
                movingRight=true;
                x+=10;
            }
        }
        // player2


        if((int)Math.sqrt(Math.pow(x-xp2,2)+Math.pow(y-yp2,2))<=30){
            a=xp2;
            b=yp2;
            play=true;
            verticle=(x-xp2==0);
            if(verticle){
                if(b>y){
                    down=true;
                }else{
                    up=true;
                }
            }else{down=up=false;}
            slope=(y-yp2)/(x-xp2);
            if(yp2>y&&slope<0){
                movingUp=false;
                movingRight=true;
                x+=10;
            }
            if(yp2>y&&slope>0){
                movingUp=false;
                movingRight=false;
                x-=10;
            }

            if(yp2<y&&slope<0){
                movingUp=true;
                movingRight=false;
                x-=10;
            }

            if(yp2<y&&slope>0){
                movingUp=true;
                movingRight=true;
                x+=10;
            }
        }

        //the bounce from
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

    public void drawplayer(GL gl,float red1,float green1,float blue1,float red2,float green2,float blue2,double x,double y , int index){
        // draw big circle for player
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK,GL.GL_FILL);
        gl.glColor3f(red1, green1, blue1);
        gl.glBegin(GL.GL_POLYGON);
        for (double aa = 0; aa < THREE_SIXTY; aa += ONE_DEGREE){
            cx = radius1 * (Math.cos(aa))+x;
            cy = radius1 * (Math.sin(aa))+y;
            gl.glVertex2d(cx, cy);
        }
        
//                gl.glEnable(GL.GL_BLEND);
//        
//        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
//
//        gl.glPushMatrix();
//            gl.glTranslated( (x-radius1) /(x+radius1) , (y-radius1)/(y+radius1) , 0);
//            gl.glScaled(0.1, 0.1, 1);
//            gl.glBegin(GL.GL_QUADS);
//            // Front Face
//                gl.glTexCoord2f(0.0f, 0.0f);
//                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
//                gl.glTexCoord2f(1.0f, 0.0f);
//                gl.glVertex3f(1.0f, -1.0f, -1.0f);
//                gl.glTexCoord2f(1.0f, 1.0f);
//                gl.glVertex3f(1.0f, 1.0f, -1.0f);
//                gl.glTexCoord2f(0.0f, 1.0f);
//                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
//            gl.glEnd();
//        gl.glPopMatrix();
//        
//        gl.glDisable(GL.GL_BLEND);
        
        gl.glEnd();

    }


    //
    public void winner(GL gl){
        if((x>-100&&x<100)&&y<=-235&&play){

            Again();
            scoreplayer2++;
        }
        if((x>-100&&x<100)&&y>=235&&play){

            Again();
            scoreplayer1++;
        }
        drawball(gl);


        if(scoreplayer1>=1){
            play = true;
            massege = "player 1 win";



        }
        if (scoreplayer2>=1){
            //play = false;
            massege = "player 2 win";
            displayMassg(gl , massege);
        }
    }
    ////to show massege for the winner
    void displayMassg(GL gl, String massege){

        gl.glPushMatrix();
        renderer.beginRendering(10, 10);
        renderer.draw(massege, 20,20);
        renderer.endRendering();
        gl.glPopMatrix();

    }

    public void Again(){
        xp1 = 0;
        yp1 =-225;
        xp2=0;
        yp2=220;

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



    public void handleKeyPress() {
        // control player2
        if (isKeyPressed(KeyEvent.VK_A)) {
            if (xp2>-220) {
                xp2+=-3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            if (xp2< 220 ) {
                xp2+=3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            if (yp2> 20) {
                yp2+=-3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (yp2< 220) {
                yp2+=3;
            }
        }

        // control player1
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xp1 > -220) {
                xp1+=-3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xp1 < 220) {
                xp1+=3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (yp1 > -220) {
                yp1+=-3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (yp1 < -20) {
                yp1+=3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_O)) {
            
        }
        
        if (isKeyPressed(KeyEvent.VK_ESCAPE)){
            System.exit(0);
        }
        if (isKeyPressed(KeyEvent.VK_SPACE)){
            play = true;
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
