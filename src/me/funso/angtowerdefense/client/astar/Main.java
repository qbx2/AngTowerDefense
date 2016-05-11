package me.funso.angtowerdefense.client.astar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] a) throws IOException {
		File file = new File("/Users/baek/Documents/workspace/AngTowerDefense/stage2.txt");
		FileInputStream input = new FileInputStream(file);
		byte buf[] = new byte[input.available()];
		input.read(buf);
		input.close();
		Map map = new Map(new String(buf));
		System.out.println(map.aStar(map.find('S'), map.find('G')));
	}
}
