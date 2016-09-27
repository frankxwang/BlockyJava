package com.awesomeness.blocky;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class ObjCollider extends BoxCollider{
	private static final long serialVersionUID = 8993485803514439062L;
	public boolean cX = false;
	public boolean cZ = false;
	ObjCollider(Vec3 vec1, Vec3 vec2, Object3d obj) {
		super(vec1, vec2, obj);
	}
	public boolean within(BoxCollider b, Vec3 v){
		Vec3 B3 = v.clone();
		
		Vec3 A1 = b.vec1.clone();
		Vec3 A2 = b.vec2.clone();
		
		if(((A1.x<=B3.x && A2.x>=B3.x) || (A1.x>=B3.x && A2.x<=B3.x)) &&
			((A1.y<=B3.y && A2.y>=B3.y) || (A1.y>=B3.y && A2.y<=B3.y)) &&
			((A1.z<=B3.z && A2.z>=B3.z) || (A1.z>=B3.z && A2.z<=B3.z))){
			return true;
		}
		return false;
	}
	public boolean isTouching(BoxCollider b){
		Iterator it = obj.vecs.iterator();
		while(it.hasNext()){
			Vec3 vec = (Vec3) it.next();
			if(within(b, vec)){
				return true;
			}
		}
		return false;
	}
	public int face(BoxCollider b){
//		int[] a1 = {0,4,5,1};//top
//		int[] a2 = {2,0,1,3};//left
//		int[] a3 = {6,7,5,4};//right
//		int[] a4 = {6,7,3,2};//bottom
//		int[] a5 = {3,7,5,1};//front
//		int[] a6 = {2,6,4,0};//back
//		int[][] faces = {a1, a2, a3, a4, a5, a6};
//		int vert = -1;
//		for(int i=0; i<obj.vecs.size(); i++){
//			Vec3 vec = (Vec3) obj.get(i);
//			if(within(b, vec)){
//				vert = i;
//			}
//		}
//		if(vert != -1){
//			for(int i=0; i<faces.length; i++){
//				int[] a = faces[i];
//				for(int j=0; j<a.length; j++){
//					if(a[j] == vert){
//						for(int k=0; k<a.length; k++){
//							if(within(b, obj.vecs.get(a[k]))){
//								return i;
//							}
//						}
//						continue;
//					}
//				}
//			}
//		}
//		return -1;
		if(isTouching(b)){
			Vec3 p = Vec3.midpoint(b.vec1, b.vec2);
			Vec3 op = obj.position.clone();
			if(p.y > op.y){
				if(p.x - op.x<Block.size/4 && op.x < p.x){
					return 1;
				}
				if(op.x - p.x<Block.size/4 && op.x>p.x){
					return 2;
				}
				if(p.z - op.z < Block.size/4 && p.z > op.z){
					return 4;
				}
				if(op.z - p.z < Block.size/4 && p.z < op.z){
					return 5;
				}
				if(p.y - op.y < Block.size/4 && p.y > op.y){
					return 3;
				}
				if(op.y - p.y < Block.size/4 && p.y < op.y){
					return 0;
				}
			}
		}
		return -1;
	}
	public void isTouchingArrayGrav(ArrayList objs){
		BoxCollider[] array = new BoxCollider[objs.size()];
		for(int i=0; i<objs.size(); i++){
			array[i] = ((Object3d)objs.get(i)).boxCollider;
		}
		for(BoxCollider b:array){
			if(isTouching(b)){
				if(!Draw.jump){
					Main.d.player.setVelocityY(0);
					Draw.grounded = true;
					break;
				}else{
					timer.schedule(new setVals(), 40);
				}
			}else{
				timer.schedule(new setGrounded(), 10);
			}
		}
//		System.out.println(face);
//		if(!Draw.jump){
//			if(face == 3){
//				Main.d.player.setVelocityY(0);
//			}
//			Draw.grounded = true;
//			break;
////			Draw.toggled = true;
////			Draw.toggled = false;
//		}else{
//			timer.schedule(new setVals(), 40);
//		}
//		if(face == 1 || face == 2){
//			Main.d.player.setVelocityX(0);
//			cX = true;
//		}else{
//			cX = false;
//		}
//		if(face == 4 || face == 5){
//			Main.d.player.setVelocityZ(0);
//			cZ = true;
//		}else{
//			cZ = false;
//		}
	}

	class setGrounded extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(!Draw.jump){
				timer.schedule(new setVals(), 0);
			}
		}
	}
	class setVals extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Draw.jump = false;
			Draw.grounded = false;
		}
	}
}
