package ft.ufam.ptr.semaforo;

import ft.ufam.ptr.semaforo.clock.Clock;

public class SemaforoMain {

	public static void main(String[] args) {
		Clock clock = new Clock(700);
		
		SemaforoMaster m1 = new SemaforoMaster(null);
		SemaforoMaster m2 = new SemaforoMaster(null);
		
		SemaforoSlave s1  = new SemaforoSlave(null);
		SemaforoSlave s2  = new SemaforoSlave(null);
		
		SemaforoPedestre p1 = new SemaforoPedestre(null);
		
		m1.setEstadoVerde();
		m1.setTempoVerde(5);
		
		m2.setTempoVerde(5);
		
		m1.setSemaforoSimetrico(m2);
		m2.setSemaforoSimetrico(m1);
		
		m1.setSemaforoSlave(s1);
		m2.setSemaforoSlave(s2);
		
		m1.setSemaforoPedestre(p1);
		
		clock.addClockListener(m1);
		//clock.addClockListener(m2);
		
		clock.start();
	}

}
