package com.awesomeness.blocky;

import java.io.Serializable;

public class Block extends RectPrism implements Serializable{
	private static final long serialVersionUID = -5932933183600972712L;
	public static int size = 100;
	Block(Vec3 pos){
		super(pos, size, size, size);
	}
}
