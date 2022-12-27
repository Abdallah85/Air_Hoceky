package game;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



//import Textures.TextureReader;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.BitSet;
import  com.sun.opengl.util.j2d.TextRenderer;


public class twoplayerMode extends JFrame implements MouseMotionListener, MouseListener {//, KeyListener {

    air_ listener = new air_();
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

class air_
        implements GLEventListener , KeyListener{
    GLUT g=new GLUT();
    int xp1 = 0;     // x1 for player1
    int yp1 =-225;  // y1 for player1


    int xp2=0;      //x1 for computer player
    int yp =220;   // y1 for computer player
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
    boolean movingUp= true;    // check ball will crash up or down
    boolean verticle =false;  // check slope not define is vertical if (x1-x0 =0)
    boolean up=false;        // if slope not define do it if y1>y0
    boolean down=false;     // if slope not define do it if y1<y0
    boolean play=false;    //  begin play if one player touch ball & to be false if game finished
    boolean computer_player=false; //who is 2player computer or player
    int scoreplayer1=0;
    int scoreplayer2=0;
    String textureNames[] = {"Man1.png","bullet.png", "Back.png"};
   // TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    TextRenderer renderer;
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        // GLU glu = new GLU();
        gl.glViewport(-250, -250, 250, 250);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-250.0, 250.0, -250.0, 250.0,1.0,-1.0);
        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 40));
    }


    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        drawplayer(gl,1,0,0,0,0,1,xp1,yp1);//player1
        ///////////////////////////
        drawplayer(gl,0,0,1,1,0,0,xp2,yp ); //player2
        ////////////////////////////
        handleKeyPress();


///////////////////////////
        winner(gl);
        // show the score on the screen
        gl.glRasterPos2i(-200, 10);
        g.glutBitmapString(5,Integer.toString(scoreplayer2));
        gl.glRasterPos2i(-200, -20);
        g.glutBitmapString(5,Integer.toString(scoreplayer1));

//
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


        if((int)Math.sqrt(Math.pow(x-xp2,2)+Math.pow(y-yp ,2))<=30){
            a=xp2;
            b=yp ;
            play=true;
            verticle=(x-xp2==0);
            if(verticle){
                if(b>y){
                    down=true;
                }else{
                    up=true;
                }
            }else{down=up=false;}
            slope=(y-yp )/(x-xp2);
            if(yp >y&&slope<0){
                movingUp=false;
                movingRight=true;
                x+=10;
            }
            if(yp >y&&slope>0){
                movingUp=false;
                movingRight=false;
                x-=10;
            }

            if(yp <y&&slope<0){
                movingUp=true;
                movingRight=false;
                x-=10;
            }

            if(yp <y&&slope>0){
                movingUp=true;
                movingRight=true;
                x+=10;
            }
        }


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
        gl.glColor3f(.3f,0.5f,.3f);
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
            gl.glVertex2d(cx, cy);
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
        if((x>-35&&x<35)&&y<=-235&&play){

            Again();
            scoreplayer2++;
        }
        if((x>-35&&x<35)&&y>=235&&play){

            Again();
            scoreplayer1++;
        }
        drawball(gl);

    }

    public void Again(){
        xp1 = 0;
        yp1 =-225;
        xp2=0;
        yp =220;

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
            if (yp > 20) {
                yp +=-3;
            }
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (yp < 220) {
                yp +=3;
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

}
