package com.awesomeness.blocky;
import java.util.ArrayList;

public class RectPrism extends Object3d{
	RectPrism(){
	}
	RectPrism(Vec3 center, float x, float y, float z){
		super();
		ArrayList<Vec3> vecA = new ArrayList<Vec3>();
		float X = x/2;
		float Y = y/2;
		float Z = z/2;
		for(int i=-1; i<=1; i+=2){
			for(int j=-1; j<=1; j+=2){
				for(int k=-1; k<=1; k+=2){
					vecA.add(new Vec3(center.x+X*i, center.y+Y*j, center.z+Z*k));
				}
			}
		}
		this.vecs = vecA;
		setBox();
	}
}
