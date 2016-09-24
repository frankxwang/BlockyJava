package com.awesomeness.blocky;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Object3d implements Serializable{
	
	static final long serialVersionUID = -7298352464830308761L;
	
	ArrayList<Vec3> vecs = new ArrayList<Vec3>();
	public Vec3 velocity = new Vec3(0,0,0);
	public BoxCollider boxCollider;
	public Vec3 position;
	Object3d(){
	}
	
	Object3d(ArrayList vecs){
		this.vecs = vecs;
		setBox();
	}
	
	public void setBox(){
		position = Vec3.midpoint((Vec3) vecs.get(0).clone(),(Vec3) vecs.get(5).clone());
		boxCollider = new BoxCollider((Vec3)vecs.get(1), (Vec3)vecs.get(6));
	}
	
	public void update(){
		translate(velocity);
		setBox();
	}
	
	public void addVelocity(Vec3 vec){
		velocity.translate(vec);
	}
	
	public void setVelocity(Vec3 vec){
		this.velocity = vec;
	}
	
	public void setVelocityY(float v){
		this.velocity.y = v;
	}
	
	public void setVelocityX(float v){
		this.velocity.x = v;
	}
	
	public void dilate(Vec3 vec, float scale) {
		for (int i = 0; i < vecs.size(); i++) {
			((Vec3) vecs.get(i)).dilate(vec, scale);
		}
	}
	public void translate(Vec3 vec) {
		for (int i = 0; i < vecs.size(); i++) {
			((Vec3) vecs.get(i)).translate(vec);
		}
	}
	public void rotate(String axis, Vec3 point, float deg) {
		for (int i = 0; i < vecs.size(); i++) {
			((Vec3) vecs.get(i)).rotate(axis, point, deg);
		}
	}
	public Vec3 get(int i){
		return (Vec3) vecs.get(i);
	}
	public int size(){
		return vecs.size();
	}
	
	public static void dilateArray(ArrayList<Object3d> objs, Vec3 vec, float scale) {
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).dilate(vec, scale);
		}
	}
	
	public static void translateArray(ArrayList<Object3d> objs, Vec3 vec) {
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).translate(vec);
		}
	}
	
	public static void rotateArray(ArrayList<Object3d> objs, String axis, Vec3 point, float deg) {
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).rotate(axis, point, deg);
		}
	}
	
	public static void updateArray(ArrayList<Object3d> objs){
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).update();
		}
	}
	
	public static void addVelocityArray(ArrayList<Object3d> objs, Vec3 vec){
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).addVelocity(vec);
		}
	}
	
	public static void setVelocityArray(ArrayList<Object3d> objs, Vec3 vec){
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).setVelocity(vec);
		}
	}
	
	public static void setVelocityArrayX(ArrayList<Object3d> objs, float x){
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).setVelocityX(x);
		}
	}
	
	public static void setVelocityArrayX(ArrayList<Object3d> objs, float x, float dist){
		float d = dist/2;
		int W = Draw.W/2;
		for (int i = 0; i < objs.size(); i++) {
			Object3d obj = (Object3d) objs.get(i);
			if(obj.velocity.x < 0){
				x = -Math.abs(x);
			}
			else if(obj.velocity.x > 0){
				x = Math.abs(x);
			}
			if(obj.position.x-W < -d){
//				System.out.println(obj.position.x-W + " " + d);
				x = Math.abs(x);
				obj.position.translate(new Vec3(x*20, 0, 0));
			}else if(obj.position.x-W > d){
//				System.out.println(obj.position.x-W + " " + d);
				x = -Math.abs(x);
				obj.position.translate(new Vec3(x*20, 0, 0));
			}
			obj.setVelocityX(x);
			obj.position.translate(new Vec3(x, 0, 0));
		}
//		System.out.println("\n");
	}
	
	public static void setVelocityArrayY(ArrayList<Object3d> objs, float v){
		for (int i = 0; i < objs.size(); i++) {
			((Object3d) objs.get(i)).setVelocityY(v);
		}
	}
	public static Object3d raycast(ArrayList<Object3d> objs, Vec3 origin, Vec3 rot, float dist){
		Vec3 dir = Vec3.forward(rot).multiply(Block.size);
//		Vec3 forward = Draw.player.position.forward(Main.d.camRot);
//		float angle = Vec3.angle(dir, origin, forward);
		for(int j=0; j<dist; j++){
			BoxCollider point = new BoxCollider(origin, origin.add(new Vec3(1,1,1)));
			for(int i=0; i<objs.size(); i++){
				Object3d block = objs.get(i);
				if(point.isTouching(block.boxCollider)){
					return block;
				}
	//			if(Math.abs(Vec3.angle(block.position, origin, forward) - angle) < 0.07
	//					&& block.position.distance(origin)<=dist){
	//				return block;
	//			}
			}
			origin.translate(dir);
		}
//		Iterator it = objs.iterator();
//		int num = 0;
//		while(it.hasNext()){
//			Object3d block = (Object3d)it.next();
//			if(Math.abs(Vec3.angle(block.position, origin, forward) - angle) < 10
//					&& block.position.distance(origin)<=dist){
//				System.out.println(num);
//				return objs.get(num);
//			}
//			num++;
//		}
		return null;
	}
	public static Vec3 raycastVec(ArrayList<Object3d> objs, Vec3 origin, Vec3 rot, float dist){
		Vec3 o = origin.clone();
		Vec3 dir = Vec3.forward(rot).multiply(Block.size);
		for(int j=0; j<dist; j++){
			BoxCollider point = new BoxCollider(o, o.add(new Vec3(1,1,1)));
			for(int i=0; i<objs.size(); i++){
				Object3d block = objs.get(i);
				if(point.isTouching(block.boxCollider)){
					return o;
				}
			}
			o.translate(dir);
		}
		return o;
	}
}
