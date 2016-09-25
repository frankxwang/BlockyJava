package com.awesomeness.blocky;

import java.util.ArrayList;

public class Player extends RectPrism {
	public ObjCollider collider;
	Player(Vec3 center, float x, float y, float z){
		super(center, x, y, z);
	}
	public void setBox(){
		position = Vec3.midpoint((Vec3) vecs.get(1).clone(),(Vec3) vecs.get(6).clone());
		collider = new ObjCollider((Vec3)vecs.get(1), (Vec3)vecs.get(6), this);
	}
}
