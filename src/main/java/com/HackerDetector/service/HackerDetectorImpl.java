package com.HackerDetector.service;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.HackerDetector.model.Datos;

@Service
public class HackerDetectorImpl implements HackerDetector {
	
	Datos[] datos=null;
	String ip, date, action, userName;
	String SO = System.getProperty("os.name");
	public static final String SIGNIN_SUCCESS = "SIGNIN_SUCCESS";
	public static final String SIGNIN_FAILURE = "SIGNIN_FAILURE";
	public static final String FOLDERPATHWINDOWS = "C:/temp/";
	public static final String FOLDERPATHUNIX = File.separator +"home" +File.separator;
	public static final String DATOS = "datos.log";
	public static final String IPSOSPECHOSOS = "IPsospechosos.log";
	public static final String IPBLOQUEADOS = "IPbloqueados.log";
	
	public Datos Servicio(String userName) {

		datos = lecturaLog(datos);
		ip = obtenerIp();
		date = obtenerDate();
		action = obtenerAction(userName);
		
		//Escribir en IPsospechosos o IPbloqueados
		if(action.equals(SIGNIN_FAILURE)) {
			
			// Escribir bloqueados si se registra más de 5 veces
			escribirBloqueado(datos, ip, date, action, userName);
			
			// Escribir en IPsospechosos
			escribirSospechoso(ip, date, action, userName);
		}
		// Escribir nueva línea en datos
		escribirLinea(ip, date, action, userName);
		
		lecturaLog(datos);
		return new Datos(ip, date, action, userName);
	}
	
	public Datos[] lecturaLog(Datos[] datos) {
		File file = new File("", "");
		if(SO.contains("Windows")) {
			file = new File(FOLDERPATHWINDOWS, IPSOSPECHOSOS);
		}else {
			file = new File(FOLDERPATHUNIX, IPSOSPECHOSOS);
		}
		
		if(file.exists()) {
			Path pt=Paths.get(file.getPath());
			try(Stream<String> st=Files.lines(pt)){
			datos=st.map(s->s.split("[,]"))
					.map(d->new Datos(d[0], d[1], d[2], d[3]))
					.toArray(t->new Datos[t]);
			} catch (IOException e) {
			e.printStackTrace();
			}
		}else {
			String[] arr = new String[0];
			Stream<String> st=Stream.of(arr);
			datos=st.map(d->new Datos(d, d, d, d))
					.toArray(t->new Datos[t]);
		}
		List<Datos> Datos=new ArrayList<>();
		for(int i = 0; i<datos.length; i++) {
			Datos.add(datos[i]);
		}
		return datos;
	}
	
	public String obtenerIp() {
		String ip = null;
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (SocketException | UnknownHostException e1) {
			e1.printStackTrace();
		}
		return ip;
	}
	
	public String obtenerDate() {
		String date = null;
		Instant instant = Instant.now();
		long timeStampMillis = instant.getEpochSecond();
		return date = timeStampMillis+"";
	}
	
	public String obtenerAction(String userName) {
		String action = null;
		if(userName.contains(".")) {
			action = SIGNIN_SUCCESS;
		}else {
			action = SIGNIN_FAILURE;
		}
		return action;
	}
	
	public void escribirLinea(String ip, String date, String action, String userName) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(ip +"," +date +"," +action +"," +userName +"\n");
			File file = new File("", "");
			if(SO.contains("Windows")) {
				file = new File(FOLDERPATHWINDOWS, DATOS);
			}else {
				file = new File(FOLDERPATHUNIX, DATOS);
			}
			createOrGetFile(file);
			Files.write(Paths.get(FOLDERPATHWINDOWS + DATOS), sb.toString().getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void escribirSospechoso(String ip, String date, String action, String userName) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(ip +"," +date +"," +action +"," +userName +"\n");
			File file = new File("", "");
			if(SO.contains("Windows")) {
				file = new File(FOLDERPATHWINDOWS, IPSOSPECHOSOS);
			}else {
				file = new File(FOLDERPATHUNIX, IPSOSPECHOSOS);
			}
			createOrGetFile(file);
			Files.write(Paths.get(FOLDERPATHWINDOWS + IPSOSPECHOSOS), sb.toString().getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void escribirBloqueado(Datos[] datos,String ip, String date, String action, String userName) {
		int ipSospechosa = 0;
		for(int i = datos.length-1; i>=0; i--) {
			try {
				int horaAnterior = Integer.parseInt(datos[i].getDate().trim());
				int horaActual = Integer.parseInt(date.trim());
				
				if(horaActual - horaAnterior < 300) {
					ipSospechosa++;
					if(datos[i].getIp().equals(ip) && ipSospechosa==5) {
						try {
							StringBuilder sb = new StringBuilder();
							sb.append(ip +"," +date +"," +action +"," +userName +"\n");
							File file = new File("", "");
							if(SO.contains("Windows")) {
								file = new File(FOLDERPATHWINDOWS, IPBLOQUEADOS);
							}else {
								file = new File(FOLDERPATHUNIX, IPBLOQUEADOS);
							}
							createOrGetFile(file);
							Files.write(Paths.get(FOLDERPATHWINDOWS + IPBLOQUEADOS), sb.toString().getBytes(), StandardOpenOption.APPEND);
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}					
			} catch (NumberFormatException e){
			}
		}
	}
	
	static void createOrGetFile(File folder) throws IOException {
		  if (!folder.exists()) {
		    Files.createFile(folder.toPath());
		  }
	}
}
