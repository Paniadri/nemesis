package com.paniadri.nemesis.guacamole;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.net.GuacamoleSocket;
import org.glyptodon.guacamole.net.GuacamoleTunnel;
import org.glyptodon.guacamole.net.InetGuacamoleSocket;
import org.glyptodon.guacamole.net.SimpleGuacamoleTunnel;
import org.glyptodon.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.glyptodon.guacamole.protocol.GuacamoleConfiguration;
import org.glyptodon.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.glyptodon.guacamole.servlet.GuacamoleSession;


public class GuacamoleTunnelServlet extends GuacamoleHTTPTunnelServlet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(GuacamoleTunnelServlet.class);
	
	@Override
    protected GuacamoleTunnel doConnect(HttpServletRequest request) throws GuacamoleException {

		log.info("Accediendo al servlet guacamole - doConnect()");
		
        HttpSession httpSession = request.getSession(true);
        
        // guacd connection information
        String hostname = "localhost"; //ejecuto el demonio guacd en el propio ordenador
        int guacdPort = 4822;
        
        // Información de la conexión VNC
        // Extraer ip y puerto de los parámetros
        String direccion = request.getParameter("direccion");
        String puerto = request.getParameter("puerto");
        
        log.info("Pasados parametros direccion: "+direccion+" y puerto: "+puerto);
        
        GuacamoleConfiguration config = new GuacamoleConfiguration();
        config.setProtocol("vnc");
        config.setParameter("hostname", direccion);
        config.setParameter("port", puerto);
        
        // Conectar a guacd, haciendo de proxy para el servidor VNC
        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                new InetGuacamoleSocket(hostname, guacdPort),
                config
        );

        // Crear el tunel en el socket recién configurado
        GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);

        // Adjuntar el tunel a la sesión
        GuacamoleSession session = new GuacamoleSession(httpSession);
        session.attachTunnel(tunnel);

        return tunnel;

    }

}