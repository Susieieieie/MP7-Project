package gobang;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class gobang extends JFrame{

	JPanel layer1 = new JPanel();
	JButton resetB = new JButton("Reset");
	JButton saveB = new JButton("Save");
	
	JPanel layer2 = new JPanel();
	JButton button[][] = new JButton[50][50];
	
	boolean isBlack = true;//record if the black is first
	int data[][] = new int[50][50];
	boolean gameOver = false;
	
	
	public gobang(){
		Container c = this.getContentPane();
		c.add(layer1, BorderLayout.NORTH);
		layer1.add(resetB);
		layer1.add(saveB);
		
		c.add(layer2, BorderLayout.CENTER);
		layer2.setLayout(new GridLayout(50, 50));
		for(int i = 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				button[i][j] = new JButton("");
			}
		}
		for(int i = 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				layer2.add(button[i][j]);
				button[i][j].setBackground(Color.BLUE);
				button[i][j].addActionListener(new Handler(i, j));
			}
		}
		resetB.addActionListener(new resetHandler());
		saveB.addActionListener(new saveHandler());
	}
	
	class resetHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for(int i = 0; i < 50; i++){
				for(int j = 0; j < 50; j++){
					button[i][j].setBackground(Color.BLUE);
				}
			}
			isBlack = true;
			gameOver = false;
		}
		
	}
	
	
	/**
	 * @author Susie Weng
	 * 
	 */
	class saveHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for(int i = 0; i < 50; i++){
				for(int j = 0; j < 50; j++){
					if(button[i][j].getBackground() == Color.BLACK)
						data[i][j] = 1;
					else if(button[i][j].getBackground() == Color.WHITE)
						data[i][j] = -1;
					else data[i][j] = 0;
				}
			}
			try{
				File file = new File("out.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for(int i = 0;i < 50; i++){
					for(int j = 0; j < 50; j++){
						if(j != 0)
							bw.write(",");
						bw.write(String.valueOf(data[i][j]));
					}
					bw.write("\r\n");
				}
				bw.write(isBlack+"\r\n");
				bw.flush();
				bw.close();
			}catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
		}
	}
	
	
	class Handler implements ActionListener{

		int row = -1,column = -1;
		
		public Handler(int x,int y){
			row = x;
			column = y;
		}
		
		boolean isValid(){
			if(button[row][column].getBackground() != Color.BLUE)
				return false;
			return true;
		}
		
		public void changePlayer(){
			if(isBlack)
				isBlack = false;
			else isBlack = true;
		}
		
		public void whoWin(){
			int count = 0;
			if(isBlack) {
				count = 0;
				for(int i = row; i < row + 5 && i < 50; i++){
					if(button[i][column].getBackground() == Color.BLACK)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "Black Win");
					return;
				}
				count=0;
				for(int i = row; i > row - 5 && i >= 0; i--){
					if(button[i][column].getBackground() == Color.BLACK)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "Black Wins");
					return;
				}
				
				count = 0;
				for(int j = column;j < column + 5 && j < 50; j++){
					if(button[row][j].getBackground() == Color.BLACK)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "Black Wins");
					return;
				}
				
				count = 0;
				for(int j = column; j > column - 5 && j >= 0; j--){
					if(button[row][j].getBackground() == Color.BLACK)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "Black Wins");
					return;
				}
				
			}else{
				count = 0;
				for(int i = row;i < row + 5 && i < 50; i++){
					if(button[i][column].getBackground()==Color.WHITE)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "White Wins");
					return;
				}
				count = 0;
				for(int i = row; i > row - 5 && i >= 0; i--){
					if(button[i][column].getBackground() == Color.WHITE)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "White Wins");
					return;
				}
				
				count = 0;
				for(int j = column;j < column + 5 && j < 50; j++){
					if(button[row][j].getBackground() == Color.WHITE)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "White Wins");
					return;
				}
				
				count = 0;
				for(int j = column;j > column-5 && j >= 0; j--){
					if(button[row][j].getBackground() == Color.white)
						count++;
				}
				if(count == 5){
					gameOver = true;
					JOptionPane.showMessageDialog(null, "White Wins");
					return;
				}
			}
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(gameOver){
				JOptionPane.showMessageDialog(null, "Gameover, please start again");
				return;
			}
			if(!isValid()){
				JOptionPane.showMessageDialog(null, "Invalid motion");
				return;
			}
			if(isBlack)
				button[row][column].setBackground(Color.BLACK);
			else button[row][column].setBackground(Color.WHITE);
			
			whoWin();
			changePlayer();
		}
		
	}
	
	
	public static void main(String[] args){
		gobang chess = new gobang();
		chess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chess.setSize(1000,1000);
		chess.setVisible(true);
	}
	
}
