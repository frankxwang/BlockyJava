package com.awesomeness.blocky;
import java.io.Serializable;
import java.util.ArrayList;

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
		position = Vec3.midpoint((Vec3) vecs.get(0).clone(),(Vec3) vecs.get(4).clone());
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
		if(!Draw.grounded){
			Draw.y += objs.get(0).velocity.y;
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
}
