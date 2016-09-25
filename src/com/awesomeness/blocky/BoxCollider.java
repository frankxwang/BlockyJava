package com.awesomeness.blocky;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BoxCollider implements Serializable{
	
	static final long serialVersionUID = -7298352464830308761L;
	
	Object3d obj;
	Vec3 vec1;
	Vec3 vec2;
	static Timer timer = new Timer();
	BoxCollider(Vec3 vec1, Vec3 vec2){
		this.vec1 = vec1;
		this.vec2 = vec2;
	}
	BoxCollider(Vec3 vec1, Vec3 vec2, Object3d obj){
		this.vec1 = vec1;
		this.vec2 = vec2;
		this.obj = obj;
	}
	
	public boolean isTouching(BoxCollider b){
		Vec3 B1 = this.vec1.clone();
		Vec3 B2 = this.vec2.clone();
		Vec3 B3 = Vec3.midpoint(B1, B2);
		
		Vec3 A1 = b.vec1.clone();
		Vec3 A2 = b.vec2.clone();
		
		if(((A1.x<=B3.x && A2.x>=B3.x) || (A1.x>=B3.x && A2.x<=B3.x)) &&
			((A1.y<=B3.y && A2.y>=B3.y) || (A1.y>=B3.y && A2.y<=B3.y)) &&
			((A1.z<=B3.z && A2.z>=B3.z) || (A1.z>=B3.z && A2.z<=B3.z))){
			return true;
		}
		
		Vec3 A3 = Vec3.midpoint(A1, A2);
		
		if(((B1.x<=A3.x && B2.x>=A3.x) || (B1.x>=A3.x && B2.x<=A3.x)) &&
				((B1.y<=A3.y && B2.y>=A3.y) || (B1.y>=A3.y && B2.y<=A3.y)) &&
				((B1.z<=A3.z && B2.z>=A3.z) || (B1.z>=A3.z && B2.z<=A3.z))){
			return true;
		}
		return false;
	}
	
	public void translate(Vec3 vec){
		vec1.translate(vec);
		vec2.translate(vec);
	}
}
