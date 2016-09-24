package com.awesomeness.blocky;
import java.awt.*;

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;
import java.util.*;
import java.util.Timer;
public class Draw implements Serializable{
	
	static final long serialVersionUID = -7298352464830308761L;
	boolean keyDown = false;
	float mul = 1;
	public static String rotMode = "x";
	public ArrayList<Object3d> objects = new ArrayList<Object3d>();
	public static int W = 700;
	public static int H = 700;
	public Vec3 camRot = new Vec3(0,0,0);
	public static final Vec3 CENTER = new Vec3(W/2, H/2, 0);
	public FrameDraw panel;
	public static JFrame frame;
	public Object3d player;
//	public BoxCollider player.boxCollider;
	public static boolean jump = false;
	public static boolean grounded = false;
	public static boolean toggled = false;
	static int objNum = 8;
	static int width = 10;
	public static float frameRate = 10;
	public static float rate = frameRate/30;
	static boolean[] keys;
	static boolean[] mouse;
	void load(){
		try {
		    Robot robot = new Robot();
		    robot.mouseMove(W/2, H/2);
		} catch (AWTException e) {
		}
		keys = new boolean[KeyEvent.KEY_LAST];
		mouse = new boolean[3];
		player = new RectPrism(CENTER.add(new Vec3(0, 50, 0)), Block.size/2, Block.size*2, Block.size/2);
//		player.boxCollider = new BoxCollider(CENTER.add(new Vec3(0, 50, 0)), CENTER.add(new Vec3(1, 50, 1)));
//		player.boxCollider = player.boxCollider;
		frame = new JFrame();
		
		panel = new FrameDraw();
		panel.setSize(new Dimension(W, H));
		
		frame.setPreferredSize(new Dimension(W, H));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		
		frame.pack();
		Timer t = new Timer();
		t.schedule(new DoStuff(), 0, (long) frameRate);
	}
	void init(){
		for(int j=0; j<width; j++){
			for(int i=objNum-1; i>=0; i--){
				Block b = new Block(CENTER);
				b.translate(new Vec3(Block.size*j, Block.size + 200, Block.size*i));
				objects.add(b);
			}
		}
		Block top = new Block(CENTER.add(Vec3.UP.multiply(10*Block.size)));
		objects.add(top);
		load();
	}
	
	@SuppressWarnings("null")
	Draw() {
		init();
	}
	class DoStuff extends TimerTask{
		@Override
		public void run() {
			(panel).repaint();
		}
	}
	
	@SuppressWarnings("unused")
	private static void sleep(int m){
		try{Thread.sleep(m);}catch(Exception e){}
	}
	
	@SuppressWarnings("serial")
	class FrameDraw extends JPanel implements KeyListener, MouseListener{
		public static final long MAX_DIST = 1l;
		FrameDraw(){
			addKeyListener(this);
			addMouseListener(this);
		}
		protected void paintComponent(Graphics g) {
			update();
			g.clearRect(0, 0, W*2, H*2);
			int[] a1 = {0,4,5,1};
			int[] a2 = {2,0,1,3};
			int[] a3 = {6,7,5,4};
			int[] a4 = {6,7,3,2};
			int[] a5 = {3,7,5,1};
			int[] a6 = {2,6,4,0};
			int[][] arrays = {a1, a2, a3, a4, a5, a6};
			arrays = sortFaces(arrays);
			
			g.setColor(Color.black);
			draw3d(arrays, g);

			Object3d obj = player;
			for (int i = 0; i < obj.size(); i++) {
				Vec3 vec = (Vec3) obj.get(i);
				drawPoint(vec,g);
			}
			drawPoint(Object3d.raycastVec(objects, player.position, camRot, 10), g);
		}
		private int[][] getArray(ArrayList<Vec3> vecs, int[] array){
			int[][] rArray = new int[2][array.length];
			
			for(int i=0; i<array.length; i++){
				Vec3 vec = (Vec3)(vecs.get(array[i]));
				int x = (int)vec.x;
				int y = (int)vec.y;
				int z = (int)vec.z;
				int[] coords = getXY(new Vec3(x, y, z));
				rArray[0][i] = coords[0];
				rArray[1][i] = coords[1];
			}
			return rArray;
		}
		
		private void draw3d(int[][] arrays, Graphics g){
			for(int i=0; i<objects.size(); i++){
				Object3d obj = objects.get(i);
				for(int j=0; j<arrays.length; j++){
					int[] aInt = arrays[j];
					int[][] aaInt = getArray(obj.vecs, aInt);
					g.setColor(Color.red);
					g.fillPolygon(aaInt[0], aaInt[1], aInt.length);
					g.setColor(Color.black);
					g.drawPolygon(aaInt[0], aaInt[1], aInt.length);
				}
			}
		}
		private void drawPoint(Vec3 vec, Graphics g){
			int[] coords = getXY(vec);
			g.fillRect(coords[0], coords[1], 5, 5);
		}
		private void drawPlayerPoint(Vec3 vec, Graphics g, int size){
			g.fillRect((int)(vec.x - size/2), (int)(vec.y-size/2), size, size);
		}
		private int[] getXY(Vec3 vec){
			Vec3 point = vec.clone();
			point.rotate("y", new Vec3(player.position.x, player.position.y, player.position.z), -camRot.y);
			point.rotate("x", new Vec3(player.position.x, player.position.y, player.position.z), -camRot.x);
			point.rotate("z", new Vec3(player.position.x, player.position.y, player.position.z), -camRot.z);
			int x = (int) point.x;
			int y = (int) point.y;
			int z = (int) point.z;
			
//			int average_len = H/2;
//			int ry;
//			if(Vec3.angle(player.position.forward(camRot).add(player.position), player.position, point) >= 90){
//				ry = (int) (y-player.position.y + H - 50);
//				System.out.println("fila");
//			}else{
//				ry = (int) (((y-player.position.y) * ( average_len) ) / ( z+ ( average_len) -player.position.z)) + H/2;
//				ry += H/2-50;
//			}
//			
//			average_len = W/2;
//			int rx;
//			if(player.position.z >= z+average_len){
//				rx = (int) (((x-player.position.x) * ( average_len ) ) / (150)) + W/2;
//			}else{
//				rx= (int) (((x-player.position.x) * ( average_len ) ) / ( z + ( W/2) -player.position.z)) + W/2;
//			}
			int rx = (int) (( (x-player.position.x) * ( W / 2 ) ) / ( z + ( W / 2 ) - player.position.z)+W/2);
			if(player.position.z>=z+W/2){
				rx = (int) (( (x-player.position.x) * ( W / 2 ) ) / (player.position.z -( z + ( W / 2 )))+W/2);
			}
			int ry = (int) (( (y-player.position.y) * ( H / 2 ) ) / ( z + ( H / 2 ) - player.position.z)+H/2);
			ry += H/2-Block.size*3;
			if(player.position.z>=z+H/2){
				ry = (int) (( (y-player.position.y) * ( H / 2 ) ) / -( z + ( H / 2 ) - player.position.z)+H/2-y);
			}
			int[] i = {rx, ry};
			return i;
		}
		private int[][] sortFaces(int[][] arrays){
			Object3d obj = objects.get(0);
			Integer[] VecsMidZ = new Integer[arrays.length];
			for(int i=0; i<arrays.length; i++){
				Vec3 vec1 = new Vec3(0,0,0);
				vec1.x = ((Vec3)obj.get(arrays[i][0])).x;
				vec1.y = ((Vec3)obj.get(arrays[i][0])).y;
				vec1.z = ((Vec3)obj.get(arrays[i][0])).z;
				Vec3 vec2 = new Vec3(0,0,0);//(Vec3) vecs.get(arrays[i][2]);
				vec2.x = ((Vec3)obj.get(arrays[i][2])).x;
				vec2.y = ((Vec3)obj.get(arrays[i][2])).y;
				vec2.z = ((Vec3)obj.get(arrays[i][2])).z;
				vec1 = Vec3.midpoint(vec1, vec2);
				
				VecsMidZ[i]=(Integer)(int) Vec3.distance(player.position, vec1);
//				VecsMidZ[i] = ((int) ((Vec3)(vecs.get(iArray[0]))).z);
				VecsMidZ[i] *= 10;
				VecsMidZ[i] += i;
			}
			Arrays.sort(VecsMidZ, Collections.reverseOrder());
			@SuppressWarnings("unused")
			int[] sorted = new int[arrays.length];
			int[][] temp = new int[arrays.length][arrays[0].length];
			for(int i=0; i<arrays.length; i++){
				for(int j=0; j<arrays[0].length; j++){
					temp[i][j] = arrays[i][j];
				}
			}
//			temp = arrays;
			
			for(int i=0; i<arrays.length; i++){
				int mod = VecsMidZ[i]%10;
				if (mod<0) mod += 10;
				arrays[i] = temp[mod];
			}
			
			return arrays;
		}
		public void addNotify() {
	        super.addNotify();
	        requestFocus();
	    }
		
		@Override
		public void keyTyped(KeyEvent e) {

		}
		int keyNum;
		int ticks = 0;
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			keyDown = true;
			keyNum = e.getKeyCode();
			keys[e.getKeyCode()] = true;//pressed
			if(keys[KeyEvent.VK_SPACE] && /*toggled*/ grounded){
				jump=true;
//				BoxCollider.timer.schedule(new setToggled(), 40);
			}
		}
		class setToggled extends TimerTask{
			@Override
			public void run() {
				
				Draw.toggled = false;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(keys[KeyEvent.VK_W] && keyDown){
				ticks = 5;
			}
			keys[e.getKeyCode()] = false;
			
		}
		public void update(){
			if(ticks > 0 && mul == 1){
				ticks -= rate;
			}
			panel.requestFocus();
//			Collections.sort(objects, (Object3d o1,Object3d o2) -> ((Float)(Vec3.distance(player,position, o1.position).compareTo((Float)(Vec3.distance(player,position, o2.position))))));
			Collections.sort(objects, new Comparator<Object3d>(){
			  public int compare(Object3d o1, Object3d o2){
			    return (int) -(dist(o1)-dist(o2));
			  }
			  private float dist(Object3d o){
				  return Vec3.distance(player.position, o.position);
			  }
			});
			if(mouse[0]){
				Object3d hit = Object3d.raycast(objects, player.position, camRot, 10);
				if(hit!=null){
					hit.translate(Vec3.UP.multiply(Block.size*1));
				}
			}
//			Object3d.addVelocityArray(objects, new Vec3(0, -1, 0).multiply(0.6f*rate));//gravity happens to be 0.32 units be second
			player.addVelocity(new Vec3(0, 1, 0).multiply(1f*rate));
			player.boxCollider.isTouchingArrayGrav(objects);
			Object3d.updateArray(objects);
			boolean updated = false;
			mul = 1;
			if(keys[KeyEvent.VK_SHIFT] || keys[KeyEvent.VK_W] && ticks > 0 || keys[KeyEvent.VK_R]){
				mul = 2;
			}
			mul*=rate*Block.size/50;
			if(keys[KeyEvent.VK_W] && keyDown){
				Vec3 val = Vec3.forward(camRot).multiply(10*mul);
				val.y = 0;
				player.translate(val);
			} if(keys[KeyEvent.VK_S] && keyDown){
				Vec3 val = Vec3.forward(camRot).multiply(-10*mul);
				val.y = 0;
				player.translate(val);
			} if(keys[KeyEvent.VK_A] && keyDown){
				Vec3 val = Vec3.right(camRot).multiply(-10f*mul);
				val.y = 0;
				player.translate(val);
			} if(keys[KeyEvent.VK_D] && keyDown){
				Vec3 val = Vec3.right(camRot).multiply(10f*mul);
				val.y = 0;
				player.translate(val);
			}
			if(keys[KeyEvent.VK_SPACE] && keyDown && /*toggled*/ grounded){
				Vec3 jumpVec = new Vec3(0, -30, 0);
				player.setVelocity(jumpVec.multiply(rate));
			}
			if(!updated){
				player.update();
			}
			
			if(keys[KeyEvent.VK_M]){
				Main.save();
			}
			if(keys[KeyEvent.VK_L]){
				Main.load();
			}
			if(keys[KeyEvent.VK_R]){
				Main.restart();
			}
			if(keys[KeyEvent.VK_ESCAPE]){
				System.exit(0);
			}
//			System.out.println(y);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	        int mouseX = MouseInfo.getPointerInfo().getLocation().x;  
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			int distX = W/2 - mouseX;
			int distY = H/2 - mouseY;
			if(distX>=14){
				distX=14;
			}
			camRot.y -= distX*rate;
			camRot.x += distY*rate;
			try {
			    Robot robot = new Robot();
			    robot.mouseMove(W/2, H/2);
			} catch (AWTException e) {
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			mouse[e.getButton()-1]=true;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			mouse[e.getButton()-1]=false;
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}
}
