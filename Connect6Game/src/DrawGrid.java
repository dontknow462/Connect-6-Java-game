

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Random;
import java.awt.event.MouseEvent;

public class DrawGrid {
    private JFrame frame;
    JLabel label;
    
    public DrawGrid() {
        frame = new JFrame("DrawGrid");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new MultiDraw(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
        
        this.label = new JLabel("LOLOLO");
        
        this.label.setBackground(Color.GREEN);
    }

    public static void main(String... argv) {
        new DrawGrid();
    }

    public  class MultiDraw extends JPanel  implements MouseListener {
       /// add static here
    	
    	int startX = 10;
        int startY = 10;
        int cellWidth = 40;
        int turn = 2;
        int rows = 8;
        int cols = 9;
      
       
      
        
        
       

        Color[][] grid = new Color[rows][cols];


        public MultiDraw(Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            //1. initialize array here
            int x = 0;
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                   grid[row][col]= new Color(255, 255, 255);
                   
                   //this loop creates circles based on rows and cols, however the problem is all are created in the same place.
//                  So it looks like only one is created
                }
            }
            
           
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Dimension d = getSize();
            g2.setColor(new Color(100, 0, 100)); //This is a background colour changer
            g2.fillRect(0,0,d.width,d.height);
            startX = 0;// this is a starting position of the first horizontal line (row)
            startY = 0;// this is a starting position of the first vertical line (column)
            


            //2) draw grid here
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid.length; col++) {
                  
                	g2.setColor(grid[row][col]);
                	g2.fillOval(startX, startY, cellWidth, cellWidth);
                	startX=startX +cellWidth; // space between the circles in X coordinate
                }

                startX=0; // this is so the grid lines up vertically, instead of starting each row(h) in ascending order 
                startY= startY+cellWidth; // space between the circles in y coordinate
            }

            g2.setColor(new Color(0, 255, 255));// color of the String below
           if(turn%2==0) { // this determines which player plays first 
        	   g2.drawString("Red's Turn"+ " ",400,20); // just the string and where its placed in the screen
           }else {
        	   g2.drawString("Yellow's Turn"+" ", 400, 20);
           }
             
          
           //Check winner after every move and print results accordingly
           int result = checkWinner();
           g2.setColor(Color.GREEN);
           if(result == 1)
        	   g2.drawString("Player has won the game", 400, 50);
           else if(result == 2)
        	   g2.drawString("Computer has won the game", 400, 50);
        }

       
        public void mousePressed(MouseEvent e) {

            int x = e.getX();/// this gets the exact location where mouse is clicked
            int y = e.getY();
            
          Random rand = new Random(); ///generates random number
          int ran = rand.nextInt(8);
          int ran1 = rand.nextInt(6);
            
            
// !!!!!!!! I can use random number on x axis and use the same ySpot for computer !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
            
            int xSpot = x/cellWidth;// by dividing pixel value, of where mouse was clicked, by given cellwidth value we can get the  simplified 
            int ySpot= y/cellWidth;//coordinates(4,2) of the circles which makes it easier to work instead of big numbers(90, 255)
//            System.out.println(x + " " +xSpot+ " "+ y+" " +ySpot);
            ySpot= testForOpenSpot(xSpot);
            
            int ySpot1= y/cellWidth;   // my stupid code             
            ySpot1= testComp(ran);
            
            
            
            if(ySpot<0) {
            	System.out.println("Not a Valid Entry");
            } else {
            	if(turn%2==0) {
               	  grid[ySpot][xSpot]=new Color(255, 0,0);// Player
               
               	  System.out.print(ySpot+" "+ xSpot);
                  }else {
                    grid[ySpot1][ran]=new Color(255,255,0); //Computer 
                  }
                  
               
//                 System.out.println(x + " " +xSpot+ " "+ y+" " +ySpot); // x/y(mouse coordinates) are different from x/Spots because 
                 //x,y value stays constant while ySpot value decreases; Also x/ySpot value starts from the bottom of the grid 
                  turn++;
            }
            
            repaint();//repaints THE OTHER  CIRCLES*************************_*********************
        }
        
        
        
       
        
        
        
        public int testForOpenSpot(int xSpot) {
        	int ySpot = rows-1;// 
        	while(!(grid[ySpot][xSpot].equals(new Color(255,255,255)) || ySpot<0)) { // It checks if the spot is non white or when ySpot is less than Zero(when the column is full)
        		ySpot--;// if it is non white then, it keeps going up 
        		
        	}

        	return ySpot;
        	
        }
     
        public int testComp(int ran) {
        	int ySpot1 = rows-1;// 
        	while(!(grid[ySpot1][ran].equals(new Color(255,255,255))||ySpot1<0)) { // It checks if the spot is non white or when ySpot is less than Zero(when the column is full)
        		ySpot1--;// if it is non white then, it keeps going up 
        		
        	}
        		
//        	}
        	return ySpot1;
        	
        }
        
        /**
         * Check if the game is over.
         * @return 1 if the Player has won the game and 2
         * if the computer has won the game, 0 if nobody won 
         * at this point
         */
        public int checkWinner() {
        	//Color objects for later comparsion
        	Color playerColor = new Color(255, 0, 0);
        	Color npcColor = new Color(255, 255, 0);
        	
        	//loop through the grid
        	for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if(grid[i][j].equals(playerColor)) { // if the coordinates match the player color
						if(checkDirections(i, j, playerColor)) {/// sends the coordinates and color and when the line match returns true(in this case 1)
							return 1;
						}
					}
					else if(grid[i][j].equals(npcColor)) {// if the coordinates match the player color
						if(checkDirections(i, j, npcColor)) {/// sends the coordinates and color and when the line match returns true(in this case 2)
							return 2;
						}
					}
				}
			}
        	
        	return 0;
        }
        
     
        private boolean checkDirections(int i, int j, Color color) {
        	//index out of bounds
        	if(!(i >= 0 && i < rows && j >= 0 && j < cols))//col-ver; row-hor
        		return false;
        		
        	int k = i, l = j;
        	int count = 0;
        	//check left
        	while(l >= 0) {
        		if(grid[k][l].equals(color)) {// for example, 9 is equal color then l-- happens, then while loop check 8, then the loop continues
        			count++;
        		}
        		//break iteration if no ovals were found
        		else break;
        		//move in that direction
        		l--;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check top
        	while(k >= 0) {
        		if(grid[k][l].equals(color)) {//for example, 9 is equal color then k-- happens, then while loop check 8, then the loop continues
        			count++;
        		}else break;
        		k--;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check left-top diagonal
        	while(k >= 0 && l >= 0) {
        		if(grid[k][l].equals(color)) {
        			count++;
        		}else break;
        		k--;// check the console to check the coordinates
        		l--;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check right-top diagonal
        	while(k >= 0 && l < cols) {
        		if(grid[k][l].equals(color)) {
        			count++;
        		}else break;
        		k--;
        		l++;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check right
        	while(l < cols) {
        		if(grid[k][l].equals(color)) {
        			count++;
        		}else break;
        		l++;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check right-bottom diagonal
        	while(k < rows && l < cols) {
        		if(grid[k][l].equals(color)) {
        			count++;
        		}else break;
        		k++;
        		l++;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check bottom side
        	while(k < rows) {
        		if(grid[k][l].equals(color)) {
        			count++;
        		}else break;
        		k++;
        	}
        	if(count >= 6) return true;
        	
        	count = 0;
        	k = i; l = j;
        	//check down-left diagonal
        	while(k < rows && l >= 0) {
        		if(grid[k][l].equals(color)) {
        			count++;
        		}else break;
        		k++;
        		l--;
        	}
        	if(count >= 6) return true;
        	
        	return false;
        }
        
        
   
        
        



        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseClicked(MouseEvent e) {

        }
    }
}