package com.paniadri.nemesis.guacamole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.net.GuacamoleSocket;
import org.glyptodon.guacamole.net.GuacamoleTunnel;
import org.glyptodon.guacamole.net.InetGuacamoleSocket;
import org.glyptodon.guacamole.net.SimpleGuacamoleTunnel;
import org.glyptodon.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.glyptodon.guacamole.protocol.GuacamoleConfiguration;
import org.glyptodon.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.glyptodon.guacamole.servlet.GuacamoleSession;

public class GuacamoleTunnelServlet extends GuacamoleHTTPTunnelServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected GuacamoleTunnel doConnect(HttpServletRequest request) throws GuacamoleException {

        HttpSession httpSession = request.getSession(true);

        // guacd connection information
        String hostname = "localhost"; //ejecuto el demonio guacd en el propio ordenador
        int port = 4822;

        // VNC connection information
        GuacamoleConfiguration config = new GuacamoleConfiguration();
        config.setProtocol("vnc");
        config.setParameter("hostname", "192.168.1.49");
        config.setParameter("port", "5901");
//        config.setParameter("password", "password");

        // Connect to guacd, proxying a connection to the VNC server above
        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                new InetGuacamoleSocket(hostname, port),
                config
        );

        // Create tunnel from now-configured socket
        GuacamoleTunnel tunnel = new SimpleGuacamoleTunnel(socket);

        // Attach tunnel
        GuacamoleSession session = new GuacamoleSession(httpSession);
        session.attachTunnel(tunnel);

        return tunnel;

    }

}