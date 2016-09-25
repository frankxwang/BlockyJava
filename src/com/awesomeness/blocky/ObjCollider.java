package com.awesomeness.blocky;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

public class ObjCollider extends BoxCollider{
	private static final long serialVersionUID = 8993485803514439062L;

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
//					Draw.toggled = true;
//					Draw.toggled = false;
				}else{
					timer.schedule(new setVals(), 40);
				}
			}else{
				timer.schedule(new setGrounded(), 10);
			}
		}
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
