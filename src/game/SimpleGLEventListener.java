

package game;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.*;

/**
 *
 * @author hp
 */
class SimpleGLEventListener implements GLEventListener {
    
  /**
   * Take care of initialization here.
   */
    
  public void init(GLAutoDrawable drawable) {
	  	GL gl = drawable.getGL();
	    
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	    
	    gl.glViewport(0, 0, 800, 900);
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glOrtho(0.0, 800.0, 0.0, 900.0, -1.0, 1.0);
  }
	
  /**
   * Take care of drawing here.
   */
  public void display(GLAutoDrawable drawable) {
		
		GL gl = drawable.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		gl.glPointSize(13.0f);
		
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		  
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2i ( 400,450);
		gl.glEnd();
		
                gl.glPointSize(33.0f);
                
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		  
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2i ( 400,225);
		gl.glEnd();
		
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		  
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2i ( 400,675);
		gl.glEnd();

  }
    
  /**
   * Called when the GLDrawable (GLCanvas 
   * or GLJPanel) has changed in size. We 
   * won't need this, but you may eventually 
   * need it -- just not yet.
   */
  public void reshape(
                        GLAutoDrawable drawable, 
                        int x, 
                        int y, 
                        int width, 
                        int height
                      ) {}
	
  /**
   * If the display depth is changed while the 
   * program is running this method is called.
   * Nowadays this doesn't happen much, unless 
   * a programmer has his program do it.
   */
  public void displayChanged(
                              GLAutoDrawable drawable, 
                              boolean modeChanged, 
                              boolean deviceChanged
                            ) {}

public void dispose(GLAutoDrawable arg0) {
	// TODO Auto-generated method stub
	
}

}
