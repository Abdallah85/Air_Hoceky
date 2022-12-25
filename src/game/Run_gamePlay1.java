/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.opengl.*;
/**
 *
 * @author hp
 */
public class Run_gamePlay1 extends JFrame {

  /**
	 * 
	 */

public static void main(String[] args) {
    final Run_gamePlay1 app = new Run_gamePlay1();

    // show what we've done
/*    SwingUtilities.invokeLater (
      new Runnable() {
        public void run() {
          app.setVisible(true);
        }
      }
    );*/
  }

  public Run_gamePlay1() {
    //set the JFrame title
    super("Simple JOGL Application");

    //kill the process when the JFrame is closed
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //only three JOGL lines of code ... and here they are 
    GLCanvas glcanvas = new GLCanvas();
    glcanvas.addGLEventListener(new SimpleGLEventListener());
    
    // add the GLCanvas just like we would	 any Component
    add(glcanvas, BorderLayout.CENTER);
    setSize(800, 900);

    //center the JFrame on the screen
    this.setTitle("Game Play");
    this.setResizable(false);
    setLocationRelativeTo(this);
    setVisible(true);
  }
}
