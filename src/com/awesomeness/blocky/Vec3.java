package com.awesomeness.blocky;
import java.io.Serializable;
import java.util.ArrayList;

public class Vec3 implements Serializable{
	
	static final long serialVersionUID = -7298352464830308761L;
	
	public static final Vec3 ZERO = new Vec3(0, 0, 0);
	public static final Vec3 UP = new Vec3(0, -1, 0);
	public static final Vec3 DOWN = new Vec3(0, 1, 0);
	public static final Vec3 LEFT = new Vec3(-1, 0, 0);
	public static final Vec3 RIGHT = new Vec3(1, 0, 0);
	public static final Vec3 FORWARD = new Vec3(0, 0, 1);
	public static final Vec3 BACKWARD = new Vec3(0, 0, -1);
	
	public float x;
	public float y;
	public float z;
	
	Vec3(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void rotate(String axis, Vec3 point, float deg){
		deg = (float) Math.toRadians(deg);
		if(axis.equals("y")){
			float[] coords = getCoords(point.z, this.z, point.x, this.x, deg);
			this.z = coords[0];
			this.x = coords[1];
		}else if(axis.equals("x")){
			float[] coords = getCoords(point.y, this.y, point.z, this.z, deg);
			this.y = coords[0];
			this.z = coords[1];
		}else if(axis.equals("z")){
			float[] coords = getCoords(point.x, this.x, point.y, this.y, deg);
			this.x = coords[0];
			this.y = coords[1];
		}
	}
	private float[] getCoords(float ptX, float thX, float ptY, float thY, float deg){
		float angle = deg;
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		float x = cos*(thX-ptX) - sin*(thY-ptY) + ptX;
		float y = sin*(thX-ptX) + cos*(thY-ptY) + ptY;
		float[] coords = {x,y};
		return coords;
	}
	public static ArrayList<Vec3> getCube(Vec3 center, float side){
		ArrayList<Vec3> vecs = new ArrayList<Vec3>();
		float s = side/2;
		for(int i=-1; i<=1; i+=2){
			for(int j=-1; j<=1; j+=2){
				for(int k=-1; k<=1; k+=2){
					vecs.add(new Vec3(center.x+s*i, center.y+s*j, center.z+s*k));
				}
			}
		}
		return vecs;
	}
	
	public Vec3 multiply(float m){
		Vec3 vec = new Vec3(this.x, this.y, this.z);
		vec.x *= m;
		vec.y *= m;
		vec.z *= m;
		return vec;
	}
	
	public void translate(Vec3 vec){
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}
	
	public Vec3 add(Vec3 vec){
		Vec3 vec3 = new Vec3(0,0,0);
		vec3.x = this.x;
		vec3.y = this.y;
		vec3.z = this.z;
		vec3.x += vec.x;
		vec3.y += vec.y;
		vec3.z += vec.z;
		return vec3;
	}
	
	public Vec3 forward(Vec3 rotation){
		Vec3 rot = rotation.clone();
		rot.x = (float) Math.toRadians(rot.x);
		rot.y = (float) Math.toRadians(rot.y);
		rot.z = (float) Math.toRadians(rot.z);
		Vec3 rVec = new Vec3(0,0,0);
		rVec.x += Math.sin(rot.y);
		rVec.z += Math.cos(rot.y);
		return rVec;
	}
	
	public Vec3 right(Vec3 rotation){
		Vec3 rot = rotation.clone();
		rot.x = (float) Math.toRadians(rot.x);
		rot.y = (float) Math.toRadians(rot.y);
		rot.z = (float) Math.toRadians(rot.z);
		Vec3 rVec = new Vec3(0,0,0);
		rVec.z -= Math.sin(rot.y);
		rVec.x += Math.cos(rot.y);
		return rVec;
	}
	
	public void dilate(Vec3 vec, float scale){
		translate(new Vec3((this.x - vec.x)*(scale-1), (this.y - vec.y)*(scale-1), (this.z - vec.z)*(scale-1)));
	}
	
	public static Vec3 midpoint(Vec3 vec1, Vec3 vec2){
		vec1.translate(vec2);
		vec1.x /= 2;
		vec1.y /= 2;
		vec1.z /= 2;
		return vec1;
	}
	
	public static float distance(Vec3 vec1, Vec3 vec2){
		return (float) Math.sqrt(Math.pow((vec1.x-vec2.x),2) + Math.pow((vec1.y-vec2.y),2) + Math.pow((vec1.z-vec2.z),2));
	}
	
	public float distance(Vec3 vec){
		return distance(vec, this);
	}
	
	public Vec3 clone(){
		return new Vec3(this.x, this.y, this.z);
	}
	
	public void print(){
		System.out.println("X: " + x + " Y: " + y + " Z: " + z);
	}
}
