package me.funso.angtowerdefense;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Param {
	public Socket sock;
	public DataInputStream din;
	public DataOutputStream dout;
	
	public Param(Socket s, DataInputStream din, DataOutputStream dout) {
		this.sock = s;
		this.din = din;
		this.dout = dout;
	}
}